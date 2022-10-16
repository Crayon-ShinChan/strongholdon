package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerTest {
    private static final int SMALL_WIDTH = 6;
    private static final int SMALL_HEIGHT = 6;
    private static final int[] OWNER_PLAYER_ID = {
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    private static final int[] POS_X = {
            2, 3, 4, 5, 1, 2, 3, 5, 1, 3, 5, 1, 3, 5, 1, 2, 4, 5
    };
    private static final int[] POS_Y = {
            0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4
    };

    private StrongholdMap strongholdMap1, strongholdMap2;
    private ArrayList<Player> playerList;
    private ArrayList<Stronghold> strongholdList;

    @BeforeEach
    public void runBefore() {
        // create a small map for testing
        strongholdMap1 = new StrongholdMap(SMALL_WIDTH, SMALL_HEIGHT);
        strongholdMap2 = new StrongholdMap(SMALL_WIDTH, SMALL_HEIGHT);
        playerList.add(new Player(1, 3, 3));
        playerList.add(new Player(2, 0, 0));
        playerList.add(new Player(3, SMALL_WIDTH - 1, SMALL_HEIGHT - 1));
        // TODO: ask: p4 is just for the test of move when player want to move to another player,
        // TODO: should p4 be tested in constructor?
        playerList.add(new Player(4, 1, 0));
        constructMap1();
        constructMap2();
    }

    @Test
    public void testConstructor() {

    }

    @Test
    public void testMoveOneTime() {
        //stub
    }

    @Test
    public void testMoveTwoTimes() {

    }

    @Test
    public void testMoveUp() {

    }

    @Test
    public void testMoveDown() {

    }

    @Test
    public void testMoveLeft() {

    }

    @Test
    public void testMoveRight() {

    }

    @Test
    public void testMoveAndGetACircle() {

    }

    @Test
    public void testMoveAndGetMultipleCircles() {

    }

    // TODO: ask: if this is integrated in other test, should we still test it again?
//    @Test
//    public void testMoveWhenOccupyStrongholdFromAnotherPlayer() {
//
//    }

    @Test
    public void testMoveOutOfMap() {

    }

    @Test
    public void testMoveToAnotherPlayer() {

    }

    private void constructMap1() {
        strongholdMap1.addPlayerWithData(playerList.get(0));
        strongholdMap1.startMatchWithPlayerPosition();
    }

    private void constructMap2() {
        for (Player player:playerList) {
            strongholdMap2.addPlayerWithData(player);
        }
        for (int i = 0; i < OWNER_PLAYER_ID.length; i++) {
            strongholdList.add(new Stronghold(playerList.get(OWNER_PLAYER_ID[i]), POS_X[i], POS_Y[i]));
            strongholdMap2.addStronghold(strongholdList.get(i));
        }
        strongholdMap2.startMatchWithPlayerPosition();
    }
}
