import java.util.Random;

/**
 * This program plays one round of yahtzee
 * This is the dice class
 * CPSC 224, Spring 2020
 * Programming Assignment #1
 * No sources to cite.
 *
 * @author Sophie Braun
 * 2/3/19
 */

public class Dice {

    public static int DIE_SIDES = 6;
    private static int side_up;
    private static int[] die_faces;

    /**
     * constructor of a dice class
     */
    public Dice() {
        die_faces = new int[] {1, 2, 3, 4, 5, 6};
        side_up = rollDie();
    }

    /**
     *  rolls a die
     */
    public static int rollDie() {
        Random randRef = new Random();
        int randRoll = randRef.nextInt(DIE_SIDES);
        return die_faces[randRoll];
    }

    /**
     *  returns the side of the die that is up
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
