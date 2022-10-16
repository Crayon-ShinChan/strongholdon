package model;

// Represent a stronghold occupied by a player
public class Stronghold {
    private Player owner;
    // TODO: ask TA for help: can I set posX as public cuz it can be changed and should be accessed by other classes?
    private final int posX;
    private final int posY;

    // REQUIRES: poxX >= 0 AND posX < StrongholdMap.width
    //           AND poxY >= 0  AND posY < StrongholdMap.height
    // EFFECTS: set a player as the owner of a stronghold in the position (posX, posY)
    public Stronghold(Player owner, int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        // stub
    }

    // MODIFIES: this
    // EFFECTS: set the owner of a stronghold
    public void setOwner(Player p) {
        // stub
    }

    // EFFECTS: get posX of a stronghold
    public int getPosX() {
        return 0; //stub
    }

    // EFFECTS: get posY of a stronghold
    public int getPosY() {
        return 0; //stub
    }

    // EFFECTS: get the owner of a stronghold
    public Player getOwner() {
        return new Player(1, 0, 0); //stub
    }
}
