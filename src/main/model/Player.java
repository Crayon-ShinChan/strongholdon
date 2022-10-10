package model;

import java.util.ArrayList;

// Represents a player in the game
public class Player {
    // TODO: Ask TA for help: playerId will not change for the player, should I use final here?
    private int playerId;
    private int posX;
    private int posY;
    private int score;
    private ArrayList<Stronghold> newStronghold;
    // private String color;

    // REQUIRES: playerId > 0 AND posX >= 0 AND posX < StrongholdMap.WIDTH
    //           AND posY >= 0 AND posY < StrongholdMap.HEIGHT
    // EFFECTS: playerId is set to a positive integer not assigned to any other players
    //          posX and posY is set within the map
    public Player(int playerId, int posX, int posY) {
        //stub
    }

    // TODO: change the direction inputs for multiple players play simultaneously
    // REQUIRES: direction is one of ["w", "a", "s", "d"]
    // MODIFIES: this
    // EFFECTS: move the player to another stronghold with speed of 1
    //          the player can occupy the destination if he successfully gets to the destination
    //          if there is another players in the destination or the destination is out of the map
    //            - return false
    //          otherwise, return true
    private void move(String direction) {
        //stub
    }

    public void setScore(int score) {
        this.score = score;
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
