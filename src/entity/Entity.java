package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    public GamePanel gp;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public int worldX, worldY;
    public int speed;
    public int damage;
    public int health;
    public int level;
    public int exp;
    public int nextLevelExp;

    public BufferedImage frame1, frame2, frame3, frame4;

    public BufferedImage setup(String imagePath){
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        return image;
    }

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public String direction;

//    public void draw(Graphics2D g2){
//        BufferedImage image = null;
//        int screenX = worldX - gp.player.worldX + gp.player.screenX;
//        int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
//            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
//            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
//            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
//
//            switch(direction){
//                case "up": case "down": case "right":case "left":
//                    if(spriteNum == 1){
//                        image = frame1;
//                    }
//                    if(spriteNum == 2){
//                        image = frame2;
//                    }
//                    if(spriteNum == 3){
//                        image = frame3;
//                    }
//                    if(spriteNum == 4){
//                        image = frame4;
//                    }
//                    g2.drawImage(image, worldX - gp.player.worldX + gp.player.screenX, worldY - gp.player.worldY + gp.player.screenY,gp.tileSize,gp.tileSize, null);
//                    break;
//            }
//        }
//    }

    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public int invincibleCounter = 0;

    public boolean canRight = true;
    public boolean canLeft = true;
    public boolean canUp = true;
    public boolean canDown = true;

    public Projectile projectile;
    public boolean alive;
    public double dx,dy;
}
