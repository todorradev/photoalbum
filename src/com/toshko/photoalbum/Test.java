package com.toshko.photoalbum;

public class Test {

	public static void main(String[] args) {
		int x = test();
		System.out.println(x);
	}

	private static int test() {
		return test(2);
	}

	private static int test(int i) {
		// TODO Auto-generated method stub
		return i+1;
	}
}
