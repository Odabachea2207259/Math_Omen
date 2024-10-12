package entity;

import main.GamePanel;
import main.KeyHandler;
import weapons.Bullet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity {
    // Constantes
    public static final double DETECTION_RANGE = 200; // Rango de deteccion en píxeles

    // Caracteristicas del player
    public int maxHealth;

    // Posiciones del Player en Mapa
    public final int screenX;
    public final int screenY;

    // Game Panel
    GamePanel gp;

    // Otros modificadores
    public KeyHandler kh;
    public boolean isHealthBarVisible = false;

    // Disparos
    public ArrayList<Bullet> bullets = new ArrayList<>();
    private long lastShotTime = 0;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.kh = kh;
        this.maxHealth = 100;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(worldX + 8,worldY + 8,32,32);

        setDefaultValues();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 90;
        worldY = gp.tileSize * 65;

        speed = 14;
        health = 100;
        direction = "up";

    }

    public void update() {
        // Variables para el cambio en las coordenadas
        float deltaX = 0;
        float deltaY = 0;

        // Verificar las teclas presionadas y actualizar el movimiento
        if (kh.upPressed && canUp) {
            deltaY = -1;
            direction = "up";
        }
        if (kh.downPressed && canDown) {
            deltaY = 1;
            direction = "down";
        }
        if (kh.leftPressed && canLeft) {
            deltaX = -1;
            direction = "left";
        }
        if (kh.rightPressed && canRight) {
            deltaX = 1;
            direction = "right";
        }

        // Normalizar el vector de movimiento si se está moviendo en diagonal
        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (length != 0) {
            deltaX = (deltaX / length) * speed;
            deltaY = (deltaY / length) * speed;
        }

        // Actualizar las posiciones del jugador
        worldX += (int) deltaX;
        worldY += (int) deltaY;

        // Verificar colisiones y otras acciones
        checkCollisions();

        // Dispara hacia el enemigo más cercano
        shootAtClosestEnemy();

        // Actualiza las balas
        updateBullets();
    }

    public void draw(Graphics2D g2){
        // Dibuja el rango de detección
        g2.setColor(new Color(255, 0, 0, 50)); // Color rojo semi-transparente
        g2.fillOval((int)(screenX - DETECTION_RANGE), (int)(screenY - DETECTION_RANGE),
                (int)(DETECTION_RANGE * 2), (int)(DETECTION_RANGE * 2));


        g2.setColor(Color.BLACK);

        g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);

        // Dibuja todas las balas
        for (Bullet bullet : bullets) {
            bullet.draw(g2);
        }
    }

    private void checkCollisions() {
        collisionOn = false; // Reiniciar el estado de colisión
        gp.cChecker.checkTile(this); // Verificar colisiones con el tile
    }

    private Enemy findClosestEnemy() {
        Enemy closest = null;
        double closestDistance = Double.MAX_VALUE;

        // Imprimir la posición del jugador
        //System.out.println("Player position: " + this.worldX + ", " + this.worldY);

        for (Enemy enemy : gp.enemies) {
            double distance = Math.hypot(this.worldX - enemy.worldX, this.worldY - enemy.worldY);
            //System.out.println("Checking enemy at: " + enemy.worldX + ", " + enemy.worldY);
           // System.out.println("Distance to enemy: " + distance);

            // Solo considera enemigos dentro del rango de detección
            if (distance < DETECTION_RANGE) {
                // Imprimir las coordenadas del enemigo dentro del rango
                //System.out.println("Enemy within detection range at: " + enemy.worldX + ", " + enemy.worldY);

                // Comprobar si es el enemigo más cercano
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closest = enemy;
                    //System.out.println("New closest enemy found at: " + closest.worldX + ", " + closest.worldY);
                }
            }
        }

        if (closest != null) {
            System.out.println("Closest enemy confirmed at: " + closest.worldX + ", " + closest.worldY);
        } else {
            System.out.println("No enemies found within range!");
        }

        return closest;
    }

    private void shootAtClosestEnemy() {
        long currentTime = System.currentTimeMillis();
        Enemy closestEnemy = findClosestEnemy();

        // Tiempo en milisegundos
        long shotCooldown = 500;

        if (closestEnemy != null && currentTime - lastShotTime >= shotCooldown) {
            double distance = Math.hypot(this.worldX - closestEnemy.worldX, this.worldY - closestEnemy.worldY);
            if (distance <= DETECTION_RANGE) {
                // Verifica si el enemigo está dentro del rango
                Bullet bullet = getBullet(closestEnemy);
                bullets.add(bullet);
                lastShotTime = currentTime;
            }
        }
    }

    private Bullet getBullet(Enemy closestEnemy) {
        int bulletStartX = this.worldX + (gp.tileSize / 2) - (Bullet.BULLET_WIDTH / 2);
        int bulletStartY = this.worldY + (gp.tileSize / 2) - (Bullet.BULLET_HEIGHT / 2);

        // Usa las coordenadas del enemigo como destino
        int targetX = closestEnemy.worldX;
        int targetY = closestEnemy.worldY;

        // Crea la bala y pasa las coordenadas del enemigo como destino
        return new Bullet(gp, bulletStartX, bulletStartY, targetX, targetY);
    }

    private void updateBullets() {
        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update();

            // Remover balas que han alcanzado su rango o están fuera de los límites
            if (bullet.isOutOfBounds() || bullet.hasReachedMaxDistance()) {
                bulletIterator.remove();
            }
        }
    }

}
