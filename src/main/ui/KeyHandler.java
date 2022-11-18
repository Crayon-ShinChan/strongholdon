package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gp;
    private Drawer drawer;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

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
            if (code == KeyEvent.VK_W) {
                drawer.decreaseMenuCursorNum();
            }
            if (code == KeyEvent.VK_S) {
                System.out.println("downPressed");
                drawer.increaseMenuCursorNum();
            }
        }
//        if (code == KeyEvent.VK_W) {
//            upPressed = true;
//        }
//        if (code == KeyEvent.VK_S) {
//            downPressed = true;
//        }
//        if (code == KeyEvent.VK_A) {
//            leftPressed = true;
//        }
//        if (code == KeyEvent.VK_D) {
//            rightPressed = true;
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

//        if (code == KeyEvent.VK_W) {
//            upPressed = false;
//        }
//        if (code == KeyEvent.VK_S) {
//            downPressed = false;
//        }
//        if (code == KeyEvent.VK_A) {
//            leftPressed = false;
//        }
//        if (code == KeyEvent.VK_D) {
//            rightPressed = false;
//        }
    }
}
