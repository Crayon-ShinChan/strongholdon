package model;

import java.util.ArrayList;

// Represents a player in the game
public class Player {
    // TODO: Ask TA for help: playerId will not change for the player, should I use final here?
    private int playerId;
    private int posX;
    private int posY;
    private ArrayList<Stronghold> newStronghold;
    // private String color;

    public Player(int playerId, int posX, int posY) {
        //stub
    }

    // EFFECTS: true, false
    private void move(String direction) {
        //stub
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
