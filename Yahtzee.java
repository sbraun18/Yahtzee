import javafx.fxml.FXMLLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This program plays one round of yahtzee
 * This is the Yahtzee class
 * CPSC 224, Spring 2020
 * Programming Assignment #2
 * No sources to cite.
 *
 * @author Sophie Braun
 * 2/13/19
 */

public class Yahtzee {
    private static int numSides;
    private static int numDice;
    private static int numRolls;
    private static Scanner inFile = null;
    private static Scanner input = new Scanner(System.in);
    private static boolean playAgain = true;
    private static ArrayList<Integer> checkNums = new ArrayList<>();
    private static ArrayList<Element> cardList = new ArrayList<>();
    private static int subTotal = 0;
    private static int upperTotal = 0;
    private static int lowerTotal = 0;
    private static int grandTotal = 0;
    private static int bonus = 0;
    private static boolean roll = false;

    public static void main(String[] args) {
        try {
            inFile = new Scanner(new java.io.File("yahtzeeConfig.txt"));
            int line1 = inFile.nextInt();
            numSides = line1;
            int line2 = inFile.nextInt();
            numDice = line2;
            int line3 = inFile.nextInt();
            numRolls = line3;
            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        createScorecard(numSides);
        while (playAgain) {
            if (isFull()) {
                System.out.println("Game over, thanks for playing!");
                System.out.println("Would you like to play again? (enter yes to play again or anything else to quit)");
                String again = input.nextLine();
                if (!again.equals("yes")) {
                    playAgain = false;
                    System.out.println("Thank you for playing!");
                    break;
                } else {
                    //SOMEHOW START GAME OVER
                    playAgain = true;
                }
            } else {
                playAgain = true;
            }
            System.out.println("You are playing with " + numDice + " " + numSides + "-sided dice");
            System.out.println("You get " + numRolls + " rolls per hand");
            System.out.println("Enter 'y' if you would like to change the configuration");
            String dieInput = "n";
            dieInput = input.next();
            if (dieInput.equals("y")) {
                System.out.println("enter the number of sides on each die");
                String userSides;
                userSides = input.next();
                System.out.println("enter the number of dice in play");
                String userDice;
                userDice = input.next();
                System.out.println("enter the number of rolls per hand");
                String userRolls;
                userRolls = input.next();

                numSides = Integer.parseInt(userSides);
                numDice = Integer.parseInt(userDice);
                numRolls = Integer.parseInt(userRolls);

                try {
                    PrintStream outFile = new PrintStream(new java.io.File("yahtzeeConfig.txt"));
                    outFile.println(userSides + "\n" + userDice + "\n" + userRolls);
                    outFile.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            int turnNum = 1;
            String userInput = "n";
            Hand diceRolled = new Hand();
            for (int j = 0; j < numDice; j++) { // re-rolls for a new game
                diceRolled.rollDiceAgain(j);
            }
            //while (turnNum < numRolls) {
                int count = 0;
                for (int i = 0; i < userInput.length(); i++) {
                    if (userInput.charAt(i) == 'y') {
                        count++;
                    }
                }
                if (count == userInput.length()) {
                    break;
                }
                System.out.println("Your roll was: " + diceRolled);
                //EventQueue.invokeLater(() ->
                //{
                new YahtzeeFrame(diceRolled);
                //});
                //while (roll == false) {
                    // keep program stuck here
                //}
                //userInput = input.next();
                //System.out.println("Your roll was: " + DisplayDice(diceRolled));
                /*System.out.print("Enter dice to keep (y or n) or 'S' to see the scorecard: ");
                userInput = input.next();
                // validates input, must answer until it is valid
                while (!validInput(userInput)) {
                    System.out.println("Not valid input, please try again.\n");
                    System.out.print("Enter dice to keep (y or n) or 'S' to see the scorecard: ");
                    userInput = input.next();
                }
                if (userInput.matches("S")) {
                    printScoreCard();
                    System.out.print("Enter dice to keep (y or n) or 'S' to see the scorecard: ");
                    userInput = input.next();
                }
                while (!validInput(userInput)) {
                    System.out.println("Not valid input, please try again.\n");
                    System.out.print("Enter dice to keep (y or n) or 'S' to see the scorecard: ");
                    userInput = input.next();
                }*/
                // roll dice not kept
                /*for (int dieNumber = 0; dieNumber < numDice; dieNumber++)
                {
                    if (userInput.charAt(dieNumber) == 'n')
                        diceRolled.rollDiceAgain(dieNumber);
                }*/
                turnNum++;
            //}
            System.out.println("Your roll was: " + diceRolled);
            diceRolled.sortHand();
            System.out.println("Here is your sorted hand: " + diceRolled);

            Turn determineScore = new Turn(diceRolled.randomHand());
            System.out.println(determineScore);

            System.out.println("Select the score you would like to keep for this turn [enter what is in the hard brackets]");
            input.nextLine();
            String scoreInput = input.nextLine().toString();
            scoreOutput(scoreInput);
        }
    }

    static class YahtzeeFrame extends JFrame {
        //private static final int DEFAULT_WIDTH = 500;
        //private static final int DEFAULT_HEIGHT = 300;
        JLabel image1, image2, image3, image4, image5, image6;
        JPanel centerPanel = new JPanel();
        JPanel contentPanel;

        public YahtzeeFrame(Hand myHand) {
            setVisible(true);
            setLocation(400, 150);
            setPreferredSize(new Dimension(800, 500));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            centerPanel.setLayout(new BorderLayout());
            int turn = 0;
            setUp(contentPanel, myHand);
            add(contentPanel);
            pack();
        }

        private void setUp(JPanel contentPanel, Hand myHand) {
            //center south
            // add the check boxes
            JPanel buttonPanel = new JPanel();

            JCheckBox dice1 = new JCheckBox("Dice 1");
            //dice1.addActionListener(listener);
            buttonPanel.add(dice1);
            JCheckBox dice2 = new JCheckBox("Dice 2");
            //dice2.addActionListener(listener);
            buttonPanel.add(dice2);
            JCheckBox dice3 = new JCheckBox("Dice 3");
            //dice3.addActionListener(listener);
            buttonPanel.add(dice3);
            JCheckBox dice4 = new JCheckBox("Dice 4");
            //dice4.addActionListener(listener);
            buttonPanel.add(dice4);
            JCheckBox dice5 = new JCheckBox("Dice 5");
            //dice5.addActionListener(listener);
            buttonPanel.add(dice5);
            centerPanel.add(buttonPanel, BorderLayout.SOUTH);

            //south
            JButton rollButton = new JButton("Roll");
            contentPanel.add(rollButton, BorderLayout.SOUTH);

            rollButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //center(contentPanel, myHand);
                    if (dice1.isSelected()) {
                        updateDice(myHand.rollDiceAgain(0));
                        //setUp(contentPanel, myHand);
                    }
                    if (dice2.isSelected()) {
                        updateDice(myHand.rollDiceAgain(1));
                        //setUp(contentPanel, myHand);
                    }
                    if (dice3.isSelected()) {
                        updateDice(myHand.rollDiceAgain(2));
                        //setUp(contentPanel, myHand);
                    }
                    if (dice4.isSelected()) {
                        updateDice(myHand.rollDiceAgain(3));
                        //setUp(contentPanel, myHand);
                    }
                    if (dice5.isSelected()) {
                        updateDice(myHand.rollDiceAgain(4));
                        //setUp(contentPanel, myHand);
                    }
                }
            });

            // center
            //int turn = 1;
            ArrayList<Integer> hand = new ArrayList<Integer>();
            hand = myHand.getYahtzee_hand();
            /*JPanel dicePanel = new JPanel();
            for (int i = 0; i < myHand.getNumDice(); i++) {
                if (hand.get(i) == 1) {
                    image1 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/1up.jpg"));
                    dicePanel.add(image1);
                } else if (hand.get(i) == 2) {
                    image2 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/2up.png"));
                    dicePanel.add(image2);
                } else if (hand.get(i) == 3) {
                    image3 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/3up.png"));
                    dicePanel.add(image3);
                } else if (hand.get(i) == 4) {
                    image4 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/4up.png"));
                    dicePanel.add(image4);
                } else if (hand.get(i) == 5) {
                    image5 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/5up.png"));
                    dicePanel.add(image5);
                } else if (hand.get(i) == 6) {
                    image6 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/6up.png"));
                    dicePanel.add(image6);
                }
                centerPanel.add(dicePanel, BorderLayout.CENTER);
            } */
            updateDice(hand);
            contentPanel.add(centerPanel, BorderLayout.CENTER);
        }

        private void updateDice(ArrayList<Integer> dice) {
            JPanel dicePanel = new JPanel();
            for (int i = 0; i < dice.size(); i++) {
                if (dice.get(i) == 1) {
                    image1 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/1up.jpg"));
                    dicePanel.add(image1);
                } else if (dice.get(i) == 2) {
                    image2 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/2up.png"));
                    dicePanel.add(image2);
                } else if (dice.get(i) == 3) {
                    image3 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/3up.png"));
                    dicePanel.add(image3);
                } else if (dice.get(i) == 4) {
                    image4 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/4up.png"));
                    dicePanel.add(image4);
                } else if (dice.get(i) == 5) {
                    image5 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/5up.png"));
                    dicePanel.add(image5);
                } else if (dice.get(i) == 6) {
                    image6 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/6up.png"));
                    dicePanel.add(image6);
                }
            }
            centerPanel.add(dicePanel, BorderLayout.CENTER);
            //contentPanel.add(centerPanel, BorderLayout.CENTER);
        }
    }

    /*private static void south(JPanel contentPanel, Hand myHand) {
        JButton rollButton = new JButton("Roll");
        contentPanel.add(rollButton, BorderLayout.SOUTH);

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //center(contentPanel, myHand);
                int count = 0;
                if (!dice1.isSelected()) {
                    checkNums.add(count, 1);
                    count++;
                }
                if (!dice2.isSelected()) {
                    checkNums.add(count, 2);
                    count++;
                }
                if (!dice3.isSelected()) {
                    checkNums.add(count, 3);
                    count++;
                }
                if (!dice4.isSelected()) {
                    checkNums.add(count, 4);
                    count++;
                }
                if (!dice5.isSelected()) {
                    checkNums.add(count, 5);
                    count++;
                }
            }
        });
    }

    private static void center(JPanel contentPanel, Hand myHand) {
        int turn = 1;
        ArrayList<Integer> hand = new ArrayList<Integer>();
        hand = myHand.getYahtzee_hand();
        JPanel centerPanel = new JPanel();
        JLabel image1, image2, image3, image4, image5, image6;
        if (checkNums.size() > 0) {
            for (int i = 0; i < checkNums.size(); i++) {
                if (checkNums.get(i) == 1) {
                    myHand.rollDiceAgain(0);
                } else if (checkNums.get(i) == 2) {
                    myHand.rollDiceAgain(1);
                } else if (checkNums.get(i) == 3) {
                    myHand.rollDiceAgain(2);
                } else if (checkNums.get(i) == 4) {
                    myHand.rollDiceAgain(3);
                } else if (checkNums.get(i) == 5) {
                    myHand.rollDiceAgain(4);
                }
                i++;
            }
        }
        for (int i = 0; i < myHand.getNumDice(); i++) {
            if (hand.get(i) == 1) {
                image1 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/1up.jpg"));
                centerPanel.add(image1);
            } else if (hand.get(i) == 2) {
                image2 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/2up.png"));
                centerPanel.add(image2);
            } else if (hand.get(i) == 3) {
                image3 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/3up.png"));
                centerPanel.add(image3);
            } else if (hand.get(i) == 4) {
                image4 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/4up.png"));
                centerPanel.add(image4);
            } else if (hand.get(i) == 5) {
                image5 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/5up.png"));
                centerPanel.add(image5);
            } else if (hand.get(i) == 6) {
                image6 = new JLabel(new ImageIcon("/Users/sophiabraun/Desktop/Yahtzee/src/6up.png"));
                centerPanel.add(image6);
            }
        }
        for (int i = 0; i < checkNums.size(); i++) {
            checkNums.remove(i);
        }
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        centerSouth(contentPanel, centerPanel);
    }

    private static void centerSouth(JPanel contentPanel, JPanel centerPanel) {
        // add the check boxes
        JPanel buttonPanel = new JPanel();

        JCheckBox dice1 = new JCheckBox("Dice 1");
        //dice1.addActionListener(listener);
        buttonPanel.add(dice1);
        JCheckBox dice2 = new JCheckBox("Dice 2");
        //dice2.addActionListener(listener);
        buttonPanel.add(dice2);
        JCheckBox dice3 = new JCheckBox("Dice 3");
        //dice3.addActionListener(listener);
        buttonPanel.add(dice3);
        JCheckBox dice4 = new JCheckBox("Dice 4");
        //dice4.addActionListener(listener);
        buttonPanel.add(dice4);
        JCheckBox dice5 = new JCheckBox("Dice 5");
        //dice5.addActionListener(listener);
        buttonPanel.add(dice5);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        ActionListener listener = event -> {
            int mode = 0;
            int count = 0;
            if (!dice1.isSelected()) {
                checkNums.add(count, 1);
                count++;
            }
            if (!dice2.isSelected()) {
                checkNums.add(count, 2);
                count++;
            }
            if (!dice3.isSelected()) {
                checkNums.add(count, 3);
                count++;
            }
            if (!dice4.isSelected()) {
                checkNums.add(count, 4);
                count++;
            }
            if (!dice5.isSelected()) {
                checkNums.add(count, 5);
                count++;
            }
        };
    } */

        /**
         * creates the scorecard in the text file and the array cardList
         *
         * @param sides takes the number of sides in as a param
         */
        public static void createScorecard(int sides) {
            try {
                PrintStream outFile = new PrintStream(new java.io.File("scorecard.txt"));
                //loop for upper section
                for (int j = 1; j <= sides; j++) {
                    outFile.print(j + ",n,u,0\n");
                }
                //individual writes for lower section
                outFile.print("3K,n,l,0\n");
                outFile.print("4K,n,l,0\n");
                outFile.print("FH,n,l,0\n");
                outFile.print("SS,n,l,0\n");
                outFile.print("LS,n,l,0\n");
                outFile.print("Y,n,l,0\n");
                outFile.print("C,n,l,0\n");
                outFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String name = "";
            char used = 'n';
            char section = 'u';
            int score = 0;
            for (int i = 0; i < numSides + 7; i++) {
                Element e = new Element(name, section, used, score);
                if (i < numSides) {
                    e.name = Integer.toString(i + 1);
                    e.used = 'n';
                    e.section = 'u';
                    e.score = 0;
                    cardList.add(i, e);
                } else if (i == numSides) {
                    e.name = "3K";
                    e.used = 'n';
                    e.section = 'l';
                    e.score = 0;
                    cardList.add(i, e);
                } else if (i == numSides + 1) {
                    e.name = "4K";
                    e.used = 'n';
                    e.section = 'l';
                    e.score = 0;
                    cardList.add(i, e);
                } else if (i == numSides + 2) {
                    e.name = "FH";
                    e.used = 'n';
                    e.section = 'l';
                    e.score = 0;
                    cardList.add(i, e);
                } else if (i == numSides + 3) {
                    e.name = "SS";
                    e.used = 'n';
                    e.section = 'l';
                    e.score = 0;
                    cardList.add(i, e);
                } else if (i == numSides + 4) {
                    e.name = "LS";
                    e.used = 'n';
                    e.section = 'l';
                    e.score = 0;
                    cardList.add(i, e);
                } else if (i == numSides + 5) {
                    e.name = "Y";
                    e.used = 'n';
                    e.section = 'l';
                    e.score = 0;
                    cardList.add(i, e);
                } else if (i == numSides + 6) {
                    e.name = "C";
                    e.used = 'n';
                    e.section = 'l';
                    e.score = 0;
                    cardList.add(i, e);
                }
            }
        }

        /**
         * changes the score in the cardList array
         *
         * @param SI the user choice is passed in this function
         */
        public static void scoreOutput(String SI) {
            ArrayList<Integer> theScores = Turn.getScores();
            for (int i = 0; i < cardList.size(); i++) {
                String tester;
                tester = cardList.get(i).getName();
                if (SI.equals(tester)) {
                    cardList.get(i).setScore(theScores.get(i));
                    cardList.get(i).setUsed('y');
                }
            }
            writeScorecard();
        }

        /**
         * writes to the scorecard from the cardList array
         */
        public static void writeScorecard() {
            try {
                PrintStream outFile = new PrintStream(new java.io.File("scorecard.txt"));
                for (int i = 0; i < cardList.size(); i++) {
                    outFile.print(cardList.get(i).getName() + "," + cardList.get(i).getUsed() + "," + cardList.get(i).getSection() + "," + cardList.get(i).getScore() + "\n");
                }
                outFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        /**
         * this function calculates the scorecard
         */
        public static void calculateScoreCard() {
            int subTotal = 0;
            for (int i = 0; i < numSides; i++) {
                char used = cardList.get(i).getUsed();
                if (used == 'y') {
                    subTotal += cardList.get(i).getScore();
                }
            }
            // bonus
            if (subTotal > 63)
                bonus = 35;
            else
                bonus = 0;
            // upper total
            upperTotal = subTotal + bonus;
            // lower total
            lowerTotal = 0;
            for (int i = 0; i < 7; i++) {
                char used = cardList.get(i + numSides).getUsed();
                if (used == 'y') {
                    lowerTotal += cardList.get(i + numSides).getScore();
                }
            }
            // grand total
            grandTotal = upperTotal + lowerTotal;
        }

        /**
         * this function prints a scorecard
         */
        public static void printScoreCard() {
            calculateScoreCard();
            System.out.println("Line          Score");
            System.out.println("-------------------");
            for (int i = 0; i < numSides; i++)
                System.out.println(String.format("%s\t\t\t\t%s", cardList.get(i).getName(), cardList.get(i).getScore()));
            System.out.println("-------------------");
            System.out.println(String.format("%s\t\t%s", "Sub Total", subTotal));
            System.out.println(String.format("%s\t\t\t%s", "Bonus", bonus));
            System.out.println("-------------------");
            System.out.println(String.format("%s\t\t%s", "Upper Total", upperTotal));
            System.out.println("-------------------");
            for (int i = numSides; i < cardList.size(); i++)
                System.out.println(String.format("%s\t\t\t\t%s", cardList.get(i).getName(), cardList.get(i).getScore()));
            System.out.println("-------------------");
            System.out.println(String.format("%s\t\t%s", "Lower Total", lowerTotal));
            System.out.println("-------------------");
            System.out.println(String.format("%s\t\t%s", "Grand Total", grandTotal));
            System.out.println();
        }

        /**
         * @return the num of sides of the die
         */
        public int getNumSides() {
            return numSides;
        }

        /**
         * @return the number of dice
         */
        public static int getNumDice() {
            return numDice;
        }

        /**
         * finds if all options have been used
         *
         * @return true if full false if not
         */
        public static boolean isFull() {
            for (int i = 0; i < cardList.size(); i++) {
                if (cardList.get(i).getUsed() == 'n') {
                    return false;
                }
            }
            return true;
        }

        public static ArrayList<Element> getCardList() {
            return cardList;
        }

        public static void setCardList(ArrayList<Element> cardList) {
            Yahtzee.cardList = cardList;
        }

        /**
         * takes user input and validates whether or not it is valid (only y and n)
         *
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
            }
            if (userInput.length() < new Hand().getNumDice()) {
                if (userInput.equals('S')) {
                    return false;
                }
            }
            return true;
        }
    }
