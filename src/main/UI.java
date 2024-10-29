package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font arial_100,arial_40,aladin_100,aladin_40;
    public boolean messageOn = false;
    public String message = "";
    int textLength = 0;
    int x,y;
    Graphics2D g2;
    public int commandNum = 0;
    public int titleScreenState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_100 = new Font("Arial", Font.BOLD, 100);
        arial_40 = new Font("Arial", Font.PLAIN, 40);

        aladin_100 = new Font("Aladin", Font.BOLD, 100);
        aladin_40 = new Font("Aladin", Font.BOLD, 40);
    }

    public void showMessage(String text){
        messageOn = true;
        message = text;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(aladin_100);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        if(gp.gameState == gp.playState){
            g2.setFont(aladin_100);
            g2.setColor(Color.RED);

            if(messageOn){
                textLength = (int)g2.getFontMetrics().getStringBounds(message,g2).getWidth();

                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 - (gp.tileSize * 3);


                g2.drawString(message, x, y);
            }
        }

        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
    }

    public void drawTitleScreen(){

        if(titleScreenState == 0){
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            //TITLE
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "Math Omen";
            int x  = getXforCenteredText(text);
            int y = gp.tileSize*3;

            g2.setColor(Color.gray);
            g2.drawString(text,x + 5,y + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "SCOREBOARD";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">",x-gp.tileSize,y);
            }
        }
        if(titleScreenState == 1){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your character";
            int x = getXforCenteredText(text);
            y = gp.tileSize*3;

            g2.drawString(text, x, y);

            text = "Boy";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">",x-gp.tileSize,y);
            }

            text = "Girl";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }
        }

    }

    public void drawPauseScreen(){

        String text = "PAUSE";

        int x = getXforCenteredText(text);
        int y = getYforCenteredText(text);

        g2.drawString(text,x,y);
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        x = gp.screenWidth/2 - length/2;

        return x;
    }

    public int getYforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getHeight();
        y = gp.screenHeight/2 - length/2;

        return y;
    }
}
