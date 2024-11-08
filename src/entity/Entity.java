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
    public int damage = 0;
    public int health;
    public int level;
    public int exp;
    public int nextLevelExp;
    public int growl;

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
