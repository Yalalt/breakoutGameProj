package testexample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {
	
	CalcSimple calc, calc2, calcString;

	@BeforeEach
	void setUp() throws Exception {
		calc = new CalcSimple();
		calc2 = new CalcSimple();
		calcString = new CalcSimple();
	}

	@Test
	void testAdd() {
		calc.setA(2);
		calc.setB(5);
		
		assertEquals(7, calc.add());
	}

	@Test
	void testDivide() {
		calc2.setA(8);
		calc2.setB(4);
		
		assertEquals(2, calc2.divide());
	}
	
	@Test
	void testConcat() {
		assertEquals("Hello world", calcString.concat("Hello ", "world"));
	}
	
	@Test
	void testCalcObject() {
		calc.setA(1);
		calc.setB(2);
		
		calc2.setA(1);
		calc2.setB(2);
		
		assertTrue(calc.equals(calc2));
	}

}
