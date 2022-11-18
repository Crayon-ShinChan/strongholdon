package ui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        new StrongholdonAppConsoleBase();
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // causes this window to be sized to fit the preferred size
        // and layouts of its subcomponents. i.e. GamePanel
        window.pack();

        // not specify the location of the window
        // i.e. the window will be displayed at the center of the screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
