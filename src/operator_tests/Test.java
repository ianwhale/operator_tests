package operator_tests;

public class Test {
	public static void main(String[] args) {
		int a = 1;
		int b = 15;
		int c = 3;

		if (a + b <= 30) {
		    c = 4;
        }
        else {
		    a = 0;
        }

        while (c > 0) {
		    a = a + 1;
		    c = c - 1;
        }

		System.out.println(a);
	}
}