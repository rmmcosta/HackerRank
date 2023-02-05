import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.*;

public class Solution {

    public static boolean canWin(int leap, int[] game) {
        // Return true if you can win the game; otherwise, return false.
        return canMove(0, leap, game);
    }

    private static boolean canMove(int currPosition, int leap, int[] game) {
        if (currPosition >= game.length)
            return true;
        if (currPosition < 0 || game[currPosition] == 1)
            return false;
        // mark with one the positions were you already were
        game[currPosition] = 1;
        return canMove(currPosition + 1, leap, game) || canMove(currPosition + leap, leap, game)
                || canMove(currPosition - 1, leap, game);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File file = new File(args[0]);
        Scanner scan = new Scanner(file);
        File outFile = new File(args[1]);
        FileOutputStream fStream = new FileOutputStream(outFile);
        int q = scan.nextInt();
        while (q-- > 0) {
            int n = scan.nextInt();
            int leap = scan.nextInt();

            int[] game = new int[n];
            for (int i = 0; i < n; i++) {
                game[i] = scan.nextInt();
            }

            // System.out.println( q + "="+ ((canWin(leap, game)) ? "YES" : "NO" ));
            String outcome = (canWin(leap, game) ? "YES" : "NO") + "\n";
            byte[] binaryOutcome = outcome.getBytes();
            fStream.write(binaryOutcome);
        }
        fStream.close();
        scan.close();
    }
}