import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This program plays one round of yahtzee
 * This is the hand class
 * CPSC 224, Spring 2020
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Sophie Braun
 * 2/13/19
 */

public class Hand {
    Yahtzee game = new Yahtzee();
    private int numDie = game.getNumDice();
    private int numSides = game.getNumSides();
    private ArrayList<Integer> yahtzee_hand = new ArrayList<Integer>();

    /**
     * constructor that fills an array list with rolled die
     */
    public Hand() {
        if (yahtzee_hand.isEmpty()) {
            for (int i = 0; i < numDie; i++)
                yahtzee_hand.add(new Dice().get_sideOnTop());
        }
    }

    public ArrayList<Integer> getYahtzee_hand() {
        return yahtzee_hand;
    }

    public void setYahtzee_hand(ArrayList<Integer> yahtzee_hand) {
        this.yahtzee_hand = yahtzee_hand;
    }

    /**
     * rolls the dice selected again
     * @param index - the index of the die that needs to be rolled again
     * @return new_hand - the new hand with the correct dice re-rolled
     */
    public ArrayList<Integer> rollDiceAgain(int index) {
        ArrayList<Integer> new_hand = yahtzee_hand;
        Random randRef = new Random();
        int randRoll = randRef.nextInt(numSides + 0) + 1;
        new_hand.set(index, randRoll);
        return new_hand;
    }

    /**
     * gets the number of die in hand
     * @return number of die
     */
    public int getNumDice() {
        return numDie;
    }

    /**
     * sorts the hand
     */
    public void sortHand() {
        Collections.sort(yahtzee_hand);
    }

    /**
     * gets a random hand of yahtzee die
     * @return a hand of yahtzee
     */
    public ArrayList<Integer> randomHand() {
        return yahtzee_hand;
    }

    /**
     * overrides the object to print as a string
     * @return String to print out the hand
     */
    @Override
    public String toString() {
        String printedHand = "";
        for (int i = 0; i < numDie; i++) {
            printedHand += yahtzee_hand.get(i) + " ";
        }
        return printedHand;
    }
}
