package entity;

import main.GamePanel;
import main.KeyHandler;
import objects.OBJ_Bullet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Player extends Entity {

    public KeyHandler kh;

    public final int screenX;
    public final int screenY;

    public static final double DETECTION_RANGE = 200;
    public Enemy closest = null;
    public double dx,dy,angulo = 0;
    public int bulletX, bulletY;
    public ArrayList<OBJ_Bullet> bullets = new ArrayList<>();

    public int shootCounter = 0;

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
        speed = 14;
        health = 100;
        direction = "up";
        exp = 0;
        level = 1;
        nextLevelExp = 25;

        projectile = new OBJ_Bullet(gp);
    }

    public void update(){

        float deltaX = 0;
        float deltaY = 0;

        if(kh.upPressed && canUp){
            //deltaY = -1;
            worldY -= speed;
            direction = "up";
        }
        if(kh.downPressed && canDown){
            //deltaY = 1;
            worldY += speed;
            direction = "down";
        }
        if(kh.leftPressed && canLeft){
            //deltaX = -1;
            worldX -= speed;
            direction = "left";
        }
        if(kh.rightPressed && canRight){
            //deltaX = 1;
            worldX += speed;
            direction = "right";
        }

        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if(length != 0){
            deltaX = (deltaX / length) * speed;
            deltaY = (deltaY / length) * speed;
        }

        //worldX += (int)deltaX;
        //worldY += (int)deltaY;

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

        if(this.health <= 0){
            gp.ui.showMessage("YOURE DEAD");
            health = 0;
            alive = false;
        }

    }

    //@Override
    public void draw(Graphics2D g2){

        g2.setColor(Color.BLACK);
        g2.fillRect(screenX - 28, screenY - 12, 104, 10);
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        g2.setColor(Color.RED);
        g2.fillRect(screenX - 26, screenY - 10, 100, 6);

        g2.setColor(Color.green);
        g2.fillRect(screenX - 26, screenY - 10, health, 6);

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
                    }
                }
            }
        }
    }

    public void shoot() {
        dx = closest.worldX - this.worldX;
        dy = closest.worldY - this.worldY;
        angulo = Math.atan2(dy, dx);

        int playerCenterX = screenX + gp.tileSize / 2;
        int playerCenterY = screenY + gp.tileSize / 2;

        int indicatorX = playerCenterX + (int)(50 * Math.cos(angulo));
        int indicatorY = playerCenterY + (int)(50 * Math.sin(angulo));

        projectile.set(worldX ,worldY,dx,dy,true,angulo);
        gp.projectileList.add(projectile);
    }



    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp * 2;
            health += 25;
            damage++;
        }
    }
}
