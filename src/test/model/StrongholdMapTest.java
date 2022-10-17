package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StrongholdMapTest {
    private static final int SMALL_WIDTH = 6;
    private static final int SMALL_HEIGHT = 6;

    private StrongholdMap smallMap, strongholdMap;
    private Player player1, player2;
    Stronghold sh1, sh2;

    @BeforeEach
    public void beforeRun() {
        smallMap = new StrongholdMap(SMALL_HEIGHT, SMALL_WIDTH);
        strongholdMap = new StrongholdMap();
        player1 = new Player(1, 0, 0, null);
        player2 = new Player(2, 0, 1, null);
        sh1 = new Stronghold(player1, 0, 0);
        sh2 = new Stronghold(player2, 0, 1);
    }

    // TODO: ask can I use this method to avoid duplicated code?
    @Test
    public void testConstructor() {
        combineTestConstructor(strongholdMap, StrongholdMap.DEFAULT_HEIGHT, StrongholdMap.DEFAULT_WIDTH);
    }

    @Test void testConstructorWithWidthHeight() {
        combineTestConstructor(smallMap, SMALL_HEIGHT, SMALL_WIDTH);
    }

    @Test
    public void testStartMatchTwoPlayers() {
        int[][] positionListForStrongMap = getPositionListForTwoPlayers(strongholdMap);
        combineTestStartMatch(strongholdMap, positionListForStrongMap, 2);
        int[][] positionListForSmallMap = getPositionListForTwoPlayers(smallMap);
        combineTestStartMatch(smallMap, positionListForSmallMap, 2);
    }

    @Test
    public void testStartMatchThreePlayers() {
        int[][] positionListForStrongMap = getPositionListForThreePlayers(strongholdMap);
        combineTestStartMatch(strongholdMap, positionListForStrongMap, 3);
        int[][] positionListForSmallMap = getPositionListForThreePlayers(strongholdMap);
        combineTestStartMatch(smallMap, positionListForSmallMap, 3);
    }

    @Test
    public void testStartMatchFourPlayers() {
        int[][] positionListForStrongMap = getPositionListForFourPlayers(strongholdMap);
        combineTestStartMatch(strongholdMap, positionListForStrongMap, 4);
        int[][] positionListForSmallMap = getPositionListForFourPlayers(strongholdMap);
        combineTestStartMatch(smallMap, positionListForSmallMap, 4);
    }

    // TODO: ask is it necessary to use for loop here for avoiding duplicated code?
    @Test
    public void testStartMatchWithPlayerPosition() {
        strongholdMap.addPlayerWithData(player1);
        strongholdMap.addPlayerWithData(player2);
        strongholdMap.startMatchWithPlayerPosition();
        combineTestStronghold(
                strongholdMap.getStrongholds()[player1.getPosX()][player1.getPosY()],
                player1.getPlayerId(),
                player1.getPosX(),
                player1.getPosY()
        );
        combineTestStronghold(
                strongholdMap.getStrongholds()[player2.getPosX()][player2.getPosY()],
                player2.getPlayerId(),
                player2.getPosX(),
                player2.getPosY()
        );
        assertEquals(1, player1.getScore());
        assertEquals(1, player2.getScore());
    }

    // TODO: ask if I write the code like firstly set the stronghold and then call testStartMatchWithPlayerPosition
    //  this is for avoiding duplicated code
    //  But when I call testStartMatchWithPlayerPosition in this test function, will beforeRun be called?
    //  if yes for the last question, should I write a function without @Test tag
    //  but the body is same as testStartMatchWithPlayerPosition, then call this function here?
    @Test
    public void testStartMatchWithPlayerPositionOccupyExistStronghold() {
        strongholdMap.addStronghold(new Stronghold(player2, player1.getPosX(), player2.getPosY()));
        strongholdMap.addStronghold(new Stronghold(player2, 1, 1));
        strongholdMap.addPlayerWithData(player1);
        strongholdMap.addPlayerWithData(player2);
        strongholdMap.startMatchWithPlayerPosition();
        combineTestStronghold(
                strongholdMap.getStrongholds()[player1.getPosX()][player1.getPosY()],
                player1.getPlayerId(),
                player1.getPosX(),
                player1.getPosY()
        );
        combineTestStronghold(
                strongholdMap.getStrongholds()[player2.getPosX()][player2.getPosY()],
                player2.getPlayerId(),
                player2.getPosX(),
                player2.getPosY()
        );
        assertEquals(1, player1.getScore());
        assertEquals(2, player2.getScore());
    }

    @Test
    public void testAddPlayerOneTime() {
        strongholdMap.addPlayer();
        assertEquals(1, strongholdMap.getPlayers().size());
        // TODO: change it to test player directly, also change test stronghold
        combineTestPlayer(strongholdMap.getPlayers().get(0), 1, null, null, strongholdMap);
    }

    @Test
    public void testAddPlayerTwoTimes() {
        strongholdMap.addPlayer();
        strongholdMap.addPlayer();
        assertEquals(2, strongholdMap.getPlayers().size());
        combineTestPlayer(strongholdMap.getPlayers().get(0), 1, null, null, strongholdMap);
        combineTestPlayer(strongholdMap.getPlayers().get(1), 2, null, null, strongholdMap);
    }

    @Test
    public void testAddPlayerWithDataOneTime() {
        strongholdMap.addPlayerWithData(player1);
        assertEquals(1, strongholdMap.getPlayers().size());
        combineTestPlayer(
                strongholdMap.getPlayers().get(0),
                player1.getPlayerId(),
                player1.getPosX(),
                player1.getPosY(),
                strongholdMap
        );
    }

    @Test
    public void testAddPlayerWithDataTwoTimes() {
        strongholdMap.addPlayerWithData(player1);
        strongholdMap.addPlayerWithData(player2);
        assertEquals(2, strongholdMap.getPlayers().size());
        combineTestPlayer(
                strongholdMap.getPlayers().get(0),
                player1.getPlayerId(),
                player1.getPosX(),
                player1.getPosY(),
                strongholdMap
        );
        combineTestPlayer(
                strongholdMap.getPlayers().get(1),
                player2.getPlayerId(),
                player2.getPosX(),
                player2.getPosY(),
                strongholdMap
        );
    }

    @Test
    public void testAddStrongholdOneTime() {
        strongholdMap.addStronghold(sh1);
        combineTestStronghold(sh1, player1.getPlayerId(), 0, 0);
    }

    @Test
    public void testAddStrongholdTwoTimes() {
        strongholdMap.addStronghold(sh1);
        strongholdMap.addStronghold(sh2);
        combineTestStronghold(sh1, player1.getPlayerId(), 0, 0);
        combineTestStronghold(sh2, player2.getPlayerId(), 0, 1);
    }

    @Test
    public void testOccupyStronghold() {
        strongholdMap.occupyStronghold(sh1.getPosX(), sh2.getPosY(), player1);
        combineTestStronghold(sh1, player1.getPlayerId(), 0, 0);
    }

    @Test
    public void testOccupyStrongholdWithOwner() {
        strongholdMap.occupyStronghold(sh1.getPosX(), sh2.getPosY(), player2);
        strongholdMap.occupyStronghold(sh1.getPosX(), sh2.getPosY(), player1);
        combineTestStronghold(sh1, player1.getPlayerId(), 0, 0);
    }

    @Test
    public void testCalScores() {
        Stronghold sh1 = new Stronghold(player1, 0, 0);
        Stronghold sh2 = new Stronghold(player2, 0, 1);
        Stronghold sh3 = new Stronghold(player1, 1, 0);
        strongholdMap.addStronghold(sh1);
        strongholdMap.addStronghold(sh2);
        strongholdMap.addStronghold(sh3);
        assertEquals(2, player1.getScore());
        assertEquals(1, player2.getScore());
    }

    private void combineTestConstructor(StrongholdMap testMap, int height, int width) {
        assertEquals(width, testMap.width);
        assertEquals(height, testMap.height);
        assertEquals(height, testMap.getStrongholds().length);
        assertEquals(width, testMap.getStrongholds()[0].length);
        assertEquals(0, testMap.getPlayers().size());
    }

    private void combineTestPlayer(Player testPlayer, int playerId, Integer posX, Integer posY, StrongholdMap testMap) {
        assertEquals(playerId, testPlayer.getPlayerId());
        assertEquals(posX, testPlayer.getPosX());
        assertEquals(posY, testPlayer.getPosY());
        assertEquals(strongholdMap, testPlayer.getStrongholdMap());
    }

    private void combineTestStronghold(Stronghold stronghold, int playerId, int posX, int posY) {
        assertNotNull(stronghold);
        assertEquals(playerId, stronghold.getOwner().getPlayerId());
        assertEquals(posX, stronghold.getPosX());
        assertEquals(posY, stronghold.getPosY());
    }

    private void combineTestStartMatch(StrongholdMap testMap, int[][] positionList, int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            strongholdMap.addPlayer();
        }
        strongholdMap.startMatch();
        int matchNum = 0;
        for (int[] position:positionList) {
            int posX = position[0];
            int posY = position[1];
            for (Player player:testMap.getPlayers()) {
                if (player.getPosX() == posX && player.getPosY() == posY) {
                    matchNum++;
                }
            }
        }
        assertEquals(numPlayers, matchNum);
        for (Player player: testMap.getPlayers()) {
            assertEquals(1, player.getScore());
        }
    }

    private int[][] getPositionListForTwoPlayers(StrongholdMap testMap) {
        return new int[][]{
                {testMap.height / 2, testMap.width / 4},
                {testMap.height / 2, testMap.width / 4 * 3}
        };
    }

    private int[][] getPositionListForThreePlayers(StrongholdMap testMap) {
        return new int[][]{
                {testMap.height / 4, testMap.width / 2},
                {testMap.height / 4 * 3, testMap.width / 4},
                {testMap.height / 4 * 3, testMap.width / 4 * 3}
        };
    }

    private int[][] getPositionListForFourPlayers(StrongholdMap testMap) {
        return new int[][]{
                {testMap.height / 4, testMap.width / 4},
                {testMap.height / 4, testMap.width / 4 * 3},
                {testMap.height / 4 * 3, testMap.width / 4},
                {testMap.height / 4 * 3, testMap.width / 4 * 3}
        };
    }
}