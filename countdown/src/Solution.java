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

    private static int countdown(Integer[] numbers, int countdownSize) {
        int i = numbers.length - 1;
        int countdownNumber = 0;

        while(i >= 0) {
            if (i - countdownSize + 1 < 0) {
                 i = -1;
            } else {
                if (numbers[i] == 1) {
                    boolean isCountdown = true;
                    int oneIndex = i;
                    int j = oneIndex - 1;
                    while (isCountdown && j >= oneIndex - countdownSize + 1) {
                        if (numbers[j] == 1) {
                            isCountdown = false;
                            i = j;
                        } else if (numbers[j] != (oneIndex - j + 1)) {
                            isCountdown = false;
                            i = j - 1;
                        } else {
                            --j;
                        }
                    }

                    if (isCountdown) {
                        i = j;
                        countdownNumber++;
                    }
                } else {
                    --i;
                }
            }
        }

        return countdownNumber;
    }
}
