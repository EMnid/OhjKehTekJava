package wk1_2_3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Print the command-line arguments:
        System.out.println("Command-line arguments:");
        for (int i = 0; i < args.length; i++) {
            System.out.println("\t" + i + ": \"" + args[i] + "\"");
        }
        System.out.println();

        // Show the array of command-line arguments as one string:
        System.out.print("Argument array converted to a string: ");
        System.out.println(Arrays.toString(args));
        System.out.println();

        // Try to convert them into integers.
        // Also sum all the valid numbers.
        int sum = 0;
        int[] numbers = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                numbers[i] = Integer.parseInt(args[i]);
                sum += numbers[i];
            } catch (NumberFormatException nfe) {
                System.err.println("Not a number: \"" + args[i] + "\"");
            }
        }

        // Show the conversion result:
        System.out.print("Array of integers converted to a string: ");
        System.out.println(Arrays.toString(numbers));
        System.out.println();

        // Show the sum:
        System.out.println(String.format("wk1_2_3.Sum of arguments = %d", sum));
    }
}
