package ui;

import exception.PlayerDoesNotExist;
import model.Event;
import model.EventLog;
import model.Player;
import model.StrongholdMap;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// GamePanel to control time, data and drawing
public class GamePanel extends JPanel implements Runnable {
    public static final int ORIGINAL_TILE_SIZE = 24;
    public static final int SCALE = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_HEIGHT = 16;
    public static final int MAX_SCREEN_WIDTH = 16;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_HEIGHT;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_HEIGHT;
    public static final int FPS = 60;
    private static final String JSON_STORE = "./data/save/strongholdMap.json";

    private KeyHandler keyH;
    private Thread gameThread;
    private GameState gameState;
    private Drawer drawer;
    private StrongholdMap strongholdMap;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Sound sound;

    // EFFECTS: construct GamePanel
    public GamePanel() {
        // sets the size of JPanel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        // Enabling this can improve game's rendering performance
        this.setDoubleBuffered(true);
        this.drawer = new Drawer(this);
        this.keyH = new KeyHandler(this, this.drawer);
        this.addKeyListener(keyH);
        // keep listening?
        this.setFocusable(true);
        this.gameState = GameState.TITLE_SCREEN;
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        this.sound = new Sound();
    }

    // EFFECTS: runs the game in FPS, controls time
    @Override
    public void run() {
        double drawInterval = 1e9 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += currentTime - lastTime;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= drawInterval) {
                // 1 UPDATE: update information such as character positions
                update(currentTime);
                // 2 DRAW: draw the screen
                repaint();
                delta -= drawInterval;
                drawCount++;
            }
            if (timer >= 1e9) {
//                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: start game thread
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(0);
    }

    // EFFECTS: paint components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawer.draw(g2);
        g2.dispose();
    }

    // EFFECTS: return game state
    public GameState getGameState() {
        return gameState;
    }

    // TODO: make it not changeable (collection), like lab 8
    // EFFECTS: return map
    public StrongholdMap getStrongholdMap() {
        return strongholdMap;
    }

    // EFFECTS: initial map
    public void initialStrongholdMap() {
        strongholdMap = new StrongholdMap();
    }

    // MODIFIES: this
    // EFFECTS: change game states and control music
    public void changeGameState(GameState newGameState) {
        if (newGameState != gameState) {
            if (newGameState == GameState.TITLE_SCREEN) {
                stopMusic();
                playMusic(0);
            } else if (newGameState == GameState.PAUSE) {
                pauseMusic();
            } else if (newGameState == GameState.MATCH && gameState == GameState.PAUSE) {
                playMusic(1);
            } else if (newGameState == GameState.MATCH_END) {
                stopMusic();
                playMusic(0);
            }
            gameState = newGameState;
            drawer.initialMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: update data and music
    private void update(long currentTime) {
        if (gameState == GameState.MATCH) {
            int currentTimeUnit = strongholdMap.getCurrentTimeUnit();
            if (currentTimeUnit % (FPS * 10) == 0) {
                stopMusic();
                playMusicWithoutLoop(1);
            }
            if (strongholdMap.getIsTimeUp()) {
                changeGameState(GameState.MATCH_END);
            } else {
                strongholdMap.increaseCurrentTimeUnit();
            }
        }
    }

    // EFFECTS: save the current match to a json file
    public void saveStrongholdMap() {
        try {
            jsonWriter.open();
            jsonWriter.write(strongholdMap);
            jsonWriter.close();
            System.out.println("Saved the current match" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: resume the match
    public void loadStrongholdMap() {
        try {
            strongholdMap = jsonReader.read();
            System.out.println("Resuming the last match");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (PlayerDoesNotExist e) {
            System.out.println("A stronghold has a player does not exist");
        }
        resumeMusic();
    }

    // MODIFIES: this
    // EFFECTS: resumes music
    private void resumeMusic() {
        long clipTime = (long) (strongholdMap.getCurrentTimeUnit() % (FPS * 10))  * 10 * 1000000 / (FPS * 10);
        stopMusic();
        sound.setClipTime(clipTime);
        sound.setStatus(2);
        playMusicWithoutLoop(1);
    }

    // MODIFIES: this
    // EFFECTS: restarts match
    public void restartMatch() {
        ArrayList<Player> playerList = getStrongholdMap().getPlayers();
        strongholdMap = new StrongholdMap();
        for (Player p : playerList) {
            Player newPlayer = new Player(p.getPlayerId(), p.getResourceId());
            strongholdMap.addPlayerWithData(newPlayer);
        }
        strongholdMap.startMatch();
    }

    // MODIFIES: this
    // EFFECTS: play music with loop
    private void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    // MODIFIES: this
    // EFFECTS: play music without loop
    private void playMusicWithoutLoop(int i) {
        sound.setFile(i);
        sound.play();
    }

    // MODIFIES: this
    // EFFECTS: stop music
    private void stopMusic() {
        sound.stop();
    }

    // MODIFIES: this
    // EFFECTS: pause music
    private void pauseMusic() {
        sound.pause();
    }

    // EFFECTS: prints log
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }
}
