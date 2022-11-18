package ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel implements Runnable {
    public static final int ORIGINAL_TILE_SIZE = 24;
    public static final int SCALE = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int MAX_SCREEN_HEIGHT = 16;
    public static final int MAX_SCREEN_WIDTH = 16;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_HEIGHT;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_HEIGHT;
    public static final int FPS = 60;

    private KeyHandler keyH;
    private Thread gameThread;
    private GameState gameState;
    private Drawer drawer;

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
    }

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
                update();
                // 2 DRAW: draw the screen
                repaint();
                delta -= drawInterval;
                drawCount++;
            }
            if (timer >= 1e9) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawer.draw(g2);
        g2.dispose();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void changeGameState(GameState newGameState) {
        if (newGameState != gameState) {
            gameState = newGameState;
            drawer.initialMenu();
        }
    }

    private void update() {
        if (gameState == GameState.MATCH) {
            // update the player status and something
        }
    }
}
