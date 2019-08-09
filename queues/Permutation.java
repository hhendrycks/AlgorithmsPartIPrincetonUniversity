/* *****************************************************************************
 *  Name: H. H.
 *  Date: 3/1/19
 *  Description: Takes input file and enqueues each string to a randomized
 *  queue. Each string is then printed out in random order.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        int num = Integer.parseInt(args[0]);
        double inputSize = 0.0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals(" ") && num != 0) {
                if (randQueue.size() < num) {
                    randQueue.enqueue(item);
                }
                else if (StdRandom.uniform(0, inputSize + 1) <= num) {
                    // uses the principal of resevior sampling
                    randQueue.dequeue();
                    randQueue.enqueue(item);
                }
            }
            inputSize++;
        }
        for (String str : randQueue) {
            StdOut.println(str);
        }
    }
}
