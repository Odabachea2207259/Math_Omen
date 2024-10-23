package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed,enterPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState){
            if(gp.ui.titleScreenState == 0){
                if(code == KeyEvent.VK_W){
                    gp.ui.commandNum--;
                }
                if(code == KeyEvent.VK_S){
                    gp.ui.commandNum++;
                }
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
                else if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }

                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        gp.ui.titleScreenState = 1;
                    }
                    if(gp.ui.commandNum == 1){
                        //IMPLEMENTAR SCOREBOARDS
                    }
                    if(gp.ui.commandNum == 2){
                        System.exit(0);
                    }
                }
            }

            else if(gp.ui.titleScreenState == 1){
                if(code == KeyEvent.VK_W){
                    gp.ui.commandNum--;
                }
                if(code == KeyEvent.VK_S){
                    gp.ui.commandNum++;
                }
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
                else if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }

                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        //JUGARDOR BOY
                        gp.gameState = gp.playState;
                    }
                    if(gp.ui.commandNum == 1){
                        //JUGADOR GIRL
                        gp.gameState = gp.playState;
                    }
                    if(gp.ui.commandNum == 2){
                        gp.ui.titleScreenState = 0;
                    }
                }
            }
        }

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }
            else if(gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }


}
