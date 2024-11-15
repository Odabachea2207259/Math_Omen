package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UI;
import objects.OBJ_Bullet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public KeyHandler kh;

    int maxHealth;
    public final int screenX;
    public final int screenY;

    public static final double DETECTION_RANGE = 200;
    public Enemy closest = null;
    public double dx,dy,angulo = 0;
    public int bulletX, bulletY;

    public int shootCounter = 0;
    BufferedImage image = null;
    public boolean seleccion = false;
    public int time = 0;


    public Player(GamePanel gp, KeyHandler kh) {
        super(gp);
        this.kh = kh;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(worldX + 8,worldY + 8,32,32);

        setDefaultValues();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 90;
        worldY = gp.tileSize * 65;

        alive = true;
        speed = 16;
        health = 100;
        maxHealth = health;
        direction = "up";
        exp = 0;
        level = 1;
        nextLevelExp = 25;
        seleccion = kh.characterPressed;

        projectile = new OBJ_Bullet(gp);
    }

    public void update(){
        if(time == 0) {
            seleccion = kh.characterPressed;
            loadPlayerImages();
            time++;
        }

        float deltaX = 0;
        float deltaY = 0;

        if(kh.upPressed && canUp){
            deltaY = -1;
            //worldY -= speed;
            direction = "up";
        }
        if(kh.downPressed && canDown){
            deltaY = 1;
            //worldY += speed;
            direction = "down";
        }
        if(kh.leftPressed && canLeft){
            deltaX = -1;
            //worldX -= speed;
            direction = "left";
        }
        if(kh.rightPressed && canRight){
            deltaX = 1;
            //worldX += speed;
            direction = "right";
        }

        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if(length != 0){
            deltaX = (deltaX * speed/ length);
            deltaY = (deltaY * speed/ length);
        }

        worldX += (int)deltaX;
        worldY += (int)deltaY;

        collisionOn = false;
        gp.cChecker.checkTile(this);
        checkClosestEnemy();

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        shootCounter++;
        if(closest != null) {
            if (shootCounter > 60) {
                //projectile.set(worldX,worldY,);
                shoot();
                shootCounter = 0;
            }
        }

        spriteCounter++;
        if(spriteCounter > 8){
            spriteNum++;
            if(spriteNum > 4){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        gp.cChecker.checkProjectile(projectile);
        checkLevelUp();

        if(this.health <= 0){
            gp.ui.deadPlayer = true;
            health = 0;
            alive = false;
        }

    }

    //@Override
    public void draw(Graphics2D g2){

        g2.setColor(Color.BLACK);
        g2.fillRect(screenX - 28, screenY - 12, 104, 10);

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

        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        if(direction.equals("right")) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        else if(direction.equals("left")) {
            g2.drawImage(image, screenX + gp.tileSize, screenY, -gp.tileSize, gp.tileSize, null);
        }
        else{
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //System.out.println("Vida Maxima: " + maxHealth + "Vida Actual: " + health);

        // Barra de experiencia
        g2.setColor(Color.BLACK);
        g2.fillRect(10, 0, gp.screenWidth - 18, 20);  // x, y, ancho, alto

        // Dibuja la barra de experiencia (azul)
        if(exp != 0)
        {
            // Calcula el porcentaje
            int anchoBarra = (int)((float)exp / nextLevelExp * gp.screenWidth - 18);
            g2.setColor(Color.getHSBColor(200f / 360f,0.7f, 0.90f));
            g2.fillRect(10, 0, anchoBarra, 20);  // x, y, ancho variable, alto
        }

        // Borde de la barra
        g2.setColor(Color.YELLOW);  // Color del borde
        g2.drawRect(8, 0, gp.screenWidth - 16, 20);  // x, y, ancho, alto (tamaño del borde)

        // Opcional: Añadir texto para mostrar la experiencia
        g2.setColor(Color.WHITE);
        g2.drawString("Exp: " + exp + "/" + nextLevelExp, gp.screenWidth / 2, 15);

        // Calculo para la exp
        int healthWidth = (int)((health / (float)maxHealth) * 100);
        g2.setColor(Color.RED);
        g2.fillRect(screenX - 26, screenY - 10, 100, 6);
        g2.setColor(Color.green);
        g2.fillRect(screenX - 26, screenY - 10, healthWidth, 6);

        AffineTransform old = g2.getTransform();

        if(closest != null){
            g2.setColor(Color.black);

            int playerCenterX = screenX + gp.tileSize / 2;
            int playerCenterY = screenY + gp.tileSize / 2;

            int indicatorX = playerCenterX + (int)(50 * Math.cos(angulo));
            int indicatorY = playerCenterY + (int)(50 * Math.sin(angulo));

            g2.translate(indicatorX, indicatorY);
            g2.rotate(angulo);
            g2.fillRect(-5,-5,10,10);
            g2.rotate(-angulo);

            g2.setTransform(old);
        }

        //System.out.println("Health: " + health + " Max Health: " + maxHealth + " Damage: " + damage);
    }

    public void checkClosestEnemy() {
        double closestDistance = Double.MAX_VALUE;

        for (Enemy enemy : gp.enemies) {
            double distance = Math.hypot(this.worldX - enemy.worldX,this.worldY - enemy.worldY);
            if(distance < DETECTION_RANGE) {
                if (distance < closestDistance) {
                    if(enemy != closest) {
                        closestDistance = distance;
                        closest = enemy;
                        dx = closest.worldX - this.worldX;
                        dy = closest.worldY - this.worldY;
                        angulo = Math.atan2(dy, dx);

                        bulletX = (int) (this.worldX + 30 * Math.cos(angulo));
                        bulletY = (int) (this.worldY + 30 * Math.sin(angulo));
                        if(!closest.alive){closest = null;}
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
            nextLevelExp = nextLevelExp * 2;
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
}
