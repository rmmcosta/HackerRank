import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;



public class Solution {
    private static final int MAX_X = 6;
    private static final int MAX_Y = 6;
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        List<List<Integer>> arr = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            String row = bufferedReader.readLine().replaceAll("\\s+$", "");
            //System.out.println("row: " + row);
            String[] arrRowTempItems = row.split(" ");

            List<Integer> arrRowItems = new ArrayList<>();

            for (int j = 0; j < 6; j++) {
                int arrItem = Integer.parseInt(arrRowTempItems[j]);
                arrRowItems.add(arrItem);
            }

            arr.add(arrRowItems);
        }

        bufferedReader.close();
        
        //now lets identify hourglasses and calculate the final sum
        int biggestSum = -63;//-9*7
        int y=0;
        while(y<=MAX_Y-3) {
            int x=0;
            while(x<=MAX_X-3) {
                int value1=arr.get(y).get(x);
                int value2=arr.get(y).get(x+1);
                int value3=arr.get(y).get(x+2);
                int value4=0;
                int value5=arr.get(y+1).get(x+1);
                int value6=0;
                int value7=arr.get(y+2).get(x);
                int value8=arr.get(y+2).get(x+1);
                int value9=arr.get(y+2).get(x+2);
                int tempBiggestSum = value1+value2+value3+value4+value5+value6+value7+value8+value9;
                System.out.println(tempBiggestSum);
                if(tempBiggestSum>biggestSum)
                    biggestSum = tempBiggestSum;
                x++;
            }
            y++;
        }
        
        System.out.println(biggestSum);
    }
}