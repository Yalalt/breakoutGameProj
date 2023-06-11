package testexample;

public class Base {

	public static void main(String[] args) {
		CalcSimple calc = new CalcSimple();
		calc.setA(16);
		calc.setB(0);
		
		System.out.println("Hariu add 1: " + calc.add());
		System.out.println("Hariu divide: " + calc.divide());
		System.out.println("Hariu add 2: " + calc.add());

	}

}
