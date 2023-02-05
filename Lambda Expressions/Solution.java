import java.io.*;
import java.util.*;

interface PerformOperation {
    boolean check(int a);
}

class MyMath {
    public static boolean checker(PerformOperation p, int num) {
        return p.check(num);
    }

    // Write your code here
    PerformOperation isOdd() {
        return (n) -> n % 2 != 0;
    }

    PerformOperation isPrime() {
        return (n) -> checkPrime(n);
    }

    PerformOperation isPalindrome() {
        return (n) -> checkPalindrome(n);
    }

    private boolean checkPrime(int num) {
        if (num < 2)
            return false;
        int i = 2;
        while (i < num) {
            if (num % i == 0)
                return false;
            i++;
        }
        return true;
    }

    private boolean checkPalindrome(int num) {
        String numText = "" + num;
        if (numText.length() == 1)
            return true;
        char[] digits = numText.toCharArray();
        int start = 0;
        int end = digits.length - 1;
        while (start < end) {
            if (digits[start] != digits[end])
                return false;
            start++;
            end--;
        }
        return true;
    }
}

public class Solution {

    public static void main(String[] args) throws IOException {
        MyMath ob = new MyMath();
        File file = new File("input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int T = Integer.parseInt(br.readLine());
        System.out.println("T = " + T);
        PerformOperation op;
        boolean ret = false;
        String ans = null;
        while (T-- > 0) {
            String s = br.readLine().trim();
            System.out.println("s = " + s);
            StringTokenizer st = new StringTokenizer(s);
            int ch = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());
            System.out.printf("ch = %d, num = %d\n", ch, num);
            if (ch == 1) {
                op = ob.isOdd();
                ret = ob.checker(op, num);
                ans = (ret) ? "ODD" : "EVEN";
            } else if (ch == 2) {
                op = ob.isPrime();
                ret = ob.checker(op, num);
                ans = (ret) ? "PRIME" : "COMPOSITE";
            } else if (ch == 3) {
                op = ob.isPalindrome();
                ret = ob.checker(op, num);
                ans = (ret) ? "PALINDROME" : "NOT PALINDROME";

            }
            System.out.println(ans);
        }
    }
}