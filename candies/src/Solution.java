import java.util.*;
import java.io.*;
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            int countdownSize = in.nextInt();

            Integer[] numbers = new Integer[n];
            for (int j = 0; j < n; ++j) {
                numbers[j] = in.nextInt();
            }

            System.out.println("Case #" + i + ": " + (countdown(numbers, countdownSize)));
        }
    }
}