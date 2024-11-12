package main;

import java.awt.*;

public class UI {
    public GamePanel gp;
    private Font arial_100, arial_40;
    private Graphics2D g2;

    public TitleScreen titleScreen;
    public PauseScreen pauseScreen;
    public OperationScreen operationScreen;
    public MessageDisplay messageDisplay;

    public boolean deadPlayer = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.arial_100 = new Font("Arial", Font.BOLD, 100);
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);

        // Inicializar pantallas anidadas
        this.titleScreen = new TitleScreen();
        this.pauseScreen = new PauseScreen();
        this.operationScreen = new OperationScreen();
        this.messageDisplay = new MessageDisplay();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        // Simplified state machine
        switch (gp.gameState) {
            case GamePanel.titleState:
                titleScreen.draw();
                break;
            case GamePanel.playState:
                messageDisplay.draw();
                break;
            case GamePanel.pauseState:
                pauseScreen.draw();
                break;
            case GamePanel.operationState:
                operationScreen.draw();
                break;
            default:
                break;
        }
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getYforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        return gp.screenHeight / 2 - length / 2;
    }

    // Clase anidada para la pantalla de título
    public class TitleScreen {
        public int commandNum = 0;
        public int titleScreenState = 0;

        public void draw() {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            if (titleScreenState == 0) {
                drawMainMenu();
            } else if (titleScreenState == 1) {
                drawCharacterSelection();
            }
        }

        public void drawMainMenu() {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            String title = "Math Omen";
            int x = getXforCenteredText(title);
            int y = gp.tileSize * 3;

            g2.setColor(Color.gray);
            g2.drawString(title, x + 5, y + 5);
            g2.setColor(Color.WHITE);
            g2.drawString(title, x, y);

            drawMenuOption("NEW GAME", y + gp.tileSize * 4, 0);
            drawMenuOption("SCOREBOARD", y + gp.tileSize * 6, 1);
            drawMenuOption("QUIT", y + gp.tileSize * 8, 2);
        }

        public void drawCharacterSelection() {
            g2.setFont(g2.getFont().deriveFont(42F));
            g2.setColor(Color.white);

            String title = "Select your character";
            int x = getXforCenteredText(title);
            int y = gp.tileSize * 3;
            g2.drawString(title, x, y);

            drawMenuOption("Boy", y + gp.tileSize * 3, 0);
            drawMenuOption("Girl", y + gp.tileSize * 6, 1);
        }

        public void drawMenuOption(String text, int y, int commandIndex) {
            int x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == commandIndex) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    // Clase anidada para la pantalla de pausa
    public class PauseScreen {
        public void draw() {
            g2.setFont(arial_100);
            g2.setColor(Color.WHITE);
            String text = "PAUSE";
            int x = getXforCenteredText(text);
            int y = getYforCenteredText(text);
            g2.drawString(text, x, y);
        }
    }

    public class OperationScreen {
        public void draw() {
            g2.setFont(arial_100);
            g2.setColor(Color.WHITE);
            String text = "OPERATION HERE!";
            int x = getXforCenteredText(text);
            int y = getYforCenteredText(text);
            g2.drawString(text, x, y);
        }
    }

    // Clase anidada para mostrar mensajes temporales
    public class MessageDisplay {
        private boolean messageOn = true;
        private String message = "GAME OVER!";

        public void draw() {
            if (messageOn && deadPlayer) {
                g2.setFont(arial_100);
                g2.setColor(Color.RED);
                int textLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth();
                int x = gp.screenWidth / 2 - textLength / 2;
                int y = gp.screenHeight / 2 - (gp.tileSize * 3);
                g2.drawString(message, x, y);
            }
        }
    }
}