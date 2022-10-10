package model;

import java.util.ArrayList;

// Represent the game map containing data of strongholds
public class StrongholdMap {
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;

    // TODO: Ask TA for help: can I use this to declare 2-D array
    private Stronghold[][] map;
    private ArrayList<Player> players;

    // EFFECTS: construct a map with no stronghold, player and with the fixed WIDTH and HEIGHT
    public StrongholdMap() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add a player to player list
    private void addPlayer(Player p) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: calculate the current scores for all players
    private void calScores() {
        // stub
    }
}
