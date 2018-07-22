package ru.stqa.pft.sandbox;

public class MyFirstProgram {
	
	public static void main(String[] args){
	    String smb = "somebody";
		hello(smb);

		Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной " + s.a + " = " + s.area());


        Rectangle r = new Rectangle(5, 6);
        System.out.println("Площадь квадрата со стороными " + r.a + " и " + r.b + " = " + r.area());
	}

	public static void hello(String smb){
        System.out.println("Hello " + smb + "!");
    }
}
