package persistence;

import exception.PlayerDoesNotExist;
import exception.model.StrongholdMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Carter, P (2021) JsonSerializationDemo (Version 2.0)
// [Source code]. https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            StrongholdMap mp = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (PlayerDoesNotExist e) {
            fail("A stronghold has a player does not exist");
        }
    }

    @Test
    public void testReaderEmptyStrongholdMap() {
        JsonReader reader = new JsonReader("./data/test/testReaderEmptyMatch.json");
        try {
            StrongholdMap mp = reader.read();
            int[][] existStrongholds = new int[0][3];
            checkStrongholds(existStrongholds, mp.getStrongholds());
            assertEquals(0, mp.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (PlayerDoesNotExist e) {
            fail("A stronghold has a player does not exist");
        }
    }

    @Test
    public void testReaderGeneralStrongholdMap() {
        JsonReader reader = new JsonReader("./data/test/testReaderGeneralMatch.json");
        try {
            StrongholdMap mp = reader.read();
            assertEquals(13, mp.getHeight());
            assertEquals(16, mp.getWidth());
            int[][] existStrongholds = {
                    {5, 4, 1},
                    {6, 4, 1},
                    {6, 12, 0}
            };
            int[][] existPlayers = {
                    {0, 6, 12, 1, 0, -1},
                    {1, 5, 4, 2, 2, 73}
            };
            checkStrongholds(existStrongholds, mp.getStrongholds());
            checkPlayers(existPlayers, mp.getPlayers(), mp);
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (PlayerDoesNotExist e) {
            fail("A stronghold has a player does not exist");
        }
    }

    @Test
    public void testReaderPlayerDoesNotExist() {
        JsonReader reader = new JsonReader("./data/test/testReaderPlayerDoesNotExist.json");
        try {
            StrongholdMap mp = reader.read();
            int[][] existStrongholds = {
                    {3, 4, 1},
                    {5, 6, 2},
                    {5, 7, 2},
            };
            checkStrongholds(existStrongholds, mp.getStrongholds());
            fail("PlayerDoesNotExist expected");
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (PlayerDoesNotExist e) {
            // pass
        }
    }
}
