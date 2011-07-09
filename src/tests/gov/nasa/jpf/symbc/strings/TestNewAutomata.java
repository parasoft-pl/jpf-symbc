package gov.nasa.jpf.symbc.strings;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.symbc.SymbolicInstructionFactory;
import gov.nasa.jpf.symbc.numeric.Comparator;
import gov.nasa.jpf.symbc.numeric.IntegerConstant;
import gov.nasa.jpf.symbc.numeric.PathCondition;
import gov.nasa.jpf.symbc.numeric.SymbolicInteger;
import gov.nasa.jpf.symbc.string.StringComparator;
import gov.nasa.jpf.symbc.string.StringConstant;
import gov.nasa.jpf.symbc.string.StringExpression;
import gov.nasa.jpf.symbc.string.StringPathCondition;
import gov.nasa.jpf.symbc.string.StringSymbolic;
import gov.nasa.jpf.symbc.string.SymbolicStringConstraintsGeneral;
import junit.framework.Assert;

import org.junit.Test;


public class TestNewAutomata {
	@Test
	//NOTEQUALS
	public void Test1_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			StringSymbolic var3 = new StringSymbolic("var3");
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, var1, var2);
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, var2, var3);		
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().equals(var2.solution()));
			Assert.assertTrue(!var2.solution().equals(var3.solution()));
		}
	}
	
	@Test
	public void Test1_2 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			StringSymbolic var3 = new StringSymbolic("var3");
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, var1, var2);
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, var2, var3);
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, var1, var3);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().equals(var2.solution()));
			Assert.assertTrue(!var2.solution().equals(var3.solution()));
			Assert.assertTrue(!var1.solution().equals(var3.solution()));
		}
	}
	
	
	@Test
	public void Test1_3 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var[] = new StringSymbolic[10];
			for (int i = 0; i < var.length; i++) {
				var[i] = new StringSymbolic("var" + i);
			}
			for (int i = 0; i < var.length-1; i++) {
				for (int j = i+1; j < var.length; j++) {
					stringCurrentPC._addDet(StringComparator.NOTEQUALS, var[i], var[j]);
				}
			}
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			for (int i = 0; i < var.length-1; i++) {
				for (int j = i+1; j < var.length; j++) {
					Assert.assertTrue(!var[i].solution().equals(var[j].solution()));
				}
			}
			
		}
	}
	
	//NOTSTARTSWITH
	@Test
	public void Test2_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			pc._addDet(Comparator.GE, var1._length(), new IntegerConstant(2));
			stringCurrentPC._addDet(StringComparator.NOTSTARTSWITH, var2, var1);		
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().startsWith(var2.solution()));
		}
	}
	
	//No negatives
	@Test
	public void Test3_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			pc._addDet(Comparator.GE, var1._length(), new IntegerConstant(2));
			stringCurrentPC._addDet(StringComparator.EQUALS, var2, var1);		
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().equals(var2.solution()));
		}
	}
	
	//NOTENDSWITH
	@Test
	public void Test4_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			pc._addDet(Comparator.GE, var1._length(), new IntegerConstant(2));
			stringCurrentPC._addDet(StringComparator.NOTENDSWITH, var2, var1);		
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().endsWith(var2.solution()));
		}
	}
	
	//NOTCONTAINS
	@Test
	public void Test5_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			pc._addDet(Comparator.GE, var1._length(), new IntegerConstant(2));
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, var2, var1);		
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().contains(var2.solution()));
		}
	}
	
	//STARTSWITH
	@Test
	public void Test6_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("a"));
			Assert.assertTrue(var2.solution().startsWith("b"));
		}
	}
	
	@Test
	public void Test6_2 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTSTARTSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("a"));
			Assert.assertTrue(!var2.solution().startsWith("b"));
		}
	}

	@Test
	public void Test6_3 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTSTARTSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().startsWith("a"));
			Assert.assertTrue(var2.solution().startsWith("b"));
		}
	}
	
	@Test
	public void Test6_4 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTSTARTSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTSTARTSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().startsWith("a"));
			Assert.assertTrue(!var2.solution().startsWith("b"));
		}
	}
	
	@Test
	public void Test7_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.ENDSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.ENDSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("a"));
			Assert.assertTrue(var2.solution().startsWith("b"));
		}
	}
	
	@Test
	public void Test7_2 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.ENDSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTENDSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("a"));
			Assert.assertTrue(!var2.solution().startsWith("b"));
		}
	}

	@Test
	public void Test7_3 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTENDSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.ENDSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().startsWith("a"));
			Assert.assertTrue(var2.solution().startsWith("b"));
		}
	}
	
	@Test
	public void Test7_4 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTENDSWITH, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTENDSWITH, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().startsWith("a"));
			Assert.assertTrue(!var2.solution().startsWith("b"));
		}
	}
	
	@Test
	public void Test8_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.EQUALS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.EQUALS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().equals("a"));
			Assert.assertTrue(var2.solution().equals("b"));
		}
	}
	
	@Test
	public void Test8_2 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.EQUALS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			System.out.println(SymbolicStringConstraintsGeneral.getSolution());
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().equals("a"));
			Assert.assertTrue(var2.solution() == null || !var2.solution().equals("b"));
		}
	}

	@Test
	public void Test8_3 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.EQUALS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().equals("a"));
			Assert.assertTrue(var2.solution().equals("b"));
		}
	}
	
	@Test
	public void Test8_4 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().equals("a"));
			Assert.assertTrue(!var2.solution().equals("b"));
		}
	}
	
	@Test
	public void Test9_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().contains("a"));
			Assert.assertTrue(var2.solution().contains("b"));
		}
	}
	
	@Test
	public void Test9_2 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			System.out.println(SymbolicStringConstraintsGeneral.getSolution());
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().contains("a"));
			Assert.assertTrue(!var2.solution().contains("b"));
		}
	}

	@Test
	public void Test9_3 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().contains("a"));
			Assert.assertTrue(var2.solution().contains("b"));
		}
	}
	
	@Test
	public void Test9_4 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			StringPathCondition stringCurrentPC = new StringPathCondition(new PathCondition());
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("b"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().contains("a"));
			Assert.assertTrue(!var2.solution().contains("b"));
		}
	}
	
	//TRIM
	@Test
	public void Test10_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringExpression var2 = var1._trim();
			stringCurrentPC._addDet(StringComparator.EQUALS, new StringConstant("cc"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var2.solution().equals("cc"));
			Assert.assertTrue(var1.solution().trim().equals("cc"));
		}
	}
	
	@Test
	public void Test10_2 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringExpression var2 = var1._trim();
			stringCurrentPC._addDet(StringComparator.NOTEQUALS, new StringConstant("cc"), var2);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var2.solution().equals("cc"));
			Assert.assertTrue(!var1.solution().trim().equals("cc"));
		}
	}
	
	
	//CONCAT
	@Test
	public void Test11_1 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.LE, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().contains("a"));
			Assert.assertTrue(var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() < 10);
		}
	}
	
	@Test
	public void Test11_2 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.LE, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().contains("a"));
			Assert.assertTrue(var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() < 10);
		}
	}
	
	@Test
	public void Test11_3 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.LE, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().contains("a"));
			Assert.assertTrue(!var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() < 10);
		}
	}
	
	@Test
	public void Test11_4 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.LE, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().contains("a"));
			Assert.assertTrue(!var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() < 10);
		}
	}

	@Test
	public void Test11_5 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.GT, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().contains("a"));
			Assert.assertTrue(var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() > 10);
		}
	}
	
	@Test
	public void Test11_6 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.GT, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().contains("a"));
			Assert.assertTrue(var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() > 10);
		}
	}
	
	@Test
	public void Test11_7 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.CONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.GT, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().contains("a"));
			Assert.assertTrue(!var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() > 10);
		}
	}
	
	@Test
	public void Test11_8 () {
		String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("a"), var1);
			stringCurrentPC._addDet(StringComparator.NOTCONTAINS, new StringConstant("b"), var2);
			StringExpression var3 = var1._concat(var2);
			pc._addDet(Comparator.GT, var3._length(), 10);
			boolean result = stringCurrentPC.simplify();
			Assert.assertTrue(result);
			Assert.assertTrue(!var1.solution().contains("a"));
			Assert.assertTrue(!var2.solution().contains("b"));
			Assert.assertTrue(var1.solution().concat(var2.solution()).equals(var3.solution()));
			Assert.assertTrue(var3.solution().length() > 10);
		}
	}
	
	//INDEXOF
	@Test
	public void Test12_1 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("aa"), var1);
			pc._addDet(Comparator.LE, var1._length(), 10);
			pc._addDet(Comparator.GT, var1._indexOf(new StringConstant("a")), 0);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue("Solver " + solver + " failed",  !result);
			/*Assert.assertTrue(var1.solution().startsWith("aa"));
			Assert.assertTrue(var1.solution().indexOf("a") > 0);*/
			
		}
	}
	
	@Test
	public void Test12_2 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata", "z3_inc"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("bb"), var1);
			pc._addDet(Comparator.GT, var1._indexOf(new StringConstant("a")), 0);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("bb"));
			Assert.assertTrue(var1.solution().indexOf("a") > 0);
			
		}
	}
	
	@Test
	public void Test12_3 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata", "z3_inc"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("bb"), var1);
			pc._addDet(Comparator.EQ, var1._indexOf(new StringConstant("a")), -1);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("bb"));
			Assert.assertTrue(var1.solution().indexOf("a") == -1);
			
		}
	}
	
	@Test
	public void Test12_4 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata", "z3_inc"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("bb"), var1);
			pc._addDet(Comparator.EQ, var1._indexOf(new StringConstant("b")), -1);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue(!result);
		}
	}
	
	@Test
	public void Test12_5 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata", "z3_inc"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("bb"), var1);
			pc._addDet(Comparator.EQ, var1._indexOf(new StringConstant("b")), 1);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue(!result);
		}
	}
	
	@Test
	//TODO: Could do with a speedup
	public void Test13_1 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("aa"), var1);
			SymbolicInteger si = new SymbolicInteger("int1");
			pc._addDet(Comparator.LE, var1._length(), 10);
			pc._addDet(Comparator.EQ, var1._indexOf(new StringConstant("a"), new IntegerConstant(5)), si);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue("Solver " + solver + " failed",  result);
			Assert.assertTrue(var1.solution().startsWith("aa"));
			System.out.printf("var1.solution() '%s'\n", var1.solution());
			System.out.printf("si.solution() '%s'\n", si.solution());
			Assert.assertTrue(var1.solution().indexOf("a", 5) == si.solution());
			
		}
	}
	
	@Test
	public void Test13_2 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata", "z3_inc"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("bb"), var1);
			SymbolicInteger si = new SymbolicInteger("int1");
			pc._addDet(Comparator.EQ, var1._indexOf(new StringConstant("a"), new IntegerConstant(5)), si);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("bb"));
			Assert.assertTrue(var1.solution().indexOf("a", 5) == si.solution());
			
		}
	}
	
	@Test
	public void Test13_3 () {
		String[] solvers = new String[]{"automata"};
		//String[] solvers = new String[]{"automata", "z3_inc"};
		for (String solver: solvers) {
			System.out.println("Solver: " + solver);
			String[] options = {"+symbolic.dp=choco",
					"+symbolic.string_dp=" + solver,
					"+symbolic.string_dp_timeout_ms=0"};
			Config cfg = new Config(options);
			new SymbolicInstructionFactory(cfg);
			PathCondition pc = new PathCondition();
			StringPathCondition stringCurrentPC = new StringPathCondition(pc);
			StringSymbolic var1 = new StringSymbolic("var1");
			StringSymbolic var2 = new StringSymbolic("var2");
			stringCurrentPC._addDet(StringComparator.STARTSWITH, new StringConstant("aa"), var1);
			stringCurrentPC._addDet(StringComparator.ENDSWITH, new StringConstant("bb"), var2);
			pc._addDet(Comparator.GT, var1._indexOf(var2), 0);
			boolean result = stringCurrentPC.simplify();
			//System.out.printf("var1: '%s'\n", var1.solution());
			Assert.assertTrue(result);
			Assert.assertTrue(var1.solution().startsWith("aa"));
			Assert.assertTrue(var2.solution().endsWith("bb"));
			Assert.assertTrue(var1.solution().indexOf(var2.solution()) > 0);
			
		}
	}
}
