package ui;

import exception.PlayerDoesNotExist;
import model.Player;
import model.Stronghold;
import model.StrongholdMap;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Game: Strongholdon
public class StrongholdonApp {
    private static final String JSON_STORE = "./data/strongholdMap.json";
    private static final int SMALL_HEIGHT = 9;
    private static final int SMALL_WIDTH = 9;
    private StrongholdMap strongholdMap;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the game
    public StrongholdonApp() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runStrongholdon();
    }

    // MODIFIES: this
    // EFFECTS: process the input before a match
    private void runStrongholdon() {
        boolean keepApp = true;
        String command;

        strongholdMap = new StrongholdMap(SMALL_HEIGHT, SMALL_WIDTH);

        while (keepApp) {
            displayAppMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepApp = false;
            } else if (command.equals("p")) {
                runMatch(false);
            } else if (command.equals("a")) {
                processAddPlayer();
            } else if (command.equals("r")) {
                resumeMatch();
            } else {
                System.out.println("Command not valid...");
            }
        }

        System.out.println("\nGoodbye!");
    }

    private void resumeMatch() {
        try {
            strongholdMap = jsonReader.read();
            System.out.println("Resuming the last match");
            runMatch(true);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (PlayerDoesNotExist e) {
            System.out.println("A stronghold has a player does not exist");
        }
    }

    // EFFECTS: print the menu before a match
    private void displayAppMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add A Player");
        System.out.println("\tp -> Play");
        System.out.println("\tr -> Resume The Last Match");
        System.out.println("\tq -> Quit");
    }

    // TODO: check if style checker count empty lines
    // MODIFIES: this
    // EFFECTS: process the input for the match
    private void runMatch(boolean resume) {
        if (!checkPlayerNum(true)) {
            return;
        }
        if (!resume) {
            strongholdMap.startMatch();
        }
        keepMatch();
        displayMatchResult();
        System.out.println("\nGame Over!");
        strongholdMap = new StrongholdMap(SMALL_HEIGHT, SMALL_WIDTH);
    }

    // EFFECTS: continue the match
    private void keepMatch() {
        boolean inMatch = true;
        int turnNum = 1;
        String command;
        while (inMatch) {
            displayStrongholdMap();
            displayMatchMenu(turnNum);
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("t")) {
                inMatch = calKeepMatch(turnNum);
            } else if (command.equals("r")) {
                inMatch = calKeepMatch(turnNum);
                saveStrongholdMap();
            } else {
                if (processMoveCommand(turnNum, command)) {
                    turnNum = calNextTurnNum(turnNum);
                }
            }
        }
    }

    private void saveStrongholdMap() {
        try {
            jsonWriter.open();
            jsonWriter.write(strongholdMap);
            jsonWriter.close();
            System.out.println("Saved the current match" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: return true if the number of players is valid; otherwise return false
    private boolean checkPlayerNum(boolean startMatch) {
        int playNum = strongholdMap.getPlayers().size();
        if (playNum < 2 && startMatch) {
            System.out.println("\nThere must be at least 2 players");
            System.out.println("\nThe number of current players: " + playNum);
            return false;
        } else if (playNum >= 4 && !startMatch) {
            System.out.println("\nUp to four players allowed!");
            System.out.println("\nThe number of current players: " + playNum);
            return false;
        }
        return true;
    }

    // EFFECTS: display the StrongholdMap
    private void displayStrongholdMap() {
        Stronghold[][] strongholds = strongholdMap.getStrongholds();
        for (int i = 0; i < strongholds.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < strongholds[0].length; j++) {
                if (strongholds[i][j] == null) {
                    System.out.print(0 + "  ");
                } else {
                    String locationIndicator = " ";
                    for (Player p:strongholdMap.getPlayers()) {
                        if (p.getPosX() == i && p.getPosY() == j) {
                            locationIndicator = "*";
                        }
                    }
                    System.out.print(strongholds[i][j].getOwner().getPlayerId() + locationIndicator + " ");
                }
            }
        }
    }

    // EFFECTS: display the menu for a match
    private void displayMatchMenu(int turnNum) {
        System.out.println("\nGame Rule: Occupy More Strongholds!");
        System.out.println("Tip: You can form circles with your strongholds");
        System.out.println("\nSelect from:");
        System.out.println(
                "\tw -> Move Player " + strongholdMap.getPlayers().get(turnNum - 1).getPlayerId() + " Up"
        );
        System.out.println(
                "\ts -> Move Player " + strongholdMap.getPlayers().get(turnNum - 1).getPlayerId() + " Down"
        );
        System.out.println(
                "\ta -> Move Player " + strongholdMap.getPlayers().get(turnNum - 1).getPlayerId() + " To The Left"
        );
        System.out.println(
                "\td -> Move Player " + strongholdMap.getPlayers().get(turnNum - 1).getPlayerId() + " To The Right"
        );
        if (turnNum == 1) {
            System.out.println("\tr -> Return To Title And Save The Match");
            System.out.println("\tt -> Time Is Up");
        }
    }

    // MODIFIES: this
    // EFFECTS: move a player
    private boolean processMoveCommand(int turnNum, String command) {
        Player player = strongholdMap.getPlayers().get(turnNum - 1);
        ArrayList<String> validCommand = new ArrayList<>(Arrays.asList("w", "s", "a", "d"));
        if (!validCommand.contains(command)) {
            System.out.println("Command not valid...");
            return false;
        }
        player.move(command);
        return true;
    }

    // EFFECTS: find who is the next player to move
    private int calNextTurnNum(int turnNum) {
        if (turnNum == strongholdMap.getPlayers().size()) {
            displayMatchResult();
            return 1;
        }
        return turnNum + 1;
    }

    // EFFECTS: display the match result
    private void displayMatchResult() {
        System.out.println("\nMatch result:");
        for (Player p:strongholdMap.getPlayers()) {
            System.out.println("\tPlayer " + p.getPlayerId() + ": " + p.getScore());
        }
    }

    // EFFECTS: find if we should continue the match
    private boolean calKeepMatch(int turnNum) {
        if (turnNum > 1) {
            System.out.println("Command not valid...");
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: add player to the map, the max number is 4
    private void processAddPlayer() {
        if (!checkPlayerNum(false)) {
            return;
        }
        strongholdMap.addPlayer();
        System.out.println("The number of current players: " + strongholdMap.getPlayers().size());
    }
}
