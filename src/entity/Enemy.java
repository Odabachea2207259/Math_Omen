package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity{

    private int speed = 2; // Velocidad de movimiento del enemigo
    private GamePanel gamePanel;
    private Random randomNumbers = new Random();
    private boolean playerCollision = false;
    private boolean hasCollided = false;


    public Enemy(GamePanel gamePanel, int startX, int startY) {
        int numEnemy = randomNumbers.nextInt(5) + 1;

        this.gamePanel = gamePanel;
        this.worldX = startX;
        this.worldY = startY;


        solidArea = new Rectangle(worldX + 8,worldY + 8,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getEnemyImage(numEnemy);

        switch(numEnemy) {
            case 1:
                speed = 3;
                damage = 1;
                break;
            case 2:
                speed = 1;
                damage = 10;
                break;
            case 3:
                speed = 4;
                damage = 20;
                break;
            case 4:
                speed = 2;
                damage = 20;
                break;
            case 5:
                speed = 5;
                damage = 10;
                break;
        }
    }

    public void getEnemyImage(int numEnemy){
        try{
            if(numEnemy == 1){
                frame1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0001.png"));
                frame2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0002.png"));
                frame3 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0003.png"));
                frame4 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0004.png"));
            }
            else if(numEnemy == 2){
                frame1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Menos-0001.png"));
                frame2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Menos-0002.png"));
                frame3 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Menos-0003.png"));
                frame4 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Menos-0004.png"));
            }
            else if(numEnemy == 3){
                frame1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Multiplicacion-0001.png"));
                frame2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Multiplicacion-0002.png"));
                frame3 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Multiplicacion-0003.png"));
                frame4 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Multiplicacion-0004.png"));
            }
            else if(numEnemy == 4){
                frame1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Division-0001.png"));
                frame2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Division-0002.png"));
                frame3 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Division-0003.png"));
                frame4 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Division-0004.png"));
            }
            else if(numEnemy == 5){
                frame1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Exponente-0001.png"));
                frame2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Exponente-0002.png"));
                frame3 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Exponente-0003.png"));
                frame4 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Exponente-0004.png"));
            }
            else{
                frame1 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0001.png"));
                frame2 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0002.png"));
                frame3 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0003.png"));
                frame4 = ImageIO.read(getClass().getResourceAsStream("/Enemies/Mas-0004.png"));
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Actualiza la posición del enemigo (por ejemplo, movimiento hacia el jugador)
    public void update(int playerX, int playerY) {


        if (worldX < playerX && canRight){ worldX += speed; direction = "right";}
        if (worldX > playerX && canLeft){worldX -= speed; direction = "left";}
        if (worldY < playerY && canDown){ worldY += speed; direction = "down";}
        if (worldY > playerY && canUp){ worldY -= speed; direction = "up";}

        solidArea.setBounds(worldX + 8, worldY + 8, 32, 32);

        checkCollisionWithOtherEnemies();
        checkCollisionWithPlayer(playerX,playerY);

        if(playerCollision && !hasCollided){
            gamePanel.player.health -= damage;
            System.out.println("Player health: " + gamePanel.player.health);
            hasCollided = true;
        }

        spriteCounter++;
        if(spriteCounter > 10){
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

        g2.drawImage(image, worldX - player.worldX + player.screenX, worldY - player.worldY + player.screenY,gamePanel.tileSize,gamePanel.tileSize, null);
    }

    // Getters y setters si son necesarios
    public int getX() { return worldX; }
    public int getY() { return worldY; }

    public void checkCollisionWithOtherEnemies() {
        for (Enemy otherEnemy : gamePanel.enemies) {
            if (otherEnemy != this) {
                // Detecta si los rectángulos están intersectando
                if (this.solidArea.intersects(otherEnemy.solidArea)) {
                    // Calcula las diferencias de posición entre los dos enemigos
                    int dx = this.solidArea.x - otherEnemy.solidArea.x;
                    int dy = this.solidArea.y - otherEnemy.solidArea.y;

                    // Verifica la colisión por los lados
                    if (Math.abs(dx) > Math.abs(dy)) {
                        if (dx > 0) {
                            canRight = false;
                            //System.out.println("Colisión en el lado derecho de " + otherEnemy);
                            this.worldX += speed;  // Mueve al enemigo hacia la derecha
                        } else {
                            canLeft = false;
                            //System.out.println("Colisión en el lado izquierdo de " + otherEnemy);
                            this.worldX -= speed;  // Mueve al enemigo hacia la izquierda
                        }
                    } else {
                        if (dy > 0) {
                            canDown = false;
                            //System.out.println("Colisión en la parte inferior de " + otherEnemy);
                            this.worldY += speed;  // Mueve al enemigo hacia abajo
                        } else {
                            canUp = false;
                            //System.out.println("Colisión en la parte superior de " + otherEnemy);
                            this.worldY -= speed;  // Mueve al enemigo hacia arriba
                        }
                    }
                }
                else{
                    canRight = true;
                    canLeft = true;
                    canUp = true;
                    canDown = true;
                }
            }
        }
    }

    public void checkCollisionWithPlayer(int playerX, int playerY) {
        for (Enemy enemies : gamePanel.enemies) {
                if (enemies.solidArea.intersects(playerX,playerY, gamePanel.tileSize,gamePanel.tileSize)) {
                    enemies.playerCollision = true;
                }
                else{
                    enemies.playerCollision = false;
                    enemies.hasCollided = false;
                }
        }
    }
}
