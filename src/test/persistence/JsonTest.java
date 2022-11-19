package persistence;

import exception.model.Player;
import exception.model.Stronghold;
import exception.model.StrongholdMap;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    protected void checkStrongholds(int[][] existStrongholds, Stronghold[][] strongholds) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!checkHolding(existStrongholds, i, j)) {
                    assertNull(strongholds[i][j]);
                }
            }
        }
        for (int[] info:existStrongholds) {
            int posX = info[0];
            int posY = info[1];
            int playerId = info[2];
            assertNotNull(strongholds[posX][posY]);
            assertEquals(playerId, strongholds[posX][posY].getOwner().getPlayerId());
        }
    }

    protected void checkPlayers(int[][] existPlayers, ArrayList<Player> players, StrongholdMap mp) {
        assertEquals(existPlayers.length, players.size());
        int count = 0;
        for (int[] info:existPlayers) {
            for (Player p:players) {
                boolean checkPlayerId = info[0] == p.getPlayerId();
                boolean checkPos = info[1] == p.getPosX() && info[2] == p.getPosY();
                boolean checkScore = info[3] == p.getScore();
                boolean checkResourceId = info[4] == p.getResourceId();
                boolean checkLastMoveUnit = true;
                if (info[4] != -1) {
                    checkLastMoveUnit = info[4] == p.getResourceId();
                }
                if (checkPlayerId && checkPos && checkScore && checkResourceId) {
                    count++;
                }
            }
        }
        assertEquals(existPlayers.length, count);
        for (Player p:players) {
            assertEquals(mp, p.getStrongholdMap());
        }
    }

    private boolean checkHolding(int[][] existStrongholds, int posX, int posY) {
        for (int[] info:existStrongholds) {
            if (info[0] == posX && info[1] == posY) {
                return true;
            }
        }
        return false;
    }
}
