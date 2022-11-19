package persistence;

import exception.PlayerDoesNotExist;
import exception.model.StrongholdMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Carter, P (2021) JsonSerializationDemo (Version 2.0)
// [Source code]. https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest{

    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyMatch() {
        try {
            StrongholdMap mp = new StrongholdMap(9, 9);
            JsonWriter writer = new JsonWriter("./data/test/testWriterEmptyMatch.json");
            writer.open();
            writer.write(mp);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/testWriterEmptyMatch.json");
            mp = reader.read();
            assertEquals(9, mp.getHeight());
            assertEquals(9, mp.getWidth());
            int[][] existStrongholds = new int[0][3];
            checkStrongholds(existStrongholds, mp.getStrongholds());
            assertEquals(0, mp.getPlayers().size());
        } catch (IOException | PlayerDoesNotExist e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralMatch() {
        try {
            writeGeneral();
            readGeneral();
        } catch (IOException | PlayerDoesNotExist e) {
            fail("Exception should not have been thrown");
        }
    }

    private void writeGeneral() throws IOException, PlayerDoesNotExist {
        JsonReader reader = new JsonReader("./data/test/testReaderGeneralMatch.json");
        StrongholdMap mp = reader.read();
        JsonWriter writer = new JsonWriter("./data/test/testWriterGeneralMatch.json");
        writer.open();
        writer.write(mp);
        writer.close();
    }

    private void readGeneral() throws IOException, PlayerDoesNotExist {
        JsonReader reader = new JsonReader("./data/test/testReaderGeneralMatch.json");
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
    }
}
