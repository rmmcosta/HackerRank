import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner in = new Scanner(file);
        Deque<Integer> deque = new ArrayDeque<>();
        int n = in.nextInt();
        int m = in.nextInt();

        for (int i = 0; i < n; i++) {
            int num = in.nextInt();
            deque.push(num);  
        }
        
        int maxDiff = 0;
        while(!deque.isEmpty()) {
            int diffs = 0;
            for(int i=0;i<m;i++) {
                //compare head an tail and remove from the head to the tail
                //it i=0 only remove and increments diffs
                //else only increments iffs if head different from tail
                if(i==0) {
                    diffs++;
                }
                else {
                    if(deque.getFirst()!=deque.getLast())
                        diffs++;
                }
                deque.addLast(deque.removeFirst());    
            }
            if(diffs>maxDiff)
                maxDiff = diffs;
            //undo the changes made to the queue and discard the firts element changed
            for(int i=1;i<m;i++) {
                deque.addFirst(deque.removeLast());
            }
            deque.removeLast();//the firts element changed
        }
        
        System.out.println(maxDiff);
    }
}
