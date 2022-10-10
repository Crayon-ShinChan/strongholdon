package model;

// Represent a stronghold occupied by a player
public class Stronghold {
    private Player owner;
    private int posX;
    private int posY;

    // REQUIRES: poxX >= StrongholdMap.WIDTH AND posX < StrongholdMap.HEIGHT
    //           AND poxY >= StrongholdMap.WIDTH AND posY < StrongholdMap.HEIGHT
    // EFFECTS: set a player as the owner of a stronghold in the position (posX, posY)
    public Stronghold(Player owner, int posX, int posY) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: change the owner of the stronghold
    public void changeOwner(Player p) {
        // stub
    }
}
