package model;

// Represent a stronghold occupied by a player
public class Stronghold {
    // TODO: can posX/posY be public since it will not change after construction
    private final int posX;
    private final int posY;
    private Player owner;

    // REQUIRES: poxX >= 0 AND posX < StrongholdMap.width
    //           AND poxY >= 0  AND posY < StrongholdMap.height
    // EFFECTS: set a player as the owner of a stronghold in the position (posX, posY)
    public Stronghold(Player owner, int posX, int posY) {
        this.owner = owner;
        this.posX = posX;
        this.posY = posY;
    }

    // MODIFIES: this
    // EFFECTS: set the owner of a stronghold
    public void setOwner(Player p) {
        this.owner = p;
    }

    // EFFECTS: get posX of a stronghold
    public int getPosX() {
        return posX;
    }

    // EFFECTS: get posY of a stronghold
    public int getPosY() {
        return posY;
    }

    // EFFECTS: get the owner of a stronghold
    public Player getOwner() {
        return owner;
    }
}
