package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Enemy extends Entity{

    private int speed = 2; // Velocidad de movimiento del enemigo
    private final GamePanel gamePanel;
    private boolean playerCollision = false;
    private boolean hasCollided = false;


    public Enemy(GamePanel gamePanel, int startX, int startY) {
        Random randomNumbers = new Random();
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
                speed = 2;
                damage = 10;
                break;
            case 3:
                speed = 4;
                damage = 8;
                break;
            case 4:
                speed = 2;
                damage = 15;
                break;
            case 5:
                speed = 5;
                damage = 6;
                break;
        }
    }

    public void getEnemyImage(int numEnemy){
        try{
            if(numEnemy == 1){
                frame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0001.png")));
                frame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0002.png")));
                frame3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0003.png")));
                frame4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0004.png")));
            }
            else if(numEnemy == 2){
                frame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Menos-0001.png")));
                frame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Menos-0002.png")));
                frame3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Menos-0003.png")));
                frame4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Menos-0004.png")));
            }
            else if(numEnemy == 3){
                frame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Multiplicacion-0001.png")));
                frame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Multiplicacion-0002.png")));
                frame3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Multiplicacion-0003.png")));
                frame4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Multiplicacion-0004.png")));
            }
            else if(numEnemy == 4){
                frame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Division-0001.png")));
                frame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Division-0002.png")));
                frame3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Division-0003.png")));
                frame4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Division-0004.png")));
            }
            else if(numEnemy == 5){
                frame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Exponente-0001.png")));
                frame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Exponente-0002.png")));
                frame3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Exponente-0003.png")));
                frame4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Exponente-0004.png")));
            }
            else{
                frame1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0001.png")));
                frame2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0002.png")));
                frame3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0003.png")));
                frame4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Enemies/Mas-0004.png")));
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // Actualiza la posición del enemigo (por ejemplo, movimiento hacia el jugador)
    public void update(int playerX, int playerY) {

        float deltaX = 0;
        float deltaY = 0;

        if (worldX < playerX && canRight) { deltaX = 1; direction = "right"; }
        if (worldX > playerX && canLeft) { deltaX = -1; direction = "left"; }
        if (worldY < playerY && canDown) { deltaY = 1; direction = "down"; }
        if (worldY > playerY && canUp) { deltaY = -1; direction = "up"; }

        // Normalizar el vector de movimiento si se está moviendo en diagonal
        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (length != 0) {
            deltaX = (deltaX / length) * speed;
            deltaY = (deltaY / length) * speed;
        }

        // Actualizar las posiciones del enemigo
        worldX += (int) deltaX;
        worldY += (int) deltaY;

        // Actualizar el area solida del enemigo
        solidArea.setBounds(worldX + 8, worldY + 8, 32, 32);

        // Comprobar colisiones
        checkCollisionWithOtherEnemies();
        checkCollisionWithPlayer(playerX, playerY);

        // Verificar colisión con el jugador
        if (playerCollision && !hasCollided) {
            gamePanel.player.health -= damage;
            gamePanel.player.isHealthBarVisible = true;
            System.out.println("Player health: " + gamePanel.player.health);
            hasCollided = true;
        }

        // Actualizar la animación del sprite
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum++;
            if (spriteNum > 4) {
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
    /*public int getX() { return worldX; }
    public int getY() { return worldY; }
    */

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
