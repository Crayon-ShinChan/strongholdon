package persistence;

import model.StrongholdMap;
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
            // TODO: to learn what is fail
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyStrongholdMap() {
        JsonReader reader = new JsonReader("./data/testEmptyMatch.json");
        try {
            StrongholdMap mp = reader.read();
            assertEquals(9, mp.getHeight());
            assertEquals(9, mp.getWidth());
            int[][] existStrongholds = new int[0][3];
            checkStrongholds(existStrongholds, mp.getStrongholds());
            assertEquals(0, mp.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralStrongholdMap() {
        JsonReader reader = new JsonReader("./data/testGeneralMatch.json");
        try {
            StrongholdMap mp = reader.read();
            assertEquals(9, mp.getHeight());
            assertEquals(9, mp.getWidth());
            int[][] existStrongholds = {
                    {3, 4, 1},
                    {5, 6, 2},
                    {5, 7, 2},
            };
            int[][] existPlayers = {
                    {1, 3, 5, 1},
                    {2, 5, 7, 2},
            };
            checkStrongholds(existStrongholds, mp.getStrongholds());
            checkPlayers(existPlayers, mp.getPlayers(), mp);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
