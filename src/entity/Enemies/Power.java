package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Power extends Enemy {
    public Power(GamePanel gamePanel, int startX, int startY) {
        super(gamePanel, startX, startY);
        this.nombre = "Exponente";
        this.speed = 5;
        this.damage = 6;
        this.exp = 4;
        this.health = 1;
        loadEnemyImages();
    }

    @Override
    protected void loadEnemyImages() {

        frame1 = setup("/Enemies/Exponente-0001");
        frame2 = setup("/Enemies/Exponente-0002");
        frame3 = setup("/Enemies/Exponente-0003");
        frame4 = setup("/Enemies/Exponente-0004");
    }
}
