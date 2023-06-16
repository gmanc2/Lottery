import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;

public class LotteryMain {

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            LotteryGame calculate = new LotteryGame();
            String replay;
            do {
                calculate.Player(input);
                calculate.Computer();
                calculate.CorrectGuesses();
                calculate.Printout();
                System.out.println("\u001B[91mDo you want to play again? (yes or no)\033[0m");
                replay = input.nextLine().replaceAll("\\s+", "");
                while (!replay.equalsIgnoreCase("yes") && !replay.equalsIgnoreCase("no")) {
                    System.out.println("Invalid input. Please enter yes or no.");
                    replay = input.nextLine().replaceAll("\\s+", "");
                }
            } while (!replay.equalsIgnoreCase("no"));
        }
        System.exit(0);
    }

}

class LotteryGame {
    int[] player = new int[4];
    int[] computer = new int[4];
    int correctGuesses;
    int totalMatches;

    public void Player(Scanner input) {
        System.out.println("Please enter 4 unique numbers between 1 and 40:");
        Set<Integer> chosenNumbers = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            System.out.printf("Number %d: ", i+1);
            try {
                int number = input.nextInt();
                String numberString = String.valueOf(number);
                numberString = numberString.replaceAll( "\\s+", "");
                number = Integer.parseInt(numberString);
                while (number < 1 || number > 40 || chosenNumbers.contains(number)) {
                    if (chosenNumbers.contains(number)) {
                        System.out.println("Number already chosen. Please choose a unique number.");
                    } else {
                        System.out.println("Invalid number. Please choose a unique number between 1 and 40.");
                    }
                    System.out.printf("Number %d: ", i+1);
                    number = input.nextInt();
                }
                System.out.printf("You chose %d.%n", number);
                player[i] = number;
                chosenNumbers.add(number);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid unique integer between 1 and 40.");
                input.nextLine();
                i--;
            }
        }
        input.nextLine();
    }

    public void Computer() {
        Random rand = new Random();
        Set<Integer> generatedNumbers = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            int number = rand.nextInt(40) + 1;
            while (generatedNumbers.contains(number)) {
                number = rand.nextInt(40) + 1;
            }
            computer[i] = number;
            generatedNumbers.add(number);
            
        }
    }

    public void CorrectGuesses() {
        correctGuesses = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (player[i] == computer[j]) {
                    correctGuesses++;
                    totalMatches++;
                }
            }
        }
    }

    public void Printout() {
        String chosenNumbers = "Chosen numbers: " + Arrays.toString(player);
        String lotteryNumbers = "Lottery numbers: " + Arrays.toString(computer);
        String correctGuessesS = "Correct Guesses!: " + correctGuesses;
        String totalMatchesS = "Total Matches: " + totalMatches;
        int maxLength = Math.max(Math.max(Math.max(chosenNumbers.length(), lotteryNumbers.length()), correctGuessesS.length()), totalMatchesS.length());
        String niceBorder = "+" + "-".repeat(maxLength + 2) + "+";
        System.out.println(niceBorder);
        System.out.printf("| \033[34m %-"+ maxLength +"s\033[0m |%n", chosenNumbers);
        System.out.printf("| \033[31m %-" + maxLength + "s\033[0m |%n", lotteryNumbers);
        System.out.printf("| \033[36m %-" + maxLength + "s\033[0m |%n", correctGuessesS);
        System.out.printf("| \033[35m %-" + maxLength + "s\033[0m |%n", totalMatchesS);
        System.out.println(niceBorder);
    }
}