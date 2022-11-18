package ui;

import model.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {
    public static final int[][] KEY_MAP = {
            {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D},
            {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT},
            {KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L}
    };
    private GamePanel gp;
    private Drawer drawer;

    public KeyHandler(GamePanel gp, Drawer drawer) {
        this.gp = gp;
        this.drawer = drawer;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        GameState gs = gp.getGameState();
        if (gs == GameState.TITLE_SCREEN) {
            keyPressedHandlerTitleScreen(code);
        } else if (gs == GameState.CHOOSING_PLAYER) {
            keyPressedHandlerChoosingPlayer(code);
        } else if (gs == GameState.MATCH) {
            keyPressedHandlerMatch(code);
        } else if (gs == GameState.PAUSE) {
            keyPressedHandlerPuse(code);
        }
    }

    private void keyPressedHandlerPuse(int code) {
        if (code == KeyEvent.VK_W) {
            drawer.decreaseMenuCursorNum();
        }
        if (code == KeyEvent.VK_S) {
            drawer.increaseMenuCursorNum();
        }
        if (code == KeyEvent.VK_ENTER) {
            int cursorNum = drawer.getMenuCursorNum();
            if (cursorNum == 0) {
                gp.changeGameState(GameState.MATCH);
            } else if (cursorNum == 1) {
                gp.saveStrongholdMap();
                gp.changeGameState(GameState.TITLE_SCREEN);
            }
        }
    }

    private void keyPressedHandlerMatch(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.changeGameState(GameState.PAUSE);
        }
        ArrayList<Player> playerList = gp.getStrongholdMap().getPlayers();
        for (int i = 0; i < playerList.size(); i++) {
            keyPressedHandlerMatchMovePlayer(code, playerList.get(i), KEY_MAP[i]);
        }
    }

    private void keyPressedHandlerMatchMovePlayer(int code, Player player, int[] keyMap) {
        if (code == keyMap[0]) {
            player.move("w");
        }
        if (code == keyMap[1]) {
            player.move("s");
        }
        if (code == keyMap[2]) {
            player.move("a");
        }
        if (code == keyMap[3]) {
            player.move("d");
        }
    }

    private void keyPressedHandlerChoosingPlayer(int code) {
        if (code == KeyEvent.VK_W) {
            drawer.decreaseMenuCursorNum();
        }
        if (code == KeyEvent.VK_S) {
            drawer.increaseMenuCursorNum();
        }
        if (code == KeyEvent.VK_A) {
            drawer.decreaseCursorPlayerNum();
        }
        if (code == KeyEvent.VK_D) {
            drawer.increaseCursorPlayerNum();
        }
        if (code == KeyEvent.VK_ENTER) {
            keyPressedHandlerChoosingPlayerEnterHandler();
        }
    }

    private void keyPressedHandlerChoosingPlayerEnterHandler() {
        int cursorNum = drawer.getMenuCursorNum();
        if (cursorNum == 0) {
            drawer.addPlayer();
        } else if (cursorNum == 1) {
            drawer.removePlayer();
        } else if (cursorNum == 2) {
            if (drawer.startMatch()) {
                gp.changeGameState(GameState.MATCH);
            }
        } else if (cursorNum == 3) {
            gp.changeGameState(GameState.TITLE_SCREEN);
        }
    }

    private void keyPressedHandlerTitleScreen(int code) {
        if (code == KeyEvent.VK_W) {
            drawer.decreaseMenuCursorNum();
        }
        if (code == KeyEvent.VK_S) {
            drawer.increaseMenuCursorNum();
        }
        if (code == KeyEvent.VK_ENTER) {
            int cursorNum = drawer.getMenuCursorNum();
            if (cursorNum == 0) {
                gp.changeGameState(GameState.CHOOSING_PLAYER);
            } else if (cursorNum == 1) {
                gp.loadStrongholdMap();
                gp.changeGameState(GameState.MATCH);
            } else if (cursorNum == 2) {
                System.exit(0);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
}
