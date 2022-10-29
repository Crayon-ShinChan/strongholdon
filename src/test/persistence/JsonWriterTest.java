package persistence;

import model.StrongholdMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

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
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMatch.json");
            writer.open();
            writer.write(mp);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMatch.json");
            mp = reader.read();
            assertEquals(9, mp.getHeight());
            assertEquals(9, mp.getWidth());
            int[][] existStrongholds = new int[0][3];
            checkStrongholds(existStrongholds, mp.getStrongholds());
            assertEquals(0, mp.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralMatch() {
        try {
            writeGeneral();
            readGeneral();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void writeGeneral() throws IOException {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMatch.json");
        StrongholdMap mp = reader.read();
        JsonWriter writer = new JsonWriter("./data/testWriterGeneralMatch.json");
        writer.open();
        writer.write(mp);
        writer.close();
    }

    private void readGeneral() throws IOException {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMatch.json");
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
    }
}
