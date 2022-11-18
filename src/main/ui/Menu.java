package ui;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static ui.GamePanel.SCREEN_WIDTH;
import static ui.GamePanel.TILE_SIZE;

public class Menu {
    private String[] menuList;
    private int cursorNum;
    private Graphics2D g2;

    public Menu(String[] manuList) {
        this.menuList = manuList;
        this.cursorNum = 0;
    }

    public void draw(Graphics2D g2, int topHeight) {
        this.g2 = g2;
        int fontSize = TILE_SIZE;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, fontSize));
        int lineSpace = TILE_SIZE / 3;
        int height = topHeight;
        for (int i = 0; i < menuList.length; i++) {
            int width = getXForCenteredText(menuList[i]);
            if (cursorNum == i) {
                g2.drawString(">", width - fontSize, height);
            }
            g2.drawString(menuList[i], width, height);
            height += fontSize + lineSpace;
        }
    }

    public void increaseCursorNum() {
        cursorNum = min(cursorNum + 1, menuList.length - 1);
    }

    public void decreaseCursorNum() {
        cursorNum = max(cursorNum - 1, 0);
    }

    private int getXForCenteredText(String text) {
        int width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return SCREEN_WIDTH / 2 - width / 2;
    }
}
