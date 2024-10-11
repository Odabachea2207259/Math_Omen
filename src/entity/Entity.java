package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage frame1, frame2, frame3, frame4;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public String direction;

    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    public boolean canRight = true;
    public boolean canLeft = true;
    public boolean canUp = true;
    public boolean canDown = true;
}
