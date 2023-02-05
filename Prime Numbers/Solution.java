import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Prime prime = new Prime();
            ArrayList<Integer> primes = new ArrayList<Integer>();
            String line = reader.readLine();
            while (line != null) {
                //System.out.println("line");
                try {
                    int num = Integer.parseInt(line);
                    if(prime.checkPrime(num)) {
                        primes.add(num);
                        printPrimes(primes);
                    }
                    else {
                        if(num%3==0 || num==1) {
                            //System.out.println("num: " + num);
                            printPrimes(primes);
                        }
                    }
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                line = reader.readLine();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void printPrimes(ArrayList<Integer> primes) {
        for(int currPrime:primes) {
            System.out.print(currPrime+" ");
        }
        System.out.println();
    }
}

class Prime {
    boolean checkPrime(int num) {
        if(num<=1) {
            return false;
        }
        int curr = 2;
        while(curr<num) {
            if(curr!=num && num%curr==0)
                return false;
            curr++;
        }
        return true;
    }
}