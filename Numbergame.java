import java.util.Random;
import java.util.Scanner;

public class Numbergame {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 100;
    private static final int MAX_ATTEMPTS = 7;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Random random = new Random();
            int totalAttempts = 0;
            int roundsWon = 0;
            
            while (true) {
                int numberToGuess = generateNumber(random);
                int attempts = playRound(scanner, numberToGuess);
                
                totalAttempts += attempts;
                if (attempts < MAX_ATTEMPTS) {
                    roundsWon++;
                }
                
                System.out.println("Rounds won: " + roundsWon);
                System.out.println("Total attempts: " + totalAttempts);
                
                System.out.print("Do you want to play again? (yes/no): ");
                String playAgain = scanner.next().trim().toLowerCase();
                if (!playAgain.equals("yes")) {
                    break;
                }
            }
            
            System.out.println("Thank you for playing!");
            System.out.println("Final Score: Rounds won: " + roundsWon + ", Total attempts: " + totalAttempts);
        }
    }

    private static int generateNumber(Random random) {
        return random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
    }

    private static int getUserGuess(Scanner scanner) {
        while (true) {
            System.out.print("Enter your guess: ");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static int playRound(Scanner scanner, int numberToGuess) {
        int attempts = 0;

        System.out.println("Guess the number between " + MIN_VALUE + " and " + MAX_VALUE + ". You have " + MAX_ATTEMPTS + " attempts.");

        while (attempts < MAX_ATTEMPTS) {
            int guess = getUserGuess(scanner);
            attempts++;

            if (guess < numberToGuess) {
                System.out.println("Too low!");
            } else if (guess > numberToGuess) {
                System.out.println("Too high!");
            } else {
                System.out.println("Congratulations! You guessed the number " + numberToGuess + " in " + attempts + " attempts.");
                return attempts; // Return the number of attempts used
            }
        }

        System.out.println("Sorry, you've used all " + MAX_ATTEMPTS + " attempts. The number was " + numberToGuess + ".");
        return MAX_ATTEMPTS; // Return MAX_ATTEMPTS to indicate failure
    }
}
