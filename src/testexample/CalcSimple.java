package testexample;

public class CalcSimple {
	private int a, b;

	CalcSimple() {
		a = 0;
		b = 0;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int add() {
		return a + b;
	}

	public double divide() {
		double result = 0;
		try {
			if (b == 0)
				throw new MyException();
			result = a / b;
		} catch (Exception e) {
			System.out.println("Aldaa: " + e.getMessage());
		} finally {
			System.out.println("Finally run...");
		}
		return result;
	}
	
	public String concat(String a, String b) {
		return a + b;
	}
	
	public boolean equals(CalcSimple calc) {
		if(this.a == calc.getA() && this.b == calc.getB())
			return true;
		else
			return false;
	}

}
