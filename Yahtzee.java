import java.util.Scanner;

/**
 * This program plays one round of yahtzee
 * This is the Yahtzee class
 * CPSC 224, Spring 2020
 * Programming Assignment #1
 * No sources to cite.
 *
 * @author Sophie Braun
 * 2/3/19
 */

public class Yahtzee {
    private static int NUM_OF_DIE = 5;
    private static Scanner input = new Scanner(System.in);
    private static boolean playAgain = true;

    public static void main(String[] args) {

        while(playAgain) {
            int turnNum = 1;
            String userInput = "nnnnn";
            Hand diceRolled = new Hand();
            for (int j = 0; j < NUM_OF_DIE; j++) { // re-rolls for a new game
                diceRolled.rollDiceAgain(j);
            }
            while (turnNum < 3 && !userInput.equals("yyyyy")) {
                System.out.println("Your roll was: " + diceRolled);
                System.out.print("Enter dice to keep (y or n): ");
                userInput = input.next().toString();
                // validates input, must answer until it is valid
                while (!validInput(userInput)) {
                    System.out.println("Not valid input, please try again.\n");
                    System.out.print("Enter dice to keep (y or n): ");
                    userInput = input.next().toString();
                }
                // roll dice not kept
                for (int dieNumber = 0; dieNumber < NUM_OF_DIE; dieNumber++)
                {
                    if (userInput.charAt(dieNumber) != 'y')
                        diceRolled.rollDiceAgain(dieNumber);
                }
                turnNum++;
            }
            System.out.println("Your roll was: " + diceRolled);
            diceRolled.sortHand();
            System.out.println("Here is your sorted hand: " + diceRolled);

            Turn determineScore = new Turn(diceRolled.randomHand());
            System.out.println(determineScore);

            System.out.println("Enter yes to play again (or no to stop)");
            input.nextLine();
            String again = input.nextLine().toString();
            if (!again.equals("yes")) {
                playAgain = false;
                System.out.println("Thank you for playing!");
            }
            else {
                playAgain = true;
            }
        }
    }

    /**
     * takes user input and validates whether or not it is valid (only y and n)
     * @param userInput the String that the user enters in response to what dice they want to keep
     * @return true if its valid false otherwise
     */
    public static boolean validInput(String userInput) {
        if (userInput.length() == new Hand().getNumDice()) {
            for (int i = 0; i < userInput.length(); i++) {
                if (userInput.charAt(i) != 'y' && userInput.charAt(i) != 'n') {
                    return false;
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
}
