import java.util.Random;

/**
 * This program plays one round of yahtzee
 * This is the dice class
 * CPSC 224, Spring 2020
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Sophie Braun
 * 2/13/19
 */

public class Dice {
    Yahtzee game = new Yahtzee();
    private int numSides = game.getNumSides();
    private static int side_up;
    private int[] die_faces;

    /**
     * constructor of a dice class
     */
    public Dice() {
        die_faces = new int[numSides];
        for(int ctr = 0; ctr < numSides; ctr++) {
            die_faces[ctr] = ctr + 1;
        }
        side_up = rollDie();
    }

    /**
     *  rolls a die
     * @return an int of the face rolled
     */
    public int rollDie() {
        Random randRef = new Random();
        int randRoll = randRef.nextInt(numSides-1) + 1;  //1-7
        return die_faces[randRoll-1];
    }

    /**
     *  returns the side of the die that is up
     * @return an int of the side up
     */
    public int get_sideOnTop() {
        return side_up;
    }

    /**
     * allows the String to be printed
     * @return a String of the side rolled
     */
    @Override
    public String toString() {
        return String.valueOf(side_up);
    }
}
