package main;

import serialization.User;

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
                gp.ui.titleScreen.titleScreenState = 0;
                handleGamePlayInput(code);
                break;
            case GamePanel.pauseState:
                handlePauseInput(code);
                break;
            case GamePanel.operationState:
                handleOperationInput(code);
                break;
            case GamePanel.characterState:
                handleCharacterState(code);
                break;
            case GamePanel.registerState:
                handleRegisterInput(code);
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
                    case 1 -> titleScreen.titleScreenState = 2;// IMPLEMENTAR SCOREBOARDS
                    case 2 -> {
                        gp.saver.escribirArchivo("res/Game.ser");
                        System.exit(0);
                    } // Salir del juego
                }
            }
        } else if (titleScreen.titleScreenState == 1) {
            if (code == KeyEvent.VK_W) {
                titleScreen.commandNum--;
                gp.playSoundEffect(10);
            } else if (code == KeyEvent.VK_S) {
                titleScreen.commandNum++;
                gp.playSoundEffect(10);
            } else if (code == KeyEvent.VK_ESCAPE) {
                titleScreen.titleScreenState = 0;
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
        } else if(titleScreen.titleScreenState == 2) {
            if (code == KeyEvent.VK_ENTER) {
                titleScreen.titleScreenState = 0;
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
            case KeyEvent.VK_C -> gp.gameState = gp.characterState;
        }
    }

    private void handlePauseInput(int code) {
        UI.PauseScreen pauseScreen = gp.ui.pauseScreen;
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState; // Volver al juego
        }
        if(pauseScreen.pauseScreenState == 0) {
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
                        case 1 -> {
                            pauseScreen.selectedOption = 0;
                            pauseScreen.pauseScreenState = 1;}
                        case 2 -> {
                            gp.player.time = 0;
                            gp.time = 0;
                            gp.gameState = gp.titleState;
                            gp.spawner.stopEnemySpawner();
                        }
                    }
                }
            }
        } else if(pauseScreen.pauseScreenState == 1) {
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

                case KeyEvent.VK_A -> {
                    if(pauseScreen.selectedOption == 0 && gp.backgroundMusic.volumeScale > 0) {
                        gp.backgroundMusic.volumeScale--;
                        gp.backgroundMusic.checkVolume();
                    }
                    if(pauseScreen.selectedOption == 1 && gp.soundEffect.volumeScale > 0) {
                        gp.soundEffect.volumeScale--;
                        gp.soundEffect.checkVolume();
                    }
                }
                case KeyEvent.VK_D -> {
                    if(pauseScreen.selectedOption == 0 && gp.backgroundMusic.volumeScale < 5) {
                        gp.backgroundMusic.volumeScale++;
                        gp.backgroundMusic.checkVolume();
                    }
                    if(pauseScreen.selectedOption == 1 && gp.soundEffect.volumeScale < 5) {
                        gp.soundEffect.volumeScale++;
                        gp.soundEffect.checkVolume();
                    }
                }
                case KeyEvent.VK_ENTER -> {
                    switch (pauseScreen.selectedOption) {
                        case 2 -> {
                            pauseScreen.pauseScreenState = 0;
                            pauseScreen.selectedOption = 1;
                        }
                    }
                }
            }
        }
    }

    private void handleCharacterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
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

    private void handleRegisterInput(int code) {
        UI.RegisterScreen registerScreen = gp.ui.registerScreen;

        if(!registerScreen.done) {
            switch (code) {
                case KeyEvent.VK_A -> {
                    if (registerScreen.selectedOption > 0) {
                        registerScreen.selectedOption--;
                        registerScreen.moveToLetter();

                    }
                }
                case KeyEvent.VK_D -> {
                    if (registerScreen.selectedOption < 2) {
                        registerScreen.selectedOption++;
                        registerScreen.moveToLetter();

                    }
                }
                case KeyEvent.VK_W -> {
                    registerScreen.selectedLetter--;
                    registerScreen.changeLetter();
                }
                case KeyEvent.VK_S -> {
                    registerScreen.selectedLetter++;
                    registerScreen.changeLetter();
                }
                case KeyEvent.VK_ENTER -> {
                    enterPressed = true;
                    gp.ui.registerScreen.selectOption();

                    gp.ui.titleScreen.titleScreenState = 2;
                    gp.gameState = gp.titleState;
                    gp.player.alive = true;
                    gp.player.invincible = false;
                    registerScreen.done = true;
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
