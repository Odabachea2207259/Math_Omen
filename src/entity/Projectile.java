package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity{

    double angle,magnitud;
    public Color color;
    public boolean canDamage = true;
    public BufferedImage image = null;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, double dx, double dy, boolean alive,double angulo){
        this.worldX = worldX;
        this.worldY = worldY;
        this.dx = dx;
        this.dy = dy;
        this.alive = alive;
        this.health = 80;
        this.angle = angulo;

        magnitud = Math.sqrt(dx*dx+dy*dy);
        gp.playSoundEffect(9);
        //System.out.println("Dx: "+ dx + " Dy: "+ dy + "D: " + magnitud + " Angulo: " + Math.toDegrees(angulo));
    }

    public void update(){
        worldX += (int)((dx * speed)/magnitud);
        worldY += (int)((dy * speed)/magnitud);
        canDamage = true;

        health--;
        if(health <= 0){
            alive = false;
            canDamage = false;
        }
    }

    public void draw(Graphics2D g2){
        //g2.setColor(color);
        //g2.fillOval(worldX - gp.player.worldX + gp.player.screenX,worldY - gp.player.worldY + gp.player.screenY,30,30);

        g2.rotate(angle);
        g2.rotate(-angle);

        g2.drawImage(image,worldX - gp.player.worldX + gp.player.screenX,worldY - gp.player.worldY + gp.player.screenY,gp.tileSize,gp.tileSize,null);
    }

}
