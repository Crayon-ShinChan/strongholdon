package persistence;

import exception.PlayerDoesNotExist;
import model.Player;
import model.Stronghold;
import model.StrongholdMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Carter, P (2021) JsonSerializationDemo (Version 2.0)
// [Source code]. https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// A reader that read previous match states
public class JsonReader {
    private String source;

    // EFFECTS: constructs the file source to read data
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads the state of a match and returns it
    // throws IOException if an error occurs reading data from file
    public StrongholdMap read() throws IOException, PlayerDoesNotExist {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStrongholdMap(jsonObject);
    }

    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses the game map from JSON object and returns it
    private StrongholdMap parseStrongholdMap(JSONObject jsonObject) throws PlayerDoesNotExist {
        int height = jsonObject.getInt("height");
        int width = jsonObject.getInt("width");
        StrongholdMap mp = new StrongholdMap(height, width);
        addPlayers(mp, jsonObject);
        addStrongholds(mp, jsonObject);
        return mp;
    }

    // EFFECTS: add players into the map
    private void addPlayers(StrongholdMap mp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            int playerId = nextPlayer.getInt("playerId");
            int posX = nextPlayer.getInt("posX");
            int posY = nextPlayer.getInt("posY");
            int score = nextPlayer.getInt("score");
            Player p = new Player(playerId, posX, posY, mp);
            p.setScore(score);
            mp.addPlayerWithData(p);
        }
    }

    // EFFECTS: add strongholds into the map
    private void addStrongholds(StrongholdMap mp, JSONObject jsonObject) throws PlayerDoesNotExist {
        JSONArray jsonArray = jsonObject.getJSONArray("strongholds");
        for (Object json : jsonArray) {
            JSONObject nextHold = (JSONObject) json;
            int posX = nextHold.getInt("posX");
            int posY = nextHold.getInt("posY");
            Player owner = findOwner(nextHold.getInt("playerId"), mp);
            mp.addStronghold(new Stronghold(owner, posX, posY));
        }
    }

    // TODO: exception to handle the player id
    // REQUIRES: playerId should exist
    // EFFECTS: returns player object by searching his id
    private Player findOwner(int playerId, StrongholdMap mp) throws PlayerDoesNotExist {
        ArrayList<Player> players = mp.getPlayers();
        for (Player p : players) {
            if (p.getPlayerId() == playerId) {
                return p;
            }
        }
        throw new PlayerDoesNotExist();
    }
}
