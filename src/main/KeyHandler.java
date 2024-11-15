package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, characterPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (gp.gameState) {
            case GamePanel.titleState:
                handleTitleScreenInput(code);
                break;
            case GamePanel.playState:
                handleGamePlayInput(code);
                break;
            case GamePanel.pauseState:
                handlePauseInput(code);
                break;
            case GamePanel.operationState:
                handleOperationInput(code);
                break;
            default:
                break;
        }
    }

    public void handleTitleScreenInput(int code) {
        UI.TitleScreen titleScreen = gp.ui.titleScreen;

        if (titleScreen.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                titleScreen.commandNum--;
                gp.playSoundEffect(10);
            } else if (code == KeyEvent.VK_S) {
                titleScreen.commandNum++;
                gp.playSoundEffect(10);
            }

            // Limitar los valores de commandNum
            if (titleScreen.commandNum > 2) {
                titleScreen.commandNum = 0;
            } else if (titleScreen.commandNum < 0) {
                titleScreen.commandNum = 2;
            }

            if (code == KeyEvent.VK_ENTER) {
                gp.playSoundEffect(11);

                switch (titleScreen.commandNum) {
                    case 0 -> titleScreen.titleScreenState = 1; // Ir a selección de personaje
                    case 1 -> {} // IMPLEMENTAR SCOREBOARDS
                    case 2 -> System.exit(0); // Salir del juego
                }
            }
        } else if (titleScreen.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                titleScreen.commandNum--;
                gp.playSoundEffect(10);
            } else if (code == KeyEvent.VK_S) {
                titleScreen.commandNum++;
                gp.playSoundEffect(10);
            }

            // Limitar los valores de commandNum en selección de personaje
            if (titleScreen.commandNum > 1) {
                titleScreen.commandNum = 0;
            } else if (titleScreen.commandNum < 0) {
                titleScreen.commandNum = 1;
            }

            if (code == KeyEvent.VK_ENTER) {
                gp.playSoundEffect(11);

                if (titleScreen.commandNum == 0) {
                    // Selección de personaje Boy
                    characterPressed = false;
                    gp.gameState = gp.playState;
                } else if (titleScreen.commandNum == 1) {
                    // Selección de personaje Girl
                    characterPressed = true;
                    gp.gameState = gp.playState;
                } else if (titleScreen.commandNum == 2) {
                    titleScreen.titleScreenState = 0; // Regresar al menú principal
                }
            }
        }
    }

    private void handleGamePlayInput(int code) {
        switch (code) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_ESCAPE -> gp.gameState = gp.pauseState;
            case KeyEvent.VK_ENTER -> enterPressed = true;
        }
    }

    private void handlePauseInput(int code) {
        UI.PauseScreen pauseScreen = gp.ui.pauseScreen;
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState; // Volver al juego
        }
            switch (code) {
                case KeyEvent.VK_W -> {
                    if (pauseScreen.selectedOption > 0) {
                        pauseScreen.selectedOption--;
                        gp.playSoundEffect(10);
                    }
                }
                case KeyEvent.VK_S -> {
                    if (pauseScreen.selectedOption < 2) {
                        pauseScreen.selectedOption++;
                        gp.playSoundEffect(10);
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    switch (pauseScreen.selectedOption) {
                        case 0 -> gp.gameState = gp.playState;
                        case 2 -> {
                            gp.player.time = 0;
                            gp.ui.titleScreen.titleScreenState = 0;
                            gp.gameState = gp.titleState;
                        }
                    }
                }
            }

    }

    private void handleOperationInput(int code) {
        UI.OperationScreen operationScreen = gp.ui.operationScreen;

        if(operationScreen.resultDisplayed == false) {
            switch (code) {
                case KeyEvent.VK_W -> {
                    if (operationScreen.selectedOption > 0) {
                        operationScreen.selectedOption--;
                        gp.playSoundEffect(10);
                    }
                }
                case KeyEvent.VK_S -> {
                    if (operationScreen.selectedOption < 2) {
                        operationScreen.selectedOption++;
                        gp.playSoundEffect(10);
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    enterPressed = true;
                    gp.ui.operationScreen.selectOption();
                    //processAnswer(operationScreen.selectedOption); // Procesar la respuesta seleccionada
                }
            }
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
