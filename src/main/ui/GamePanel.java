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

    public GamePanel() {
        // sets the size of JPanel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        // Enabling this can improve game's rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH = new KeyHandler());
        // keep listening?
        this.setFocusable(true);
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
        gameThread = new Thread();
        gameThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Font maruMonica;
        InputStream is = getClass().getClassLoader().getResourceAsStream("fonts/x12y16pxMaruMonica.ttf");
        try {
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, TILE_SIZE));
        g2.setColor(Color.white);
        g2.drawString("Strongholdon", TILE_SIZE * 2, TILE_SIZE * 2);
        g2.dispose();
    }

    private void update() {
        // stub
    }
}
