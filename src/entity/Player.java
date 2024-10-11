package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player extends Entity {

    GamePanel gp;
    public KeyHandler kh;

    public final int screenX;
    public final int screenY;


    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.kh = kh;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(worldX + 8,worldY + 8,32,32);

        setDefaultValues();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 90;
        worldY = gp.tileSize * 65;

        speed = 6;
        health = 100;
        direction = "up";

    }

    public void update(){

        if(kh.upPressed && canUp){
            worldY -= speed;
            direction = "up";
        }
        if(kh.downPressed && canDown){
            worldY += speed;
            direction = "down";
        }
        if(kh.leftPressed && canLeft){
            worldX -= speed;
            direction = "left";
        }
        if(kh.rightPressed && canRight){
            worldX += speed;
            direction = "right";
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);

    }

    public void draw(Graphics g2){

        g2.setColor(Color.BLACK);

        g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
    }
}
