import java.util.ArrayList;

/**
 * This program plays one round of yahtzee
 * This is the Turn class that controls the scoring of a turn
 * CPSC 224, Spring 2020
 * Programming Assignment #1
 * No sources to cite.
 *
 * @author Sophie Braun
 * 2/3/19
 */

public class Turn {
    private static ArrayList<Integer> hand = new ArrayList<>();

    /**
     * Constructor that tells the user they have an empty hand
     */
    public Turn() {
        System.out.println("empty hand");
    }

    /**
     * Constructor that creates a variable of the dice in hand
     * @param: the dice faces rolled in hand
     */
    public Turn(ArrayList<Integer> hand) {
        this.hand = hand;
    }

    /**
     * this function represents the upper score card of the game Yahtzee
     * @return a String showing the upperScoreCard results
     */
    private String upperScoreCard() {
        String upperScoreCard = "";
        for (int dieValue = 1; dieValue <= 6; dieValue++) {
            int count = 0;
            for (int diePosition = 0; diePosition < 5; diePosition++) {
                if (hand.get(diePosition) == dieValue) {
                    count++;
                }
            }
            upperScoreCard +=  "Score " + dieValue * count + "  on the " + dieValue + " line\n";
        }
        return upperScoreCard;
    };

    /**
     * represents the lower score card of the game Yahtzee
     * @return a String of the lowerScoreCard possible options
     */
    private String lowerScoreCard() {
        String lowerScoreCard = "";

        if (maxOfAKindFound() >= 3)
            lowerScoreCard += "Score " + sumOfDie() + " on the " + "3 of a Kind line\n";
        else
            lowerScoreCard += "Score 0 on the 3 of a Kind line\n";

        if (maxOfAKindFound() >= 4)
            lowerScoreCard += "Score " + sumOfDie() + " on the " + "4 of a Kind line\n";
        else
            lowerScoreCard += "Score 0 on the 4 of a Kind line\n";

        if (fullHouseFound())
            lowerScoreCard += "Score 25 on the Full House line\n";
        else
            lowerScoreCard += "Score 0 on the Full House line\n";

        if (maxStraightFound() >= 4)
            lowerScoreCard += "Score 30 on the Small Straight line\n";
        else
            lowerScoreCard += "Score 0 on the Small Straight line\n";

        if (maxStraightFound() >= 5)
            lowerScoreCard += "Score 40 on the Large Straight line\n";
        else
            lowerScoreCard += "Score 0 on the Large Straight line\n";

        if (maxOfAKindFound() >= 5)
            lowerScoreCard += "Score 50 on the Yahtzee line\n";
        else
            lowerScoreCard += "Score 0 on the Yahtzee line\n";

        lowerScoreCard += "Score " + sumOfDie() + " on the Chance line";

        return lowerScoreCard;
    }

    /**
     * determines the max number of a kind is found in a hand
     * @return maxCount - the maximum number of repeated numbers in hand
     */
    private int maxOfAKindFound() {
        int max = 0;
        int count;
        for (int dieValue = 1; dieValue <= 6; dieValue++) {
            count = 0;
            for (int diePosition = 0; diePosition < 5; diePosition++) {
                if (hand.get(diePosition) == dieValue) {
                    count++;
                }
            }
            if (count > max) {
                max = count;
            }
        }
        return max;
    }

    /**
     * finds the max number of a straight
     * @return maxLength - the max length of a straight
     */
    private int maxStraightFound() {
        int maxLength = 1;
        int curLength = 1;
        for(int count = 0; count < 4; count++) {
            if (hand.get(count) + 1 == hand.get(count + 1)) {
                curLength++;
            }
            else if (hand.get(count) + 1 < hand.get(count + 1)) {
                curLength = 1;
            }
            if (curLength > maxLength) {
                maxLength = curLength;
            }
        }
        return maxLength;
    }

    /**
     * checks for a full house
     * @return true if there is a full house, false if not
     */
    private boolean fullHouseFound() {
        boolean fullHouse = false;
        boolean threeOfKind = false;
        boolean twoOfKind = false;
        for (int dieNum = 1; dieNum <=6; dieNum++) {
            int count = 0;
            for (int diePosition = 0; diePosition < 5; diePosition++) {
                if (hand.get(diePosition) == dieNum) {
                    count++;
                }
            }
            if (count == 2) {
                twoOfKind = true;
            }
            if (count == 3) {
                threeOfKind = true;
            }
        }
        if (twoOfKind && threeOfKind) {
            fullHouse = true;
        }
        return fullHouse;
    }

    /**
     * Sums the dice in the hand
     * @return the sum of the dice
     */
    private int sumOfDie() {
        int sum = 0;
        for (int diePosition = 0; diePosition < 5; diePosition++)
        {
            sum += hand.get(diePosition);
        }
        return sum;
    }

    @Override
    public String toString() {
        return upperScoreCard() + lowerScoreCard();
    }
}
