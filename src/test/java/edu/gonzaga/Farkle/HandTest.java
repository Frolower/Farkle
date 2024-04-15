package edu.gonzaga.Farkle;

import edu.gonzaga.Farkle.Hand;
import edu.gonzaga.Farkle.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class HandTest {
    @Test
    void testCountMeldStraight() {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<Integer> testBank = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        Integer expectedResults = 1000;

        hand.setBank(testBank);

        hand.countMeld(player);

        assertEquals(expectedResults , player.getMeld());
    }

    @Test
    void testCountMeldThreePairs() {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<Integer> testBank = new ArrayList<>(Arrays.asList(1,1,2,2,3,3));
        Integer expectedResults = 750;

        hand.setBank(testBank);

        hand.countMeld(player);

        assertEquals(expectedResults , player.getMeld());
    }

    @Test
    void testCountMeldOne() {
        Hand hand = new Hand();
        Player player = new Player();
        ArrayList<Integer> testBank = new ArrayList<>(Arrays.asList(1,2,2,3,3,4));
        Integer expectedResults = 100;

        hand.setBank(testBank);

        hand.countMeld(player);

        assertEquals(expectedResults , player.getMeld());
    }

    @Test
    void testIsFarkle() {
        Hand hand = new Hand();

        ArrayList<Integer> testDiceNumberCount = new ArrayList<>(Arrays.asList(0,2,2,1,0,1));

        hand.setDiceNumberCount(testDiceNumberCount);

        assertTrue(hand.isFarkle());
    }

    @Test
    void testFullhouse() {
        Hand hand = new Hand();
        Player player = new Player();

        ArrayList<Integer> testNumDicesInBank = new ArrayList<>(Arrays.asList(0,2,3,0,0,0));

        hand.setNumDicesInBank(testNumDicesInBank);

        hand.countMeld(player);

        assertEquals(1500 , player.getMeld());
    }

    @Test
    void testFullhousePlus() {
        Hand hand = new Hand();
        Player player = new Player();

        ArrayList<Integer> testNumDicesInBank = new ArrayList<>(Arrays.asList(1,2,3,0,0,0));

        hand.setNumDicesInBank(testNumDicesInBank);

        hand.countMeld(player);

        assertEquals(1600 , player.getMeld());
    }

    @Test
    void testFullhouseOnesAndFives() {
        Hand hand = new Hand();
        Player player = new Player();

        ArrayList<Integer> testNumDicesInBank = new ArrayList<>(Arrays.asList(2,0,0,0,3,0));

        hand.setNumDicesInBank(testNumDicesInBank);

        hand.countMeld(player);

        assertEquals(1500 , player.getMeld());
    }
}

