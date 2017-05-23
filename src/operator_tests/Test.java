package operator_tests;

public class Test {
	public static void main(String[] args) {
		int a = foo(1, 2, 3);
		System.out.println(a);
	}

	private static int foo(int a, int b, int c) {
	    if (a + b <= 50) {
	        c = 4;
        }
        else {
	        a = 0;
        }

        while (c > 0) {
	        c = c - 1;
	        a = a + 1;
        }

        return a;
    }
}