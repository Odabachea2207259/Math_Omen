package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UI;
import objects.OBJ_Bullet;
import serialization.User;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Scanner;


public class Player extends Entity {
    // Constantes
    public static final double DETECTION_RANGE = 200; // Rango de deteccion en píxeles

    public KeyHandler kh;

    public int maxHealth;
    public final int screenX;
    public final int screenY;

    public static final double DETECTION_RANGE = 300;
    public Enemy closest = null;
    public double dx, dy, angulo = 0;
    public int bulletX, bulletY;
    public int attack;

    public int shootCounter = 0;
    BufferedImage image = null;
    public boolean seleccion = false;
    public int time = 0;

    public int expTotal = 0;

    public void setExp(int exp) {
        this.exp += exp;
        expTotal += exp;
    }

    public Player(GamePanel gp, KeyHandler kh) {
        super(gp);
        this.kh = kh;
        this.maxHealth = 100;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(worldX + 8, worldY + 8, 32, 32);

        setDefaultValues();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 90;
        worldY = gp.tileSize * 65;

        alive = true;
        speed = 16;
        health = 1;
        maxHealth = health;

        direction = "up";
        exp = 0;
        expTotal = 0;
        level = 1;
        nextLevelExp = 25;
        seleccion = kh.characterPressed;

        projectile = new OBJ_Bullet(gp);
        attack = getAttack();
    }

    public int getAttack() {
        return this.damage + projectile.damage;
    }

    public void update() {
        if (time == 0) {
            seleccion = kh.characterPressed;
            loadPlayerImages();
            time++;
        }

        float deltaX = 0;
        float deltaY = 0;

        if (kh.upPressed && canUp) {
            deltaY = -1;
            //worldY -= speed;

            direction = "up";
        }
        if (kh.downPressed && canDown) {
            deltaY = 1;
            //worldY += speed;

            direction = "down";
        }
        if (kh.leftPressed && canLeft) {
            deltaX = -1;
            //worldX -= speed;

            direction = "left";
        }
        if (kh.rightPressed && canRight) {
            deltaX = 1;
            //worldX += speed;
            direction = "right";
        }

        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (length != 0) {
            deltaX = (deltaX * speed / length);
            deltaY = (deltaY * speed / length);
        }

        worldX += (int) deltaX;
        worldY += (int) deltaY;

        collisionOn = false;
        gp.cChecker.checkTile(this);
        checkClosestEnemy();

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        shootCounter++;
        if (closest != null) {
            if (shootCounter > 60) {
                //projectile.set(worldX,worldY,);
                shoot();
                shootCounter = 0;
            }
        }

        spriteCounter++;
        if (spriteCounter > 8) {
            spriteNum++;
            if (spriteNum > 4) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        gp.cChecker.checkProjectile(projectile);
        checkLevelUp();

        if (this.health <= 0) {
            gp.spawner.stopEnemySpawner();
            //gp.ui.deadPlayer = true;
            health = 0;
            alive = false;
        }

        // Dispara hacia el enemigo más cercano
        shootAtClosestEnemy();

        // Actualiza las balas
        updateBullets();
    }

    // Nuevo metodo para manejar la barra de experiencia
    public void drawExperienceBar(Graphics2D g2) {
        // Fondo de la barra
        g2.setColor(Color.BLACK);
        g2.fillRect(10, 4, gp.screenWidth - 18, 38); // x, y, ancho, alto

        // Dibuja la barra de experiencia (azul)
        if (exp != 0) {
            // Calcula el porcentaje
            int anchoBarra = (int) ((float) exp / nextLevelExp * (gp.screenWidth - 18));
            g2.setColor(Color.getHSBColor(200f / 360f, 0.7f, 0.90f));
            g2.fillRect(10, 4, anchoBarra, 38); // x, y, ancho variable, alto
        }

        // Borde de la barra
        g2.setColor(Color.YELLOW); // Color del borde
        g2.drawRect(8, 4, gp.screenWidth - 16, 38); // x, y, ancho, alto (tamaño del borde)

        // Opcional: Añadir texto para mostrar la experiencia
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.setColor(Color.WHITE);
        String resultMessage = "Exp: " + exp + "/" + nextLevelExp;
        int messageX = gp.ui.getXforCenteredText(resultMessage);
        g2.drawString(resultMessage, messageX, 30);
    }


    public void drawScore(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        String total = expTotal + "000";
        int messageX = gp.ui.getXforCenteredText(total);
        g2.setColor(Color.BLACK);
        g2.drawString(total, messageX + 4, gp.screenHeight - 36);
        g2.setColor(Color.WHITE);
        g2.drawString(total, messageX, gp.screenHeight - 40);
    }

    public void draw(Graphics2D g2) {
        // Dibujo de fondo negro de la salud
        g2.setColor(Color.BLACK);
        g2.fillRect(screenX - 28, screenY - 12, 104, 10);

        if (spriteNum == 1) {
            image = frame1;
        }
        if (spriteNum == 2) {
            image = frame2;
        }
        if (spriteNum == 3) {
            image = frame3;
        }
        if (spriteNum == 4) {
            image = frame4;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        if (direction.equals("right")) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else if (direction.equals("left")) {
            g2.drawImage(image, screenX + gp.tileSize, screenY, -gp.tileSize, gp.tileSize, null);
        } else {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Dibuja la barra de experiencia
        //drawExperienceBar(g2);

        // Calculo para la salud
        int healthWidth = (int) ((health / (float) maxHealth) * 100);
        g2.setColor(Color.RED);
        g2.fillRect(screenX - 26, screenY - 10, 100, 6);
        g2.setColor(Color.GREEN);
        g2.fillRect(screenX - 26, screenY - 10, healthWidth, 6);

        AffineTransform old = g2.getTransform();

        if (closest != null) {
            g2.setColor(Color.BLACK);

            int playerCenterX = screenX + gp.tileSize / 2;
            int playerCenterY = screenY + gp.tileSize / 2;

            int indicatorX = playerCenterX + (int) (50 * Math.cos(angulo));
            int indicatorY = playerCenterY + (int) (50 * Math.sin(angulo));

            g2.translate(indicatorX, indicatorY);
            g2.rotate(angulo);
            g2.fillRect(-5, -5, 10, 10);
            g2.rotate(-angulo);

            g2.setTransform(old);
        }
    }


    public void checkClosestEnemy() {
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : gp.enemies) {
            double distance = Math.hypot(this.worldX - enemy.worldX,this.worldY - enemy.worldY);
            //System.out.println("Distance: " + distance + " Range " + DETECTION_RANGE);
            if(distance < DETECTION_RANGE) {
                if (distance < closestDistance) {
                    if (enemy != closest) {
                        closestDistance = distance;
                        closest = enemy;
                        dx = closest.worldX - this.worldX;
                        dy = closest.worldY - this.worldY;
                        angulo = Math.atan2(dy, dx);

                        bulletX = (int) (this.worldX + 30 * Math.cos(angulo));
                        bulletY = (int) (this.worldY + 30 * Math.sin(angulo));
                        if (!closest.alive) {
                            closest = null;
                        }
                    }
                }
            }
            if(distance > DETECTION_RANGE) {
                bulletX = 0;
                bulletY = 0;
                if(closest != null){
                    distance = Math.hypot(this.worldX - closest.worldX, this.worldY - closest.worldY);
                    if (!closest.alive || distance > DETECTION_RANGE) {
                        closest = null;
                    }
                }
            }
        }
    }

    public void shoot() {
        dx = closest.worldX - this.worldX;
        dy = closest.worldY - this.worldY;
        angulo = Math.atan2(dy, dx);

        projectile.set(worldX ,worldY,dx,dy,true,angulo);
        gp.projectileList.add(projectile);
    }


    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp + 25;
            exp = 0;
            health += 25;
            maxHealth = health;
            damage++;

            gp.ui.setFoo(0);

            gp.gameState = GamePanel.operationState;
        }
    }

    protected void loadPlayerImages() {

        if(!seleccion) {
            frame1 = setup("/player/nino-0001");
            frame2 = setup("/player/nino-0002");
            frame3 = setup("/player/nino-0003");
            frame4 = setup("/player/nino-0004");
        }
        else{
            frame1 = setup("/player/nina-0001");
            frame2 = setup("/player/nina-0002");
            frame3 = setup("/player/nina-0003");
            frame4 = setup("/player/nina-0004");
        }
    }

    @Override
    public String toString() {
        return "Health: " + health + "\tMax Healt: " + maxHealth + "\tDamage: " + damage;

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
