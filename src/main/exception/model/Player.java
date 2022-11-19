package exception.model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a player in the game
public class Player implements Writable {
    private final int playerId;
    // TODO: change the posX default value to -1 as null
    // TODO: update specification for resourceId
    private final int resourceId;
    private Integer posX; // Integer can be null
    private Integer posY;
    private int score;
    private StrongholdMap strongholdMap;
    private Integer lastMoveTimeUnit;
    // private ArrayList<Stronghold> newStronghold;
    // private String color;

    // REQUIRES: playerId > 0 AND posX >= 0 AND posX < StrongholdMap.width
    //           AND posY >= 0 AND posY < StrongholdMap.height
    // EFFECTS: playerId is set to a positive integer
    //          posX and posY is set within the map
    //          score is set to be 0
    public Player(int playerId, int resourceId) {
        this.playerId = playerId;
        this.resourceId = resourceId;
        this.score = 0;
    }

    // REQUIRES: playerId > 0 AND posX >= 0 AND posX < StrongholdMap.width
    //           AND posY >= 0 AND posY < StrongholdMap.height
    // EFFECTS: playerId is set to a positive integer
    //          posX and posY is set within the map
    //          score is set to be 0
    public Player(int playerId, int resourceId, Integer posX, Integer posY, StrongholdMap strongholdMap) {
        this.playerId = playerId;
        this.resourceId = resourceId;
        this.posX = posX;
        this.posY = posY;
        this.score = 0;
        this.strongholdMap = strongholdMap;
    }

    // REQUIRES: playerId > 0 AND posX >= 0 AND posX < StrongholdMap.width
    //           AND posY >= 0 AND posY < StrongholdMap.height
    // EFFECTS: playerId is set to a positive integer
    //          posX and posY is set within the map
    //          score is set to be 0
    public Player(
            int playerId,
            int resourceId,
            Integer posX,
            Integer posY,
            StrongholdMap strongholdMap,
            Integer lastMoveTimeUnit
    ) {
        this.playerId = playerId;
        this.resourceId = resourceId;
        this.posX = posX;
        this.posY = posY;
        this.score = 0;
        this.strongholdMap = strongholdMap;
        this.lastMoveTimeUnit = lastMoveTimeUnit;
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
        long roundStartTimeUnit = strongholdMap.getRoundStartTimeUnit();
        if (lastMoveTimeUnit != null && lastMoveTimeUnit > roundStartTimeUnit) {
            return false;
        }
        lastMoveTimeUnit = strongholdMap.getCurrentTimeUnit();
        direction = direction.toLowerCase();
        if (!movePlayer(direction)) {
            return false;
        }
        occupyStrongholds();
        strongholdMap.calScores();
        return true;
    }

    // EFFECTS: get playerId
    public int getPlayerId() {
        return playerId;
    }

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

    public int getResourceId() {
        return resourceId;
    }

    public Integer getLastMoveTimeUnit() {
        return lastMoveTimeUnit;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerId", playerId);
        json.put("resourceId", resourceId);
        json.put("posX", posX);
        json.put("posY", posY);
        json.put("score", score);
        json.put("lastMoveTimeUnit", lastMoveTimeUnit);
        return json;
    }

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
        for (Player player : strongholdMap.getPlayers()) {
            if (player != this && player.getPosX() == newPosX && player.getPosY() == newPosY) {
                return false;
            }
        }
        if (newPosX < 0 || newPosX >= strongholdMap.getHeight() || newPosY < 0 || newPosY >= strongholdMap.getWidth()) {
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
        boolean[][] visited = new boolean[strongholdMap.getHeight()][strongholdMap.getWidth()];
        for (int i = 0; i < strongholdMap.getHeight(); i++) {
            for (int j = 0; j < strongholdMap.getWidth(); j++) {
                if (
                        !visited[i][j]
                        && (
                                strongholdMap.getStrongholds()[i][j] == null
                                || strongholdMap.getStrongholds()[i][j].getOwner() != this
                        )
                ) {
                    ArrayList<int[]> block = new ArrayList<>();
                    if (searchBlockInCircle(i, j, block, visited, this)) {
                        occupyBlock(block, this);
                    }
                }
            }
        }
    }

    // MODIFIES: block, visited
    // EFFECTS: find the connected block and return true if the block is surrounded; otherwise, return false
    private boolean searchBlockInCircle(int row, int col, ArrayList<int[]> block, boolean[][] visited, Player p) {
        if (row == -1 || row == strongholdMap.getHeight() || col == -1 || col == strongholdMap.getWidth()) {
            return false;
        }
        Stronghold sh = strongholdMap.getStrongholds()[row][col];
        if ((sh != null && sh.getOwner() == p) || visited[row][col]) {
            return true;
        }
        visited[row][col] = true;
        block.add(new int[]{row, col});
        return searchNeighbor(row, col, block, visited, p);
    }

    // MODIFIES: res
    // EFFECTS: search strongholds around the stronghold in the position (row, col)
    private boolean searchNeighbor(int row, int col, ArrayList<int[]> block, boolean[][] visited, Player p) {
        boolean isSurrounded = true;
        int[] deltaX = new int[]{-1, 0, 0, 1};
        int[] deltaY = new int[]{0, -1, 1, 0};
        for (int i = 0; i < deltaX.length; i++) {
            isSurrounded &= searchBlockInCircle(row + deltaX[i], col + deltaY[i], block, visited, p);
        }
        return isSurrounded;
    }

    // MODIFIES: this
    // EFFECTS: occupy the strongholds in the circle as a block
    private void occupyBlock(ArrayList<int[]> block, Player p) {
        for (int[] pos:block) {
            strongholdMap.occupyStronghold(pos[0], pos[1], p);
        }
    }
}
