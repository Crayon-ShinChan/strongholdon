package model;

import java.util.ArrayList;

// Represents a player in the game
public class Player {
    // TODO: Ask TA for help: playerId will not change for the player, should I use final here?
    private final int playerId;
    private Integer posX; // Integer can be null
    private Integer posY;
    private int score;
    // private ArrayList<Stronghold> newStronghold;
    // private String color;

    // TODO: add the new fields to EFFECTS if you add the fields later
    // REQUIRES: playerId > 0 AND posX >= 0 AND posX < StrongholdMap.width
    //           AND posY >= 0 AND posY < StrongholdMap.height
    // EFFECTS: playerId is set to a positive integer
    //          posX and posY is set within the map
    //          score is set to be 0
    public Player(int playerId, int posX, int posY) {
        this.playerId = playerId;
        //stub
    }

    // TODO: change the direction inputs for multiple players playing simultaneously
    // REQUIRES: direction is one of ["w", "a", "s", "d"] from the keyboard
    // MODIFIES: this
    // EFFECTS: move the player to another stronghold with speed of 1
    //          the player can occupy the destination if he successfully gets to the destination
    //          the player can occupy the strongholds in circles he made after moving
    //          if there is another players in the destination or the destination is out of the map
    //            - return false
    //          otherwise, return true
    public boolean move(String direction) {
        // lowercase first
        return false;
        //stub
    }

    // EFFECTS: set score of the player
    public void setScore(int score) {
        this.score = score;
    }

    // EFFECTS: get playerId
    public int getPlayerId() {
        return playerId;
    }

    // TODO: ask here posX is Integer not int, it pass with reference, right?
    //  which means if other class call this method, they can get the reference and change posX
    //  if I do not want other class to change the posX, but test class still has to call the func, how to design?
    //  can we simple change the return type as int rather than integer?
    //  if now posX is a customized class written by me, how to design to avoid other class to change it but
    //  the test class still can get its value?
    // EFFECTS: get posX
    public int getPosX() {
        return posX;
    }

    // EFFECTS: get posY
    public int getPosY() {
        return posY;
    }

    // EFFECTS: get score
    public int getScore() {
        return score;
    }
}
