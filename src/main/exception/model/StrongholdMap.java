package exception.model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Random;

import static ui.GamePanel.*;

// Represent the game map containing data of strongholds
public class StrongholdMap implements Writable {
    public static final int DEFAULT_HEIGHT = MAX_SCREEN_HEIGHT - 3; // should be larger than 4
    public static final int DEFAULT_WIDTH = MAX_SCREEN_WIDTH; // should be larger than 4
    public static final int MAX_PLAYER_NUM = 3;
    public static final int MIN_PLAYER_NUM = 2;
    public static final int GAME_SECOND = 25;

    private final int height;
    private final int width;
    private Stronghold[][] strongholds;
    private ArrayList<Player> players;
    private int currentTimeUnit;
    private int roundInterval;

    // EFFECTS: construct a map with no stronghold, player and with the fixed default width and height
    public StrongholdMap() {
        this.height = DEFAULT_HEIGHT;
        this.width = DEFAULT_WIDTH;
        this.strongholds = new Stronghold[DEFAULT_HEIGHT][DEFAULT_WIDTH];
        this.players = new ArrayList<>();
        this.currentTimeUnit = 0;
        this.roundInterval = FPS / 2;
    }

    // REQUIRES: width >= 4 AND height >= 4;
    // EFFECTS: construct a map with no stronghold, player and with the fixed width and height
    public StrongholdMap(int height, int width) {
        this.height = height;
        this.width = width;
        this.strongholds = new Stronghold[this.height][this.width];
        this.players = new ArrayList<>();
        this.roundInterval = FPS / 2;
    }

    public StrongholdMap(int height, int width, int currentTimeUnit, int roundInterval) {
        this.height = height;
        this.width = width;
        this.strongholds = new Stronghold[this.height][this.width];
        this.players = new ArrayList<>();
        this.currentTimeUnit = currentTimeUnit;
        this.roundInterval = roundInterval;
    }

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
            players.get(i).setPosX(posX);
            players.get(i).setPosY(posY);
        }
        calScores();
    }

    // TODO: ask: Move the function to the test class
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
    // TODO: make sure the max constraint and may state requires here
    public void addPlayer() {
        players.add(new Player(players.size() + 1, players.size() + 1, null, null, this));
    }

    // REQUIRES: p is not already in this class
    // MODIFIES: this
    // EFFECTS: add a player to player list without position values
    public void addPlayerWithData(Player p) {
        p.setStrongholdMap(this);
        players.add(p);
    }

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
        for (int i = 0; i < strongholds.length; i++) {
            for (int j = 0; j < strongholds[0].length; j++) {
                if (strongholds[i][j] == null) {
                    continue;
                }
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("height", height);
        json.put("width", width);
        json.put("currentTimeUnit", currentTimeUnit);
        json.put("roundInterval", roundInterval);
        json.put("strongholds", strongholdsToJson());
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS: returns strongholds in this map as a JSON array
    private JSONArray strongholdsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < strongholds.length; i++) {
            for (int j = 0; j < strongholds[0].length; j++) {
                if (strongholds[i][j] != null) {
                    jsonArray.put(strongholds[i][j].toJson());
                }
            }
        }

        return jsonArray;
    }

    // EFFECTS: returns strongholds in this map as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : players) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // TODO: Do it like the following function in Debug Lab
    // EFFECTS: return list of players in the map
    public ArrayList<Player> getPlayers() {
        return players;
    }

//    public List<Cell> getBodyPositions() {
//        return Collections.unmodifiableList(body);
//    }

    // EFFECTS: return list of strongholds in the map
    public Stronghold[][] getStrongholds() {
        return strongholds;
    }

    //EFFECTS: returns width
    public int getWidth() {
        return width;
    }

    //EFFECTS: returns height
    public int getHeight() {
        return height;
    }

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

    public int getRoundStartTimeUnit() {
        return currentTimeUnit / roundInterval * roundInterval;
    }

    public void increaseCurrentTimeUnit() {
        this.currentTimeUnit++;
    }

    public int getCurrentTimeUnit() {
        return this.currentTimeUnit;
    }

    public boolean getIsTimeUp() {
        return GAME_SECOND * FPS <= currentTimeUnit;
    }

    // TODO: delete players, only available before any Match, add fields to indicate match start
}
