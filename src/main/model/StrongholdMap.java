package model;

import java.util.ArrayList;

// Represent the game map containing data of strongholds
public class StrongholdMap {
    public static final int DEFAULT_HEIGHT = 20; // should be larger than 4
    public static final int DEFAULT_WIDTH = 20; // should be larger than 4
    public static final int MAX_PLAYER_NUM = 4;
    public static final int MIN_PLAYER_NUM = 2;
    // TODO: Ask TA for help: final without static should be fields or constant like WIDTH, should they be public?
    public final int height;
    public final int width;
    private Stronghold[][] strongholds;
    private ArrayList<Player> players;

    // EFFECTS: construct a map with no stronghold, player and with the fixed default width and height
    public StrongholdMap() {
        this.height = DEFAULT_HEIGHT;
        this.width = DEFAULT_WIDTH;
        this.strongholds = new Stronghold[this.height][this.width];
        // stub
    }

    // TODO: ask if there is also a constructor with width and map parameters, how does the computer distinguish them
    // REQUIRES: width >= 4 AND height >= 4;
    // EFFECTS: construct a map with no stronghold, player and with the fixed width and height
    public StrongholdMap(int height, int width) {
        this.height = height;
        this.width = width;
        // TODO: ask if there is any way to eliminate the duplicated rows in different constructors
        this.strongholds = new Stronghold[this.height][this.width];
        // stub
    }

    // TODO: ask TA for help: should I constraint size here? players.size() is not an input.
    // REQUIRES: players.size() >= 2 AND players.size() <= 4
    // MODIFIES: this
    // EFFECTS: assign initial positions and initial strongholds for players
    //          if the initial positions already have strongholds, change the owner
    //          all players have different initial positions
    public void startMatch() {
        // stub
    }

    // TODO: ask: this func is mainly for test, should I use a func with different name? or just startMatch?
    // REQUIRES: players.size() >= MIN_PLAYER_NUM AND players.size() <= MAX_PLAYER_NUM
    //           AND players.get(i).getPosX() != null AND players.get(i).getPosY() != null
    //           AND all players have different initial positions and playerId
    // MODIFIES: this
    // EFFECTS: assign initial strongholds for players
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
    // EFFECTS: add a player to player list without position values
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

    // TODO: ask if print function should be moved to ui package?
//    // EFFECTS: print strongholds using player id
//    public void printStrongholds() {
//        // stub
//    }

    // TODO: ask should I make it private?
    // EFFECTS: return list of players in the map
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // EFFECTS: return list of strongholds in the map
    public Stronghold[][] getStrongholds() {
        return strongholds;
    }

    // TODO: ask should we test helper functions?
}
