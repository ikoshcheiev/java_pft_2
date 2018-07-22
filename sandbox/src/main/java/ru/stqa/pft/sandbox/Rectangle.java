package ru.stqa.pft.sandbox;

public class Rectangle {

    public double a;
    public double b;

    Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double area(){
        return a * b;
    }
}
