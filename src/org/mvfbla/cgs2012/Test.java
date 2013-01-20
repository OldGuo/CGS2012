package org.mvfbla.cgs2012;

public class Test {
	public static void main(String[] args) {
		Vector v1 = new Vector(0, -2);
		Vector v2 = new Vector(0,-1);
		System.out.println(v1.normalise().dot(v2));
	}
}
