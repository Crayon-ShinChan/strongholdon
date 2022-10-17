package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrongholdTest {
    private Stronghold sh1, sh2, sh3;

    @BeforeEach
    public void beforeRun() {
        Player p1, p2;
        p1 = new Player(1, 0, 0);
        p2 = new Player(2,  StrongholdMap.DEFAULT_HEIGHT - 1, StrongholdMap.DEFAULT_WIDTH - 1);
        sh1 = new Stronghold(p1, 0, 0);
        sh2 = new Stronghold(p2, StrongholdMap.DEFAULT_HEIGHT - 1, StrongholdMap.DEFAULT_WIDTH - 1);
        sh3 = new Stronghold(
                p1,
                (StrongholdMap.DEFAULT_HEIGHT - 1) / 2,
                (StrongholdMap.DEFAULT_WIDTH - 1) / 2
        );
    }

    @Test
    public void testConstructor() {
        // TODO: ask TA for help: Is testing uuid enough for test player? How to test whether two classes are equal?
        //assertEquals(s1.getOwner(), new Player(1, 0, 0));
        assertEquals(1, sh1.getOwner().getPlayerId());
        assertEquals(0, sh1.getPosX());
        assertEquals(0, sh1.getPosY());
        assertEquals(2, sh2.getOwner().getPlayerId());
        assertEquals(StrongholdMap.DEFAULT_HEIGHT - 1, sh2.getPosX());
        assertEquals(StrongholdMap.DEFAULT_WIDTH - 1, sh2.getPosY());
        assertEquals(1, sh3.getOwner().getPlayerId());
        assertEquals((StrongholdMap.DEFAULT_HEIGHT - 1) / 2, sh3.getPosX());
        assertEquals((StrongholdMap.DEFAULT_WIDTH - 1) / 2, sh3.getPosY());
    }
}
