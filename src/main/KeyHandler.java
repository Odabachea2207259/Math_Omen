package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            handleTitleScreenInput(code);
        } else if (gp.gameState == gp.playState) {
            handleGamePlayInput(code);
        } else if (gp.gameState == gp.pauseState) {
            handlePauseInput(code);
        }
    }

    public void handleTitleScreenInput(int code) {
        UI.TitleScreen titleScreen = gp.ui.titleScreen;

        if (titleScreen.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                titleScreen.commandNum--;
            } else if (code == KeyEvent.VK_S) {
                titleScreen.commandNum++;
            }

            // Limitar los valores de commandNum
            if (titleScreen.commandNum > 2) {
                titleScreen.commandNum = 0;
            } else if (titleScreen.commandNum < 0) {
                titleScreen.commandNum = 2;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (titleScreen.commandNum) {
                    case 0 -> titleScreen.titleScreenState = 1; // Ir a selección de personaje
                    case 1 -> {} // IMPLEMENTAR SCOREBOARDS
                    case 2 -> System.exit(0); // Salir del juego
                }
            }
        } else if (titleScreen.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                titleScreen.commandNum--;
            } else if (code == KeyEvent.VK_S) {
                titleScreen.commandNum++;
            }

            // Limitar los valores de commandNum en selección de personaje
            if (titleScreen.commandNum > 1) {
                titleScreen.commandNum = 0;
            } else if (titleScreen.commandNum < 0) {
                titleScreen.commandNum = 1;
            }

            if (code == KeyEvent.VK_ENTER) {
                if (titleScreen.commandNum == 0) {
                    // Selección de personaje Boy
                    gp.gameState = gp.playState;
                } else if (titleScreen.commandNum == 1) {
                    // Selección de personaje Girl
                    gp.gameState = gp.playState;
                } else if (titleScreen.commandNum == 2) {
                    titleScreen.titleScreenState = 0; // Regresar al menú principal
                }
            }
        }
    }

    private void handleGamePlayInput(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        } else if (code == KeyEvent.VK_S) {
            downPressed = true;
        } else if (code == KeyEvent.VK_A) {
            leftPressed = true;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = true;
        } else if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        } else if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    private void handlePauseInput(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState; // Volver al juego
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        } else if (code == KeyEvent.VK_S) {
            downPressed = false;
        } else if (code == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
