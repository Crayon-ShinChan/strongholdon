package ui;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static ui.GamePanel.*;

public class Drawer {
    private GamePanel gp;
    private Graphics2D g2;
    private Font maruMonicaFont;
    private Menu menu;

    public Drawer(GamePanel gp) {
        this.gp = gp;

        InputStream is = getClass().getClassLoader().getResourceAsStream("fonts/x12y16pxMaruMonica.ttf");
        // TODO: ask the best practice to catch
        try {
            maruMonicaFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        String[] menuList = {"NEW GAME", "RESUME GAME", "QUIT"};
        menu = new Menu(menuList);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonicaFont);
//        g2.setColor(Color.white);

        if (gp.getGameState() == GameState.TITLE_SCREEN) {
            drawTitleScreen();
        } else if (gp.getGameState() == GameState.CHOOSING_PLAYER) {
            // stub
        }
    }

    public void increaseMenuCursorNum() {
        menu.increaseCursorNum();
    }

    public void decreaseMenuCursorNum() {
        menu.decreaseCursorNum();
    }

    public int getMenuCursorNum() {
        return menu.getCursorNum();
    }

    public void initialMenu() {
        GameState gs = gp.getGameState();
        String[] menuList;
        if (gs == GameState.TITLE_SCREEN) {
            menu = new Menu(new String[] {"NEW GAME", "RESUME GAME", "QUIT"});
        } else if (gs == GameState.PAUSE) {
            menu = new Menu(new String[] {"SAVE GAME", "QUIT GAME"});
        } else {
            menu = null;
        }
    }

    private void drawTitleScreen() {
        g2.setColor(new Color(0xEABC52));
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // title
        drawTitle();

        // menu
        menu.draw(g2, TILE_SIZE * 9);
    }

    private void drawTitle() {
        String title = "STRONGHOLDON";
        // set the font first to allow getXForCenteredText to get the correct width
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, TILE_SIZE * 2));
        int width = getXForCenteredText(title);
        int height = TILE_SIZE * 5;
        g2.setColor(Color.black);
        g2.drawString(title, width - 3, height + 3);
        g2.setColor(Color.white);
        g2.drawString(title, width, height);
    }

    private int getXForCenteredText(String text) {
        int width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return SCREEN_WIDTH / 2 - width / 2;
    }
}
