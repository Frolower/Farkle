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
import java.util.Objects;
import java.util.Scanner;
import java.util.Comparator;

public class Board {

    private Hand hand = new Hand();
    private Scanner input = new Scanner(System.in);
    private String playersInput = "";
    private Boolean gameRunning = true;


    /**
     has a general logic to play the game
     *
     * @param player
     * @return -
     */
    public void playRound (Player player) {
        hand.rollHand();
        playersTurn(player);
    }

    public void playGame(ArrayList<Player> players, Integer score) {
        Integer currentPlayerIndex = 0;
        boolean targetScoreReached = false;
        Integer targetScorePlayerIndex = null;

        while (gameRunning) {
            hand = new Hand();
            Player currentPlayer = players.get(currentPlayerIndex);
            prepareRound(currentPlayer);
            playRound(currentPlayer);

            if (currentPlayer.getBank() >= score && !targetScoreReached) {
                targetScoreReached = true;
                targetScorePlayerIndex = currentPlayerIndex;
            }
            printResults(players);
            if (targetScoreReached && ((currentPlayerIndex + 1) % players.size() == targetScorePlayerIndex)) {
                break;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        endgame(players, currentPlayerIndex);
    }


    /**
     contaions functions to run rounds
     *
     * @param -
     * @return -
     */
    public void playersTurn (Player player) {
        hand.getReady();
        hand.countMeld(player);
        hand.printInterface(player);
        if (hand.isFarkle()) {
            System.out.println("Farkle!");
            return;
        } else {
            playersAction(player);
        }
    }

    /**
     contaions functions to run players actions
     *
     * @param -
     * @return -
     */
    public void playersAction(Player player) {
        playersInput = input.nextLine().trim().toUpperCase();

        if (Objects.equals(playersInput, "K")) {
            hand.endRound(player);
        } else if (Objects.equals(playersInput, "Q")) {
            System.out.println("Quitting the game...");
            gameRunning = false;
            return;
        } else if (Objects.equals(playersInput, "R") && player.getMeld() > 0) {
            hand.findDiceInCombo();
            if (hand.prepareForReroll(player)) {
                playersAction(player);
                hand.printChoice();
            }
            hand.rollHand();
            hand.getReady();
            playersTurn(player);
        } else {
            if (!playersInput.isEmpty()) {
                hand.bankHand(playersInput);
            }
            playersTurn(player);
        }
    }



    /**
     prints greetings
     *
     * @param -
     * @return -
     */
    public void printGreetings () {
        System.out.println("************************************************************************\n" +
                "* Zag Farkle by Nikita Dubinin! *\n" +
                "* Copyright: 2024 *\n" +
                "************************************************************************");
    }

    /**
     has the logic for the endgame
     *
     * @param players, playerToBeat
     * @return -
     */
    public void endgame(ArrayList<Player> players, Integer playerToBeat) {
        Player currentPlayer = new Player();
        for (int i = 0; i < playerToBeat; i++) {
            hand = new Hand();
            currentPlayer = players.get(i);
            playRound(currentPlayer);

        }
        printEndgame(players);
    }

    /**
     prints results of the game
     *
     * @param players
     * @return -
     */
    public void printResults (ArrayList<Player> players) {
        System.out.println("\n");
        for (int i = 0; i < players.size(); i++) {
            System.out.println((players.get(i)).getName() + ": " + (players.get(i)).getBank());
        }
        System.out.println("\n");
    }

    /**
     sets params for the next round
     *
     * @param player
     * @return -
     */
    public void prepareRound (Player player) {
        player.setMeld(0);
        player.setConstMeld(0);

    }

    /**
     prints the leaderboard after the game ended
     *
     * @param players
     * @return -
     */
    public void printEndgame(ArrayList <Player> players) {
        players.sort(Comparator.comparing(Player::getBank));

        System.out.println("Final Score: ");
        for (int i = players.size() - 1; i >= 0; i--) {
            System.out.println(players.get(i).getName() + ": " + players.get(i).getBank());
        }
    }
}