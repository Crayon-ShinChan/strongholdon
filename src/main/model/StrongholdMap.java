package model;

import java.util.ArrayList;

// Represent the game map containing data of strongholds
public class StrongholdMap {
    public static final int DEFAULT_WIDTH = 16;
    public static final int DEFAULT_HEIGHT = 16;
    // TODO: Ask TA for help: final without static should be fields or constant like WIDTH, should they be public?
    public final int width;
    public final int height;
    private Stronghold[][] strongholds;
    private ArrayList<Player> players;

    // EFFECTS: construct a map with no stronghold, player and with the fixed WIDTH and HEIGHT
    public StrongholdMap() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.strongholds = new Stronghold[this.width][this.height];
        // stub
    }

    // TODO: ask if there is also a constructor with width and map parameters, how does the computer distinguish them
    // EFFECTS: construct a map with no
    public StrongholdMap(int width, int height) {
        this.width = width;
        this.height = height;
        // TODO: ask if there is any way to eliminate the duplicated rows in different constructors
        this.strongholds = new Stronghold[this.width][this.height];
        // stub
    }

    // TODO: ask TA for help: should I constraint size here? players.size() is not an input.
    // REQUIRES: players.size() >= 2 AND players.size() <= 4
    // MODIFIES: this
    // EFFECTS: assign initial positions and initial strongholds for players
    //          if the initial positions already have strongholds, change the owner
    public void startMatch() {
        // stub
    }

    // TODO: ask: this func is mainly for test, should I use a func with different name? or just startMatch?
    // REQUIRES: players.size() >= 2 AND players.size() <= 4
    //           AND players.get(i).getPosX() != null AND players.get(i).getPosY() != null
    // MODIFIES: this
    // EFFECTS: assign initial positions and initial strongholds for players
    //          if the initial positions already have strongholds, change the owner
    public void startMatchWithPlayerPosition() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add a player to player list, playerId is a continuously increasing positive number
    public void addPlayer() {
        // stub
    }

    // TODO: ask this is for test, is there any better way?
    // REQUIRES: p
    // MODIFIES: this
    // EFFECTS: add a player to player list
    public void addPlayerWithData(Player p) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add a stronghold to map
    public void addStronghold(Stronghold s) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: calculate the current scores for the current player who moved just now
    public void calScores() {
        // stub
    }

    // TODO: ask should I make it private?
    // EFFECTS: return list of players in the map
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // EFFECTS: return list of strongholds in the map
    public Stronghold[][] getStrongholds() {
        return strongholds;
    }

    // EFFECTS: print strongholds using player id
    public void printStrongholds() {
        // stub
    }
}
