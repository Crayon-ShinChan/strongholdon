package model;

import java.util.ArrayList;
import java.util.Locale;

// Represents a player in the game
public class Player {
    // TODO: can playerId be public since it will not change after construction
    private final int playerId;
    private Integer posX; // Integer can be null
    private Integer posY;
    private int score;
    private StrongholdMap strongholdMap;
    // private ArrayList<Stronghold> newStronghold;
    // private String color;

    // TODO: add the new fields to EFFECTS if you add the fields later
    // REQUIRES: playerId > 0 AND posX >= 0 AND posX < StrongholdMap.width
    //           AND posY >= 0 AND posY < StrongholdMap.height
    // EFFECTS: playerId is set to a positive integer
    //          posX and posY is set within the map
    //          score is set to be 0
    public Player(int playerId, Integer posX, Integer posY, StrongholdMap strongholdMap) {
        this.playerId = playerId;
        this.posX = posX;
        this.posY = posY;
        this.score = 0;
        this.strongholdMap = strongholdMap;
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
        direction = direction.toLowerCase();
        if (!movePlayer(direction)) {
            return false;
        }
        occupyStrongholds();
        return true;
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
    public Integer getPosX() {
        return posX;
    }

    // EFFECTS: get posY
    public Integer getPosY() {
        return posY;
    }

    // EFFECTS: get score
    public int getScore() {
        return score;
    }

    // EFFECTS: get strongholdMap of the player
    public StrongholdMap getStrongholdMap() {
        return strongholdMap;
    }

    // MODIFIES: this
    // EFFECTS: set new posX
    public void setPosX(int posX) {
        this.posX = posX;
    }

    // MODIFIES: this
    // EFFECTS: set new posY
    public void setPosY(int posY) {
        this.posY = posY;
    }

    // MODIFIES: this
    // EFFECTS: set score of the player
    public void setScore(int score) {
        this.score = score;
    }

    // MODIFIES: this
    // EFFECTS: set strongholdMap of the player
    public void setStrongholdMap(StrongholdMap strongholdMap) {
        this.strongholdMap = strongholdMap;
    }

    // TODO: should we write the detail about the effects again? which are written in move function
    // REQUIRES: direction is one of ["w", "a", "s", "d"] from the keyboard
    // MODIFIES: this
    // EFFECTS: move the player to another stronghold with speed of 1
    //          if there is another players in the destination or the destination is out of the map
    //            - return false
    //          otherwise, return true
    private boolean movePlayer(String direction) {
        int newPosX = posX;
        int newPosY = posY;
        if (direction.equals("w")) {
            newPosX--;
        } else if (direction.equals("s")) {
            newPosX++;
        } else if (direction.equals("a")) {
            newPosY--;
        } else {
            newPosY++;
        }
        if (checkMovementValidity(newPosX, newPosY)) {
            posX = newPosX;
            posY = newPosY;
            return true;
        }
        return false;
    }

    // EFFECTS: if there is another players in the destination or the destination is out of the map
    //            - return false
    //          otherwise, return true
    private boolean checkMovementValidity(int newPosX, int newPosY) {
        for (Player player:strongholdMap.getPlayers()) {
            if (player != this && player.getPosX() == newPosX && player.getPosY() == newPosY) {
                return false;
            }
        }
        if (newPosX < 0 || newPosX >= strongholdMap.height || newPosY < 0 || newPosY >= strongholdMap.width) {
            return false;
        }
        posX = newPosX;
        posY = newPosY;
        return true;
    }

    // MODIFIES: this
    // EFFECTS: change the owner as the player for new strongholds occupied by the player
    private void occupyStrongholds() {
        strongholdMap.occupyStronghold(posX, posY, this);
    }
}
