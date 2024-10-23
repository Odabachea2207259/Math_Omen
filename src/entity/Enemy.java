package entity;

import entity.Enemies.*;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public abstract class Enemy extends Entity{

    protected Random randomNumbers = new Random();
    public boolean playerCollision = false;

    public Enemy(GamePanel gamePanel, int startX, int startY) {

        super(gamePanel);
        this.worldX = startX;
        this.worldY = startY;

        solidArea = new Rectangle(worldX + 8,worldY + 8,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    protected abstract void loadEnemyImages();


    // Actualiza la posición del enemigo (por ejemplo, movimiento hacia el jugador)
    public void update(int playerX, int playerY) {

        if (worldX < playerX && canRight){ worldX += speed; direction = "right";}
        if (worldX > playerX && canLeft){worldX -= speed; direction = "left";}
        if (worldY < playerY && canDown){ worldY += speed; direction = "down";}
        if (worldY > playerY && canUp){ worldY -= speed; direction = "up";}

        solidArea.setBounds(worldX + 8, worldY + 8, 32, 32);

        gp.cChecker.checkCollisionWithOtherEnemies(this);
        gp.cChecker.checkCollisionWithPlayer(playerX,playerY);

        spriteCounter++;
        if(spriteCounter > 8){
            spriteNum++;
            if(spriteNum > 4){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2, Player player) {
        BufferedImage image = null;

        if(spriteNum == 1){
            image = frame1;
        }
        if(spriteNum == 2){
            image = frame2;
        }
        if(spriteNum == 3){
            image = frame3;
        }
        if(spriteNum == 4){
            image = frame4;
        }

        g2.drawImage(image, worldX - player.worldX + player.screenX, worldY - player.worldY + player.screenY,gp.tileSize,gp.tileSize, null);
    }
}
