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

    // REQUIRES: length of menuList > 0
    public Menu(String[] menuList) {
        this.menuList = menuList;
        this.cursorNum = 0;
    }

    public void draw(Graphics2D g2, int topY) {
        this.g2 = g2;
        int fontSize = TILE_SIZE;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, fontSize));
        int lineSpace = TILE_SIZE * 2 / 3;
        int y = topY;
        int fontHeight = (int) g2.getFontMetrics().getStringBounds(menuList[0], g2).getHeight();
        int barHeight = fontHeight + (fontSize / 4) * 2;
        for (int i = 0; i < menuList.length; i++) {
            int x = getXForCenteredText(menuList[i]);
            int fontWidth = (int) g2.getFontMetrics().getStringBounds(menuList[i], g2).getWidth();
            int barWidth = fontWidth + (fontSize / 4) * 2;
            g2.setColor(new Color(0x1543AD));
            g2.fillRoundRect(x - fontSize / 4, y - fontSize / 8 - fontSize, barWidth, barHeight, 5, 5);
            g2.setColor(Color.white);
            if (this.cursorNum == i) {
                g2.drawString(">", x - fontSize, y);
            }
            g2.drawString(menuList[i], x, y);
            y += fontHeight + lineSpace;
        }
    }

    public void increaseCursorNum() {
        cursorNum = min(cursorNum + 1, menuList.length - 1);
    }

    public void decreaseCursorNum() {
        cursorNum = max(cursorNum - 1, 0);
    }

    public int getCursorNum() {
        return cursorNum;
    }

    private int getXForCenteredText(String text) {
        int width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return SCREEN_WIDTH / 2 - width / 2;
    }
}
