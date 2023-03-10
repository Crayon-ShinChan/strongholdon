package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private static final int SMALL_WIDTH = 6;
    private static final int SMALL_HEIGHT = 6;
    private static final int[] OWNER_PLAYER_ID = {
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    private static final int[] POS_X = {
            1, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4
    };
    private static final int[] POS_Y = {
            4, 3, 4, 5, 1, 2, 3, 5, 1, 3, 5, 1, 3, 5, 2, 4, 5
    };

    private StrongholdMap strongholdMap1, strongholdMap2;
    private ArrayList<Player> playerList;

    @BeforeEach
    public void runBefore() {
        // create a small map for testing
        strongholdMap1 = new StrongholdMap(SMALL_HEIGHT, SMALL_WIDTH);
        strongholdMap2 = new StrongholdMap(SMALL_HEIGHT, SMALL_WIDTH);
        playerList = new ArrayList<>();
        playerList.add(new Player(1, 1, 3, 3, null));
        playerList.add(new Player(2, 2, 0, 0, null));
        playerList.add(new Player(3, 3, SMALL_HEIGHT - 1, SMALL_WIDTH - 1, null));
        playerList.add(new Player(1, 1, 3, 3, null));
        constructMap1();
        constructMap2();
    }

    // TODO: ask if I should separate testConstructor to testConstructorBorder
    //  Combine
    @Test
    public void testConstructor() {
        assertEquals(1, playerList.get(0).getPlayerId());
        assertEquals(2, playerList.get(1).getPlayerId());
        assertEquals(3, playerList.get(2).getPlayerId());
        assertEquals(3, playerList.get(0).getPosX());
        assertEquals(0, playerList.get(1).getPosX());
        assertEquals(SMALL_HEIGHT - 1, playerList.get(2).getPosX());
        assertEquals(3, playerList.get(0).getPosY());
        assertEquals(0, playerList.get(1).getPosY());
        assertEquals(SMALL_WIDTH - 1, playerList.get(2).getPosY());
    }

    @Test
    public void testConstructorOnlyWithId() {
        Player player = new Player(0, 1);
        assertEquals(0, player.getPlayerId());
        assertEquals(1, player.getResourceId());
        assertEquals(0, player.getScore());
        assertNull(player.getPosX());
        assertNull(player.getPosY());
        assertNull(player.getStrongholdMap());
    }

    @Test
    public void testConstructorWithFullInfo() {
        Player player = new Player(0, 1, 0, 1, strongholdMap1, 0);
        assertEquals(0, player.getPlayerId());
        assertEquals(1, player.getResourceId());
        assertEquals(0, player.getScore());
        assertEquals(0, player.getPosX());
        assertEquals(1, player.getPosY());
        assertEquals(strongholdMap1, player.getStrongholdMap());
        assertEquals(0, player.getLastMoveTimeUnit());
    }

    // TODO: ask if we should test calScore, which is tested in StrongholdMap test class
    //  Don't have to test it
    @Test
    public void testMoveOneTime() {
        Player player = strongholdMap1.getPlayers().get(0);
        assertTrue(player.move("w"));
        assertEquals(2, player.getPosX());
        assertEquals(3, player.getPosY());
        combineTestStronghold(strongholdMap1.getStrongholds()[2][3], 1, 2, 3);
        assertEquals(2, player.getScore());
    }

    @Test
    public void testMoveTwoTimes() {
        Player player = strongholdMap1.getPlayers().get(0);
        assertTrue(player.move("w"));
        assertTrue(player.move("d"));
        assertEquals(2, player.getPosX());
        assertEquals(4, player.getPosY());
        combineTestStronghold(strongholdMap1.getStrongholds()[2][3], 1, 2, 3);
        combineTestStronghold(strongholdMap1.getStrongholds()[2][4], 1, 2, 4);
    }

    // TODO: ask: we've test occupying stronghold above, should we test this again here?
    //  Not necessary
    @Test
    public void testMoveUp() {
        Player player = strongholdMap1.getPlayers().get(0);
        assertTrue(player.move("w"));
        assertEquals(2, player.getPosX());
        assertEquals(3, player.getPosY());
    }

    @Test
    public void testMoveDown() {
        Player player = strongholdMap1.getPlayers().get(0);
        assertTrue(player.move("s"));
        assertEquals(4, player.getPosX());
        assertEquals(3, player.getPosY());
    }

    @Test
    public void testMoveLeft() {
        Player player = strongholdMap1.getPlayers().get(0);
        assertTrue(player.move("a"));
        assertEquals(3, player.getPosX());
        assertEquals(2, player.getPosY());
    }

    @Test
    public void testMoveRight() {
        Player player = strongholdMap1.getPlayers().get(0);
        assertTrue(player.move("d"));
        assertEquals(3, player.getPosX());
        assertEquals(4, player.getPosY());
    }

    @Test
    public void testMoveAndGetACircle() {
        Player player = strongholdMap2.getPlayers().get(0);
        assertTrue(player.move("d"));
        assertEquals(3, player.getPosX());
        assertEquals(4, player.getPosY());
        combineTestStronghold(strongholdMap2.getStrongholds()[3][4], 1, 3, 4);
        // test strongholds inside the circle
        combineTestStronghold(strongholdMap2.getStrongholds()[2][4], 1, 2, 4);
        combineTestStronghold(strongholdMap2.getStrongholds()[1][4], 1, 1, 4);
    }

    @Test
    public void testMoveAndGetMultipleCircles() {
        Player player = strongholdMap2.getPlayers().get(0);
        assertTrue(player.move("s"));
        // TODO: ask: should test position again in this test? other tests have tested this
        //  NO
        assertEquals(4, player.getPosX());
        assertEquals(3, player.getPosY());
        combineTestStronghold(strongholdMap2.getStrongholds()[4][3], 1, 4, 3);
        // test strongholds inside the circle
        combineTestStronghold(strongholdMap2.getStrongholds()[3][4], 1, 3, 4);
        combineTestStronghold(strongholdMap2.getStrongholds()[2][4], 1, 2, 4);
        combineTestStronghold(strongholdMap2.getStrongholds()[1][4], 1, 1, 4);
        combineTestStronghold(strongholdMap2.getStrongholds()[2][2], 1, 2, 2);
        combineTestStronghold(strongholdMap2.getStrongholds()[3][2], 1, 3, 2);
    }

    // TODO: ask: if this is integrated in other test, should we still test it again?
//    @Test
//    public void testMoveWhenOccupyStrongholdFromAnotherPlayer() {
//
//    }

    @Test
    public void testMoveOutOfMap() {
        Player player = strongholdMap2.getPlayers().get(1);
        assertFalse(player.move("w"));
        assertEquals(0, player.getPosX());
        assertEquals(0, player.getPosY());
        assertFalse(player.move("a"));
        assertEquals(0, player.getPosX());
        assertEquals(0, player.getPosY());
        player.setPosX(SMALL_HEIGHT - 1);
        player.setPosY(SMALL_WIDTH - 1);
        assertFalse(player.move("d"));
        assertEquals(SMALL_HEIGHT - 1, player.getPosX());
        assertEquals(SMALL_WIDTH - 1, player.getPosY());
        assertFalse(player.move("s"));
        assertEquals(SMALL_HEIGHT - 1, player.getPosX());
        assertEquals(SMALL_WIDTH - 1, player.getPosY());
    }

    @Test
    public void testMoveToAnotherPlayer() {
        Player player = strongholdMap2.getPlayers().get(2);
        player.setPosX(0);
        player.setPosY(1);
        assertFalse(player.move("a"));
        assertEquals(0, player.getPosX());
        assertEquals(1, player.getPosY());
        // test the stronghold of another player has not changed the owner
        combineTestStronghold(strongholdMap2.getStrongholds()[0][0], 2, 0, 0);
        // for code coverage of player.getPosX() == newPosX && player.getPosY() == newPosY
        assertTrue(player.move("s"));
        assertTrue(player.move("w"));
        assertTrue(player.move("s"));
        assertTrue(player.move("a"));
    }

    private void constructMap1() {
        strongholdMap1.addPlayerWithData(playerList.get(playerList.size() - 1));
        strongholdMap1.startMatchWithPlayerPosition();
    }

    private void constructMap2() {
        for (int i = 0; i < min(StrongholdMap.MAX_PLAYER_NUM, playerList.size() - 1); i++) {
            strongholdMap2.addPlayerWithData(playerList.get(i));
        }
        for (int i = 0; i < OWNER_PLAYER_ID.length; i++) {
            Stronghold stronghold = new Stronghold(playerList.get(OWNER_PLAYER_ID[i]), POS_X[i], POS_Y[i]);
            strongholdMap2.addStronghold(stronghold);
        }
        strongholdMap2.startMatchWithPlayerPosition();
    }

    private void combineTestStronghold(Stronghold stronghold, int playerId, int posX, int posY) {
        assertNotNull(stronghold);
        assertEquals(playerId, stronghold.getOwner().getPlayerId());
        assertEquals(posX, stronghold.getPosX());
        assertEquals(posY, stronghold.getPosY());
    }
}
