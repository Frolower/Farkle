package edu.gonzaga.Farkle;

import edu.gonzaga.Farkle.Hand;
import edu.gonzaga.Farkle.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class PlayerTest {
    @Test
    void testSetPlayerName() {
        Player player = new Player();
        String newName = "John Doe";
        String expectedName = "John Doe";

        player.setName(newName);

        assertEquals(expectedName , player.getName());
    }

    @Test
    void testSetPlayerNameEmpty() {
        Player player = new Player();
        String newName = "";
        String expectedName = "Unknown Player";

        player.setName(newName);

        assertEquals(expectedName , player.getName());
    }


}
