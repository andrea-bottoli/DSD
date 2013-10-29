
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
	int a;
	int b;
	int c;
	String shape;
	
	public int getA() {
		return a;
	}
	public int getB() {
		return b;
	}
	public int getC() {
		return c;
	}
	public String getShape() {
		return shape;
		
	}

	public Test(String a, String b, String c){
		if (isTriangle(a, b, c)) {
			this.a = Integer.parseInt(a);
			this.b = Integer.parseInt(b);
			this.c = Integer.parseInt(c);
			if (this.a == this.b && this.b == this.c) 
				this.shape = "equilateral";
			else if (this.a != this.b && this.b != this.c && this.a != this.c) 
				this.shape = "scalene";
			else 
				this.shape = "isoscelesISOSCELESBLA";
			//this.shape = getShape(a, b, c);
		} else {
			shape = "invalid";
		}
	}
	
	public boolean isTriangle(String a, String b, String c){
		try{
			  int num1 = Integer.parseInt(a);
			  int num2 = Integer.parseInt(b);
			  int num3 = Integer.parseInt(c);
			  if (num1 + num2 < num3)
				  return false;
			  if (num1 + num3 < num2)
				  return false;
			  if (num2 + num3 < num1)
				  return false;
			  return true;
			} catch (NumberFormatException e) {
				return false;
			}
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("Hello World");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please enter the lengths of the sides of the triangle :");
		System.out.println("a: ");
		String aInput = in.readLine();
		System.out.println("a: ");
		System.out.println("a: ");

		System.out.println("b: ");
		String bInput = in.readLine();

		System.out.println("c: ");
		String cInput = in.readLine();
		
		Test t = new Test(aInput, bInput, cInput);
		System.out.println(t.shape);
	}
}

