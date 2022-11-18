package ui;

import model.Player;
import model.Stronghold;
import model.StrongholdMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static model.StrongholdMap.*;
import static ui.GamePanel.*;

public class Drawer {
    private GamePanel gp;
    private Graphics2D g2;
    private Font maruMonicaFont;
    private Menu menu;
    private ArrayList<Integer> playerStack;
    private int cursorPlayerNum;

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

        GameState gs = gp.getGameState();

        if (gs == GameState.TITLE_SCREEN) {
            drawTitleScreen();
        } else if (gs == GameState.CHOOSING_PLAYER) {
            drawChoosingPlayer();
        } else if (gs == GameState.MATCH) {
            drawMatch();
        } else if (gs == GameState.PAUSE) {
            drawPause();
        } else if (gs == GameState.MATCH_END) {
            drawMatchEnd();
        }
    }

    private void drawMatchEnd() {
        g2.setColor(new Color(0xEABC52));
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        menu.draw(g2, TILE_SIZE * 11);
        drawPlayerResults();
    }

    private void drawPlayerResults() {

    }

    private void drawPause() {
        g2.setColor(new Color(0xEABC52));
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        menu.draw(g2, TILE_SIZE * 7);
    }

    private void drawMatch() {
        g2.setColor(new Color(0xEABC52));
        g2.fillRect(0, 0, SCREEN_WIDTH, DEFAULT_HEIGHT * TILE_SIZE);
        g2.setColor(Color.white);
        g2.fillRect(0, DEFAULT_HEIGHT * TILE_SIZE, SCREEN_WIDTH, (SCREEN_HEIGHT - DEFAULT_HEIGHT * TILE_SIZE));
//        drawLandInMatch();

        drawStrongholdInMatch();
        drawPlayerInMatch();
        drawTimer();
    }

    private void drawTimer() {
        int timer = GAME_SECOND - gp.getStrongholdMap().getCurrentTimeUnit() / FPS;
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, TILE_SIZE * 2));
        g2.drawString(String.valueOf(timer), TILE_SIZE * 6 / 10, SCREEN_WIDTH - TILE_SIZE * 8 / 10);
    }

    private void drawLandInMatch() {
        BufferedImage landImage;
        try {
            landImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("lands/emptyLand.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < DEFAULT_HEIGHT; i++) {
            for (int j = 0; j < DEFAULT_WIDTH; j++) {
                g2.drawImage(landImage, j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    // TODO: return unchangeable array
    private void drawStrongholdInMatch() {
        Stronghold[][] strongholds = gp.getStrongholdMap().getStrongholds();
        for (int i = 0; i < DEFAULT_HEIGHT; i++) {
            for (int j = 0; j < DEFAULT_WIDTH; j++) {
                if (strongholds[i][j] == null) {
                    continue;
                }
                BufferedImage image = getStrongholdImage(strongholds[i][j]);
                g2.drawImage(image, j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    private BufferedImage getStrongholdImage(Stronghold stronghold) {
        int resourceId = stronghold.getOwner().getResourceId();
        String imagePath = "players/hold" + resourceId + ".png";
        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    private void drawPlayerInMatch() {
        ArrayList<Player> playerList = gp.getStrongholdMap().getPlayers();
        for (Player p : playerList) {
            int x = p.getPosY() * TILE_SIZE;
            int y = p.getPosX() * TILE_SIZE;
            String playerPath = "players/player" + p.getResourceId() + ".png";
            BufferedImage playerImage;
            try {
                playerImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(playerPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            g2.drawImage(playerImage, x, y, TILE_SIZE, TILE_SIZE, null);
        }
    }


    private void drawChoosingPlayer() {
        g2.setColor(new Color(0xEABC52));
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        menu.draw(g2, TILE_SIZE * 8);

        drawPlayerList();
    }

    private void drawPlayerList() {
        int imageSize = TILE_SIZE * 3;
        int space = TILE_SIZE * 2;
        int x = SCREEN_WIDTH / 2 - imageSize / 2 - space - imageSize;
        int y = TILE_SIZE * 2;
        for (int i = 0; i < MAX_PLAYER_NUM; i++) {
            String playerPath = "players/player" + i + ".png";
            String holdPath = "players/hold" + i + ".png";
            BufferedImage playerImage;
            BufferedImage holdImage = null;
            try {
                playerImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(playerPath));
                if (playerStack.contains(i)) {
                    holdImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(holdPath));
                } else if (cursorPlayerNum == i) {
                    holdImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("players/holdEmpty.png"));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            paintPlayerAndHold(holdImage, playerImage, x, y, imageSize);
            x += space + imageSize;
        }
    }

    private void paintPlayerAndHold(BufferedImage holdImage, BufferedImage playerImage, int x, int y, int imageSize) {
        if (holdImage != null) {
            g2.drawImage(holdImage, x, y, imageSize, imageSize, null);
        }
        g2.drawImage(playerImage, x, y, imageSize, imageSize, null);
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
        } else if (gs == GameState.CHOOSING_PLAYER) {
            playerStack = new ArrayList<>();
            cursorPlayerNum = 0;
            menu = new Menu(new String[] {"ADD PLAYER", "REMOVE PLAYER", "START", "BACK"});
        } else if (gs == GameState.PAUSE) {
            menu = new Menu(new String[] {"RESUME", "SAVE AND QUIT MATCH"});
        } else if (gs == GameState.MATCH_END) {
            menu = new Menu(new String[] {"PLAY AGAIN", "RETURN TO TITLE SCREEN"});
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

    public void decreaseCursorPlayerNum() {
        for (int i = cursorPlayerNum - 1; i >= 0; i--) {
            if (!playerStack.contains(i)) {
                cursorPlayerNum = i;
                break;
            }
        }
        cursorPlayerNum = max(0, cursorPlayerNum);
    }

    public void increaseCursorPlayerNum() {
        for (int i = cursorPlayerNum + 1; i < MAX_PLAYER_NUM; i++) {
            if (!playerStack.contains(i)) {
                cursorPlayerNum = i;
                break;
            }
        }
        cursorPlayerNum = min(MAX_PLAYER_NUM, cursorPlayerNum);
    }

    public int getCursorPlayerNum() {
        return cursorPlayerNum;
    }

    public void addPlayer() {
        if (playerStack.contains(cursorPlayerNum)) {
            return;
        }
        playerStack.add(cursorPlayerNum);
        int nextCursorPlayerNum = 0;
        for (int i = 0; i < MAX_PLAYER_NUM; i++) {
            if (!playerStack.contains(i)) {
                nextCursorPlayerNum = i;
                break;
            }
        }
        cursorPlayerNum = nextCursorPlayerNum;
    }

    public void removePlayer() {
        if (playerStack.size() == 0) {
            return;
        }
        playerStack.remove(playerStack.size() - 1);
        int nextCursorPlayerNum = 0;
        for (int i = 0; i < MAX_PLAYER_NUM; i++) {
            if (!playerStack.contains(i)) {
                nextCursorPlayerNum = i;
                break;
            }
        }
        cursorPlayerNum = nextCursorPlayerNum;
    }

    public boolean startMatch() {
        if (playerStack.size() < 2) {
            return false;
        }
        gp.initialStrongholdMap();
        StrongholdMap strongholdMap = gp.getStrongholdMap();
        for (int i = 0; i < playerStack.size(); i++) {
            Player p = new Player(i, playerStack.get(i), null, null, strongholdMap);
            strongholdMap.addPlayerWithData(p);
        }
        strongholdMap.startMatch();
        return true;
    }
}
