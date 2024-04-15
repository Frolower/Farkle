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

/*
*  This is the main class for the Farkle project.
*  It really should just instantiate another class and run
*   a method of that other class.
*/

/** Main program class for launching Farkle program. */
public class Farkle {
    // This main is where your Farkle game starts execution for general use.
    public static void main(String[] args) {
        Farkle game = new Farkle();
        Board board = new Board();
        ArrayList<Player> players = new ArrayList<>();
        Integer score = 0;
        Scanner input = new Scanner(System.in);
        String nameInput;

        board.printGreetings();
        players = game.setPlayers(players);
        score = game.setScore();

        board.playGame(players, score);
    }

    /**
     function that creates the players roaster
     *
     * @param players
     * @return ArrayList Player
     */
    public ArrayList<Player> setPlayers(ArrayList<Player> players) {
        Scanner input = new Scanner(System.in);
        System.out.println("How many players are going to play?");
        String numPlayers = input.nextLine();

        if (!Objects.equals(numPlayers, "")) {
            for (int i = 0; i < Integer.parseInt(numPlayers); i++) {
                System.out.println("Enter new player name:");
                String nameInput = input.nextLine();
                Player newPlayer = new Player();

                if (!Objects.equals(nameInput, "")) {
                    newPlayer.setName(nameInput);
                } else {
                    newPlayer.setName("Unknown Player " + (i + 1));
                }
                players.add(newPlayer);
            }
        } else {
            Player defaultPlayer = new Player();
            defaultPlayer.setName("Unknown Player");
            players.add(defaultPlayer);
        }
        return players;
    }

    /**
     sets the target score for the game
     *
     * @param  -
     * @return Integer
     */
    public Integer setScore() {
        Integer score = 0;

        System.out.println("Enter the target score for the game");
        Scanner input = new Scanner(System.in);
        String scoreInput = "";

        scoreInput = input.nextLine();

        if (!Objects.equals(scoreInput, "")) {
            score = Integer.valueOf(scoreInput);
            return score;
        } else {
            return 10000;
        }


    }
}
