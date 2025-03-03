package wk1_2_3;

import java.util.Scanner;

public class GuessingGame {
    public static void main(String[] args) {
        int value = (int) (Math.random() * 100) + 1; //Math.random gives 0.0-0.99, scale it

        Scanner scanner = new Scanner(System.in); //Scanner setup
        int attempts = 0;
        final int maxAttempts = 7; //Number of attempts locked at 7

        System.out.println("Guess a number between 1 and 100. You have " + maxAttempts + " tries!");

        while (attempts < maxAttempts) { // Loop for the number of maxAttempts
            System.out.print("Enter your guess: ");
            int number = scanner.nextInt();
            attempts++; // Increment the attempt counter

            if (number == value) {
                System.out.println("Correct! You guessed the number in " + attempts + " attempts.");
                break; // Exit the loop when the user guesses correctly, give the attempts count
            } else if (number < value) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }

            // Inform user of remaining attempts between incorrect guesses
            if (attempts < maxAttempts) {
                System.out.println("You have " + (maxAttempts - attempts) + " attempts left.");
            }
        }

        //Max attempts reached
        if (attempts == maxAttempts) {
            System.out.println("Game over! The correct number was: " + value);
        }

        scanner.close(); //Scanner exit
    }
}