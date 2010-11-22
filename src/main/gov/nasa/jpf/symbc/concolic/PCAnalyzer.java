package gov.nasa.jpf.symbc.concolic;

import gov.nasa.jpf.symbc.SymbolicInstructionFactory;
import gov.nasa.jpf.symbc.numeric.*;

public class PCAnalyzer {

	// this one will hold the parts that are easy to solve
	PathCondition simplePC;
	// this is the part that contains functions/operations 
	// that we cannot solve with our DPs and hence want to use 
	// concrete values on
	PathCondition concolicPC;
	
	/* 
	 * Walks the PC and splits it into simplePC and concolicPC
	 */
	public void splitPathCondition(PathCondition pc) {
		PathCondition newPC = pc.make_copy();
		Constraint cRef = newPC.header;
		simplePC = new PathCondition();
		concolicPC = new PathCondition(); 
		
		while (cRef != null) {
			if (cRef instanceof RealConstraint) {
				// this will be the only one that goes to concolicPC for now
				concolicPC.prependUnlessRepeated(new RealConstraint((RealConstraint)cRef));
			} else if (cRef instanceof LinearIntegerConstraint) {
				simplePC.prependUnlessRepeated(new LinearIntegerConstraint((LinearIntegerConstraint)cRef));
			} else if (cRef instanceof MixedConstraint) {
				simplePC.prependUnlessRepeated(new MixedConstraint((MixedConstraint)cRef));
			} else if (cRef instanceof LogicalORLinearIntegerConstraints) {
				simplePC.prependUnlessRepeated(new LogicalORLinearIntegerConstraints(((LogicalORLinearIntegerConstraints)cRef).getList()));
			} else {
				throw new RuntimeException("## Error: Non Linear Integer Constraint not handled " + cRef);
			}
			cRef = cRef.and;
		}
		if (SymbolicInstructionFactory.debugMode) {
			System.out.println("--------after splitting------------");
			System.out.println("concolicPC " + concolicPC);
			System.out.println("simplePC " + simplePC);
			System.out.println("--------after splitting------------");
		}
	}
	
	RealExpression getExpression(RealExpression eRef) {
		assert eRef != null;
		assert !(eRef instanceof RealConstant);

		if (eRef instanceof SymbolicReal) {
			return eRef;
		}

		if(eRef instanceof BinaryRealExpression) {
			Operator    opRef = ((BinaryRealExpression)eRef).getOp();
			RealExpression	e_leftRef = ((BinaryRealExpression)eRef).getLeft(); 
			RealExpression	e_rightRef = ((BinaryRealExpression)eRef).getRight();

			return new BinaryRealExpression(getExpression(e_leftRef),opRef,getExpression(e_rightRef));
		}

		if(eRef instanceof MathRealExpression) {
			return new RealConstant(eRef.solution());
		}

		throw new RuntimeException("## Error: Expression " + eRef);
	}

	
	RealConstraint traverseRealConstraint(RealConstraint cRef) {
		Comparator c_compRef = cRef.getComparator();
		RealExpression c_leftRef = (RealExpression)cRef.getLeft();
		RealExpression c_rightRef = (RealExpression)cRef.getRight();

		return new RealConstraint(getExpression(c_leftRef),c_compRef,getExpression(c_rightRef));
	}	
	
	public void solveSplitPC() {
		// first solve the simplePC and then use the results to update concolicPC
		simplePC.solve();
		if (SymbolicInstructionFactory.debugMode) {
			System.out.println("--------------------");
			System.out.println("simplePC " + simplePC);
			System.out.println("--------------------");
		}

		// now replace all math functions in concolicPC 
		// with their execution results with simplePC arguments
		
		//PathCondition simplifiedPC = new PathCondition();
		RealConstraint cRef = (RealConstraint)concolicPC.header;
		
		while (cRef != null) {
			simplePC.prependUnlessRepeated(traverseRealConstraint(cRef)); 
			cRef = (RealConstraint)cRef.and;
		}
		
		simplePC.solve();
		
		if (true /*SymbolicInstructionFactory.debugMode*/) {
			System.out.println("--------------------");
			System.out.println("simplifiedPC " + simplePC);
			System.out.println("--------------------");
		}
		
	}
	
	public PathCondition getSimplifiedPC() {
		return simplePC;
	}
	
	
}