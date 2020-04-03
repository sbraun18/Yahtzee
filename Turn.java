import java.util.ArrayList;

/**
 * This program plays one round of yahtzee
 * This is the Turn class that controls the scoring of a turn
 * CPSC 224, Spring 2020
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Sophie Braun
 * 2/13/19
 */

public class Turn {
    //private static ArrayList<Integer> scores;
    Yahtzee game = new Yahtzee();
    private ArrayList<Integer> hand = new ArrayList<>();
    public static ArrayList<Integer> scores = new ArrayList<>();
    public int numSides = game.getNumSides();
    public int numDie = game.getNumDice();
    public ArrayList<Element> cardList = game.getCardList();
    public static String lowerScorecard = "";
    public static String upperScorecard = "";

    /**
     * Constructor that tells the user they have an empty hand
     */
    public Turn() {
        System.out.println("empty hand");
    }

    /**
     * Constructor that creates a variable of the dice in hand
     * @param hand is an ArrayList with the dice faces rolled in hand
     */
    public Turn(ArrayList<Integer> hand) {
        this.hand = hand;
    }

    /**
     * this function represents the upper score card of the game Yahtzee
     * @return a String showing the upperScoreCard results
     */
    public String upperScoreCard(String upperScorecard) {
        for (int dieValue = 1; dieValue <= numSides; dieValue++) {
            int count = 0;
            for (int diePosition = 0; diePosition < numDie; diePosition++) {
                if (hand.get(diePosition) == dieValue) {
                    count++;
                }
            }
            if (cardList.get(dieValue - 1).getUsed() == 'n') {
                upperScorecard += "Score " + dieValue * count + "  on the " + dieValue + " line [" + dieValue + "]\n";
                scores.add(dieValue - 1, dieValue * count);
            }
        }

        return upperScorecard;
    };

    /**
     * represents the lower score card of the game Yahtzee
     * @return a String of the lowerScoreCard possible options
     */
    public String lowerScoreCard(String lowerScorecard) {
        if (cardList.get(numSides).getUsed() == 'n') {
            if (maxOfAKindFound() >= 3) {
                lowerScorecard += "Score " + sumOfDie() + " on the " + "3 of a Kind line [3K]\n";
                scores.add(numSides, sumOfDie());
            } else {
                lowerScorecard += "Score 0 on the 3 of a Kind line [3K]\n";
                scores.add(numSides, 0);
            }
        }
        if (cardList.get(numSides + 1).getUsed() == 'n') {
            if (maxOfAKindFound() >= 4) {
                lowerScorecard += "Score " + sumOfDie() + " on the " + "4 of a Kind line [4K]\n";
                scores.add(numSides + 1, sumOfDie());
            } else {
                lowerScorecard += "Score 0 on the 4 of a Kind line [4K]\n";
                scores.add(numSides + 1, 0);
            }
        }
        if (cardList.get(numSides + 2).getUsed() == 'n') {
            if (fullHouseFound()) {
                lowerScorecard += "Score 25 on the Full House line [FH]\n";
                scores.add(numSides + 2, 25);
            } else {
                lowerScorecard += "Score 0 on the Full House line [FH]\n";
                scores.add(numSides + 2, 0);
            }
        }
        if (cardList.get(numSides + 3).getUsed() == 'n') {
            if (maxStraightFound() >= 4) {
                lowerScorecard += "Score 30 on the Small Straight line [SS]\n";
                scores.add(numSides + 3, 30);
            } else {
                lowerScorecard += "Score 0 on the Small Straight line [SS]\n";
                scores.add(numSides + 3, 0);
            }
        }
        if (cardList.get(numSides + 4).getUsed() == 'n') {
            if (maxStraightFound() >= 5) {
                lowerScorecard += "Score 40 on the Large Straight line [LS]\n";
                scores.add(numSides + 4, 40);
            } else {
                lowerScorecard += "Score 0 on the Large Straight line [LS]\n";
                scores.add(numSides + 4, 0);
            }
        }
        if (cardList.get(numSides + 5).getUsed() == 'n') {
            if (maxOfAKindFound() >= 5) {
                lowerScorecard += "Score 50 on the Yahtzee line [Y]\n";
                scores.add(numSides + 5, 50);
            } else {
                lowerScorecard += "Score 0 on the Yahtzee line [Y]\n";
                scores.add(numSides + 5, 0);
            }
        }
        if (cardList.get(numSides + 6).getUsed() == 'n') {
            lowerScorecard += "Score " + sumOfDie() + " on the Chance line [C]";
            scores.add(numSides + 6, sumOfDie());
        }

        return lowerScorecard;
    }

    /**
     * determines the max number of a kind is found in a hand
     * @return maxCount - the maximum number of repeated numbers in hand
     */
    private int maxOfAKindFound() {
        int max = 0;
        int count;
        for (int dieValue = 1; dieValue <= numSides; dieValue++) {
            count = 0;
            for (int diePosition = 0; diePosition < numDie; diePosition++) {
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
        if (numDie < 4) {
            return 0;
        }
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
        if (numDie < 4) {
            return false;
        }
        boolean fullHouse = false;
        boolean threeOfKind = false;
        boolean twoOfKind = false;
        for (int dieNum = 1; dieNum <= numSides; dieNum++) {
            int count = 0;
            for (int diePosition = 0; diePosition < numDie; diePosition++) {
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
        for (int diePosition = 0; diePosition < numDie; diePosition++)
        {
            sum += hand.get(diePosition);
        }
        return sum;
    }

    public static String getLowerScorecard() {
        return lowerScorecard;
    }

    public void setLowerScorecard(String lowerScorecard) {
        this.lowerScorecard = lowerScorecard;
    }

    public String getUpperScorecard() {
        return upperScorecard;
    }

    public void setUpperScorecard(String upperScorecard) {
        this.upperScorecard = upperScorecard;
    }

    public static ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return upperScoreCard(upperScorecard) + lowerScoreCard(lowerScorecard);
    }
}
