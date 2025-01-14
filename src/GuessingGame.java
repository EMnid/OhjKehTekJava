import java.util.Random;
import java.util.Scanner;

    public class GuessingGame {
        public static void main(String[] args) {
            Random r = new Random();
            int value = r.nextInt(100) + 1;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Guess a number between 1 and 100: ");
            int number = scanner.nextInt();


            if (number == value) {
                System.out.println("Correct! It was " + value);
            } else if (number < value) {
                System.out.println("Too low! The correct number was: " + value);
            } else {
                System.out.println("Too high! The correct number was: " + value);
            }

            scanner.close();
        }
    }
