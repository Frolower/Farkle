/**
 * This program computes a game of Farkle
 * CPSC 224, Spring 2024
 * No sources to cite.
 *
 * @author Nikita Dubinin
 * @version v2.0 03/19/24
 */

package edu.gonzaga.Farkle;

import java.util.Scanner;

public class Player {
    private Integer meld = 0;
    private Integer bank = 0;
    private String name = "Unknown Player";
    private Scanner input = new Scanner(System.in);
    private String nameInput = "";
    private Integer constMeld = 0;

    public Integer getMeld() {
        return meld;
    }

    public void setMeld(int newMeld) {
        meld = newMeld;
    }

    public void addMeld(Integer add) {
        meld += add;
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(int newBank) {
        bank = newBank;
    }

    public void addBank(Integer add) {
        bank += add;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if (newName != "") {
            name = newName;
        } else {
            name = "Unknown Player";
        }
    }

    public Integer getConstMeld() {
        return constMeld;
    }

    public void setConstMeld(int newConstMeld) {
        constMeld = newConstMeld;
    }

    public void addConstMeld(Integer add) {
        constMeld += add;
    }

    public void transferToBank () {
        bank += meld + constMeld;
    }
}
