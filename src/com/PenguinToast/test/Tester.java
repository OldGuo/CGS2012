package com.PenguinToast.test;



public class Tester {
	public static void main(String[] args) {
		byte b = 0b0000;
		b = (byte) (b | 0b0110);
		b = (byte) (b & 0b1011);
		System.out.println((b >> 1) & 0b0001);
	}
}
