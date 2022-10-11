package model;

import java.util.ArrayList;

// Represent the game map containing data of strongholds
public class StrongholdMap {
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;

    // TODO: Ask TA for help: can I use this to declare 2-D array with fixed length? YES
    private Stronghold[][] map;
    private ArrayList<Player> players;

    // EFFECTS: construct a map with no stronghold, player and with the fixed WIDTH and HEIGHT
    public StrongholdMap() {
        // stub
    }

    // TODO: Ask TA for help: should I write the REQUIRES for positions? If the postions are fixed, we don't need it
    // MODIFIES: this
    // EFFECTS: add a player to player list
    private void addPlayer(Player p) {
        // stub
    }

    // REQUIRES: Stronghold.posX >= 0 AND Stronghold.posX < WIDTH
    //           AND Stronghold.posY >= 0 AND Stronghold.posY < HEIGHT
    // MODIFIES: this
    // EFFECTS: add a stronghold to map
    private void addStronghold(Stronghold s) {
        // stub
    }

    // TODO: Ask TA for help: does changing the player in player list means modifying "this"? YES
    // MODIFIES: this
    // EFFECTS: calculate the current scores for the current player who moved just now
    private void calScores() {
        // stub
    }
}
