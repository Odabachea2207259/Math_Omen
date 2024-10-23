package entity;

import main.GamePanel;

import java.awt.*;

public class Projectile extends Entity{

    double angle,magnitud;
    public Color color;

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
        //System.out.println("Dx: "+ dx + " Dy: "+ dy + "D: " + magnitud + " Angulo: " + Math.toDegrees(angulo));
    }

    public void update(){
        worldX += (int)((dx * speed)/magnitud);
        worldY += (int)((dy * speed)/magnitud);

//        worldX += (int)dx / speed;
//        worldY += (int)dy / speed;

        //System.out.println(worldX + " " + worldY);
        health--;
        if(health <= 0){
            alive = false;
        }
    }

    public void draw(Graphics2D g2){
        g2.setColor(color);
        g2.fillOval(worldX - gp.player.worldX + gp.player.screenX,worldY - gp.player.worldY + gp.player.screenY,30,30);

        g2.rotate(angle);
        g2.rotate(-angle);
    }
}
