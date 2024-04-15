/**
 * This program computes a game of Farkle
 * CPSC 224, Spring 2024
 * No sources to cite.
 *
 * @author Nikita Dubinin
 * @version v2.0 03/19/24
 */

package edu.gonzaga.Farkle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
    private ArrayList<Integer> hand = new ArrayList<>();
    private ArrayList<Integer> diceNumberCount = new ArrayList<>();
    private ArrayList<Integer> bank = new ArrayList<>(Collections.nCopies(6, 0));
    private ArrayList<Integer> numDicesInBank = new ArrayList<>(Collections.nCopies(6, 0));
    private Integer meldScore = 0;
    private Die die = new Die(6);
    private ArrayList<Integer> diceInCombo = new ArrayList<>(Arrays.asList(0 , 0 , 0 , 0 , 0 , 0));
    private ArrayList<Integer> diceInMeld = new ArrayList<>(Collections.nCopies(6, 0));
    public ArrayList<Integer> getHand() {
        return this.hand;
    }

    public void setBank(ArrayList<Integer> newBank) {
        for (int i = 0; i < 6; i++) {
            bank.set(i , newBank.get(i));
        }
    }

    public void setDiceNumberCount(ArrayList<Integer> newNumBank) {
        diceNumberCount = new ArrayList<>(Collections.nCopies(6 , 0));
        for (int i = 0; i < 6; i++) {
            diceNumberCount.set(i , newNumBank.get(i));
        }
    }

    public void setNumDicesInBank (ArrayList<Integer> newNumDicesInBank) {
        numDicesInBank = new ArrayList<>(Collections.nCopies(6 , 0));
        for (int i = 0; i < 6; i++) {
            numDicesInBank.set(i , newNumDicesInBank.get(i));
        }
    }

    public ArrayList<Integer> returnDiceInMeld() {return diceInMeld;}

    public ArrayList<Integer> getDiceNumberCount() {
        return this.diceNumberCount;
    }

    public ArrayList<Integer> getDiceInMeld() {
        return diceInMeld;
    }

    public Integer getMeldScore() {
        return meldScore;
    }

    /**
     * clears arrays for a new round
     *
     * @param -
     * @return -
     */
    public void getReady() {
        numDicesInBank.clear();
        numDicesInBank = new ArrayList<>(Collections.nCopies(6, 0));

        diceNumberCount.clear();
        diceNumberCount = new ArrayList<>(Collections.nCopies(6, 0));
    }

    /**
     * rolls 6 dices
     *
     * @param -
     * @return -
     */
    public void rollHand() {
        hand = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            die.roll();
            hand.add(die.getSideUp());
        }

        hand.sort(Comparator.naturalOrder());

        for (int i = 0; i < 6; i++) {
            if (diceInCombo.get(i) < 1) {
                continue;
            } else {
                hand.set(i , 0);
            }
        }
    }

    /**
     * prints user's interface
     *
     * @param player
     * @return -
     */
    public void printInterface(Player player) {
        System.out.println(player.getName() + ", it's your turn.");

        System.out.println("Hand: " + hand);

        printHand(player);

        printChoice();
    }

    /**
     * prints information about user's hand
     *
     * @param player
     * @return -
     */
    public void printHand(Player player) {
        findNumDices(hand, diceNumberCount);

        System.out.println("Dice number count: " + diceNumberCount);

        System.out.println("*************************** Current hand and meld *******************");
        System.out.println(" Die   Hand |   Meld");
        System.out.println("------------+---------------");
        for (int i = 65; i < 71; i++) {
            if (diceInCombo.get(i - 65) == 0) {
                if (bank.get(i - 65) == 0) {
                    System.out.println("(" + (char) i + ")    " + hand.get(i - 65) + "    |      ");
                } else {
                    System.out.println("(" + (char) i + ")         |     " + bank.get(i - 65));
                }
            } else {
                System.out.println("(" + (char) i + ")         |     " + diceInCombo.get(i - 65));
            }
        }

        System.out.println("------------+---------------");
        System.out.println("                Meld Score: " + (player.getMeld() + player.getConstMeld()));
        System.out.println("                Bank Score: " + player.getBank());

    }

    /**
     * looks through player input to determines which dices does the player bank
     *
     * @param str
     * @return -
     */
    public void bankHand(String str) {

        for (int i = 0; i < str.length(); i++) {
            Integer comp = (int) str.charAt(i);
            if (comp >= 65 && comp <= 70) {
                if (bank.get(comp - 65) == 0) {
                    bank.set(comp - 65, hand.get(comp - 65));
                } else {
                    bank.set(comp - 65, 0);
                }
            }
        }
    }

    /**
     * goes through one array to find num dices of each value and saves this number to the second array
     *
     * @param from , to
     * @return -
     */
    public void findNumDices(ArrayList<Integer> from, ArrayList<Integer> to) {
        for (int i = 0; i < 6; i++) {
            if (from.get(i) != 0) {
                to.set(from.get(i) - 1, to.get(from.get(i) - 1) + 1);
            }
        }
    }

    /**
     * contaions logic to count player's bank
     *
     * @param player
     * @return -
     */
    public void countMeld(Player player) {
        player.setMeld(0);

        findNumDices(bank, numDicesInBank);

        if (!numDicesInBank.contains(0)) {//straight combo
            player.addMeld(1000);
            return;
        }

        if (numDicesInBank.contains(3) && numDicesInBank.contains(2)) { //custom full house combo
            player.setMeld(1500);
            findOnes(player);
            return;
        }

        findOnes(player);
        findPairs(player);
        findTriplesPlus(player);
    }

    /**
     * checks for combinations with single dices
     *
     * @param player
     * @return -
     */
    public void findOnes(Player player) {

        if (numDicesInBank.get(0) == 1) {
            player.addMeld(100);
        }

        if (numDicesInBank.get(4) == 1) {
            player.addMeld(50);
        }
    }

    /**
     * checks for combinations with pairs
     *
     * @param player
     * @return -
     */
    public void findPairs(Player player) {

        Integer numPairs = 0;

        for (int i = 0; i < 6; i++) {
            if (numDicesInBank.get(i) == 2) {
                numPairs++;
            }
        }

        if (numPairs == 3) {
            player.addMeld(750);
            return;
        }

        if (numDicesInBank.get(0) == 2) {
            player.addMeld(200);
        }

        if (numDicesInBank.get(4) == 2) {
            player.addMeld(100);
        }
    }

    /**
     * checks for combinations with triples and bigger combinations
     *
     * @param player
     * @return -
     */
    public void findTriplesPlus(Player player) {

        if (numDicesInBank.get(0) >= 3) {
            player.addMeld((1000 + (100 * (numDicesInBank.get(0) - 3))));
        }

        for (int i = 1; i < 6; i++) {
            if (numDicesInBank.get(i) >= 3) {
                player.addMeld((100 * (i + 1) + (100 * (numDicesInBank.get(i) - 3))));
            }
        }
    }

    /**
     * checks players roll for farkle
     *
     * @param -
     * @return bool
     */
    public boolean isFarkle() {

        Integer numPairs = 0;

        for (int i = 0; i < 6; i++) {
            if (diceNumberCount.get(i) == 2) {
                numPairs++;
            }
        }

        if (diceNumberCount.get(0) == 0 && diceNumberCount.get(1) <= 2 && diceNumberCount.get(2) <= 2 && diceNumberCount.get(3) <= 2 && diceNumberCount.get(4) == 0 && diceNumberCount.get(5) <= 2 && numPairs < 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * looks for combos in the meld
     *
     * @param -
     * @return -
     */
    public void findDiceInCombo() {

        for (int i = 0; i < 6; i++) {
            diceInMeld.set(i, 0);
        }

        int numPairs = 0;
        for (int i = 0; i < 6; i++) {
            if (numDicesInBank.get(i) == 2) {
                numPairs++;
            }
        }

        if (numPairs == 3) {
            for (int i = 0; i < 6; i++) {
                diceInMeld.set(i, 1);
            }
            return;
        }

        for (int i = 0; i < 6; i++) {
            int diceValue = bank.get(i);
            if (diceValue > 0 && (diceValue == 1 || diceValue == 5 || Collections.frequency(bank, diceValue) >= 3)) {
                diceInMeld.set(i, 1);
            }
        }
    }


    /**
     * preparing elements of the program for re rolling, returning true if player has a hot hand, returning false in all other cases
     *
     * @param player
     * @return bool
     */
    public Boolean prepareForReroll (Player player) {
        player.addConstMeld(player.getMeld());

        for (int i = 0; i < 6; i++) {
            if (diceInMeld.get(i) == 1) {
                diceInCombo.set(i, bank.get(i));
            }
            bank.set(i, 0);
        }
        if (!(diceInCombo.contains(0))) {
            diceInMeld.clear();
            diceInCombo = new ArrayList<>(Collections.nCopies(6 , 0));
            System.out.println("***** HOT HAND! **** \n" + "Would you like to roll 6 new dice, or bank and end your turn?\n" + "************************");
            return true;
        } else {
            return false;
        }
    }

    public void endRound(Player player) {
        player.transferToBank();
    }

    public void printChoice() {
        System.out.println(" (R) Re-roll");
        System.out.println(" (K) Bank Meld & End Round");
        System.out.println(" (Q) Quit game");
    }
}