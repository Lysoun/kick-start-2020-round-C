import java.util.*;
import java.io.*;
public class Solution {
    private final static Map<Integer, Boolean> perfectSquares = new HashMap<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();

            Integer[] numbers = new Integer[n];
            for (int j = 0; j < n; ++j) {
                final int number = in.nextInt();
                numbers[j] = number;
            }

            System.out.println("Case #" + i + ": " + (countPerfectSubarrays(numbers)));
        }
    }

    private static long countPerfectSubarrays(Integer[] numbers) {
        long count = 0;

        for (int i = 0; i < numbers.length ; ++i) {
            int sum = 0;

            for (int j = i; j < numbers.length ; ++j) {
                sum += numbers[j];
                if (isPerfectSquare(sum)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean isPerfectSquare(int number) {
        if (number < 0) {
            return false;
        }

        if (perfectSquares.containsKey(number)) {
            return perfectSquares.get(number);
        }

        int i = 0;

        while(i * i < number) {
            ++i;
        }

        boolean result = i * i == number;
        perfectSquares.put(number, result);
        return result;
    }
}