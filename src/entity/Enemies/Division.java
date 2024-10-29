package entity.Enemies;

import entity.Enemy;
import entity.EnemyImages;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Division extends Enemy implements EnemyImages {
    public Division(GamePanel gamePanel, int startX, int startY) {
        super(gamePanel, startX, startY);
        this.nombre = "Division";
        this.speed = 2;
        this.damage = 15;
        this.exp = 3;
        this.health = 4;
        loadEnemyImages();
    }

    @Override
    public void loadEnemyImages() {
        frames[0] = setup("/Enemies/Division-0001");
        frames[1] = setup("/Enemies/Division-0002");
        frames[2] = setup("/Enemies/Division-0003");
        frames[3] = setup("/Enemies/Division-0004");
    }
}
