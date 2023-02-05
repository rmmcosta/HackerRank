import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        //grab from the input stream the data and bring it to memory
        //sample input
        //5
        //1 -2 4 -5 1
        Scanner sc = new Scanner(System.in);
        int arrLen = sc.nextInt();
        int[] arr = new int[arrLen];
        for(int i=0;i<arrLen;i++)
            arr[i] = sc.nextInt();
        sc.close();
        
        //print the num of subarrays having negative sums
        int negativeSubArrays = 0;
        int start = 0;
        int end=0;
        int delta=0;
        int currSum;
        //for the subarrays we want a delta less than arrlen
        while(delta<arrLen) {
            while(end<arrLen) {
                currSum = 0;
                for(int i=start;i<=end;i++)
                    currSum+=arr[i];
                if(currSum<0)
                    negativeSubArrays++;
                start++;
                end=start+delta;
            }
            delta++;
            start=0;
            end=start+delta;
        }
        
        System.out.println(negativeSubArrays);
    }
}