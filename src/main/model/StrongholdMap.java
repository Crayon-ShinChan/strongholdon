package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
        this.players = new ArrayList<Player>();
    }

    // TODO: ask if there is also a constructor with width and map parameters, how does the computer distinguish them
    // REQUIRES: width >= 4 AND height >= 4;
    // EFFECTS: construct a map with no stronghold, player and with the fixed width and height
    public StrongholdMap(int height, int width) {
        this.height = height;
        this.width = width;
        // TODO: ask if there is any way to eliminate the duplicated rows in different constructors
        this.strongholds = new Stronghold[this.height][this.width];
        this.players = new ArrayList<Player>();
    }

    // TODO: ask TA for help: should I constraint size here? players.size() is not an input.
    // REQUIRES: players.size() >= 2 AND players.size() <= 4
    // MODIFIES: this
    // EFFECTS: assign initial positions and initial strongholds for players
    //          if the initial positions already have strongholds, change the owner
    //          all players have different initial positions
    //          refresh scores
    public void startMatch() {
        int[][] positionList = calPositionList(players.size());
        shufflePositionList(positionList);
        for (int i = 0; i < players.size(); i++) {
            int posX = positionList[i][0];
            int posY = positionList[i][1];
            if (strongholds[posX][posY] == null) {
                strongholds[posX][posY] = new Stronghold(players.get(i), posX, posY);
            } else {
                strongholds[posX][posY].setOwner(players.get(i));
            }
        }
        calScores();
    }

    // TODO: ask: this func is mainly for test, should I use a func with different name? or just startMatch?
    // REQUIRES: players.size() >= MIN_PLAYER_NUM AND players.size() <= MAX_PLAYER_NUM
    //           AND players.get(i).getPosX() != null AND players.get(i).getPosY() != null
    //           AND all players have different initial positions and playerId
    // MODIFIES: this
    // EFFECTS: assign initial strongholds for players
    //          if the initial positions already have strongholds, change the owner
    //          refresh scores
    public void startMatchWithPlayerPosition() {
        for (Player player:players) {
            int posX = player.getPosX();
            int posY = player.getPosY();
            if (strongholds[posX][posY] == null) {
                strongholds[posX][posY] = new Stronghold(player, posX, posY);
            } else {
                strongholds[posX][posY].setOwner(player);
            }
        }
        calScores();
    }

    // MODIFIES: this
    // EFFECTS: add a player to player list, playerId is a continuously increasing positive number
    public void addPlayer() {
        players.add(new Player(players.size() + 1, null, null, this));
    }

    // TODO: ask this is for test, is there any better way?
    // REQUIRES: p
    // MODIFIES: this
    // EFFECTS: add a player to player list without position values
    public void addPlayerWithData(Player p) {
        p.setStrongholdMap(this);
        players.add(p);
    }

    // TODO: ask should I test this? I would like make it private and test occupyStronghold
    //  which implicitly test addStronghold
    // MODIFIES: this
    // EFFECTS: add a stronghold to map
    public void addStronghold(Stronghold s) {
        strongholds[s.getPosX()][s.getPosY()] = s;
    }

    // MODIFIES: this
    // EFFECTS: if the position has no stronghold, add a stronghold there and make the owner as the player
    //          if the position has a stronghold, change the owner as the player
    public void occupyStronghold(int posX, int posY, Player p) {
        if (strongholds[posX][posY] == null) {
            addStronghold(new Stronghold(p, posX, posY));
        }
        strongholds[posX][posY].setOwner(p);
    }

    // MODIFIES: this
    // EFFECTS: calculate the current scores for the current player who moved just now
    public void calScores() {
        int[] newScores = new int[players.size()];
        Arrays.fill(newScores, 0);
        for (int i = 0; i < strongholds.length; i++) {
            for (int j = 0; j < strongholds[0].length; j++) {
                int playerId = strongholds[i][j].getOwner().getPlayerId();
                for (int k = 0; k < players.size(); k++) {
                    if (players.get(k).getPlayerId() == playerId) {
                        newScores[k]++;
                    }
                }
            }
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setScore(newScores[i]);
        }
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

    // TODO: ask should we test helper functions? it is implicitly tested by the parent function
    //  how to test a random function? fix random seed?
    //  The function changes the local variable passed from other function. should I write modifies?
    // MODIFIES: positionList
    // EFFECTS: shuffle positions for players
    private void shufflePositionList(int[][] positionList) {
        Random rand = new Random();

        for (int i = 0; i < positionList.length; i++) {
            int randomIndexToSwap = rand.nextInt(positionList.length);
            int[] temp = positionList[randomIndexToSwap];
            positionList[randomIndexToSwap] = positionList[i];
            positionList[i] = temp;
        }
    }

    // REQUIRES: numPlayers >= 2 AND numPlayer <= 4
    // EFFECTS: return
    private int[][] calPositionList(int numPlayers) {
        if (numPlayers == 2) {
            return new int[][]{
                    {height / 2, width / 4},
                    {height / 2, width / 4 * 3}
            };
        } else if (numPlayers == 3) {
            return new int[][]{
                    {height / 4, width / 2},
                    {height / 4 * 3, width / 4},
                    {height / 4 * 3, width / 4 * 3}
            };
        } else {
            return new int[][]{
                    {height / 4, width / 4},
                    {height / 4, width / 4 * 3},
                    {height / 4 * 3, width / 4},
                    {height / 4 * 3, width / 4 * 3}
            };
        }
    }
}
