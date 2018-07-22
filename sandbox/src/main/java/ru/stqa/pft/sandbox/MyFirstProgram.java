package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	
	public static void main(String[] args){
	    String smb = "somebody";
		hello(smb);

		double a = 5;
        System.out.println("Площадь квадрата со стороной " + a + " = " + area(a));


        double b = 6;
        System.out.println("Площадь квадрата со стороными " + a + " и " + b + " = " + area(a, b));
	}

	public static void hello(String smb){
        System.out.println("Hello " + smb + "!");
    }

    public static double area(double a){
	    return a * a;
    }

    public static double area(double a, double b){
        return a * b;
    }
}
