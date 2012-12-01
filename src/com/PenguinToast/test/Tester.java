package com.PenguinToast.test;

import java.awt.geom.Rectangle2D;


public class Tester {
	public static void main(String[] args) {
		Rectangle r1 = new Rectangle(0, 0, 20, 20);
		System.out.println(new Rectangle2D.Float(r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight()));
	}
}
