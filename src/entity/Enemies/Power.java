package entity.Enemies;

import entity.Enemy;
import entity.EnemyImages;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Power extends Enemy implements EnemyImages {
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
    public void loadEnemyImages() {
        frames[0] = setup("/Enemies/Exponente-0001");
        frames[1] = setup("/Enemies/Exponente-0002");
        frames[2] = setup("/Enemies/Exponente-0003");
        frames[3] = setup("/Enemies/Exponente-0004");
    }
}
