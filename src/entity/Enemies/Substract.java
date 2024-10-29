package entity.Enemies;

import entity.Enemy;
import entity.EnemyImages;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Substract extends Enemy implements EnemyImages {
    public Substract(GamePanel gamePanel, int startX, int startY) {
        super(gamePanel, startX, startY);
        this.nombre = "Menos";
        this.speed = 2;
        this.damage = 10;
        this.exp = 2;
        this.health = 5;
        loadEnemyImages();
    }

    @Override
    public void loadEnemyImages() {
        frames[0] = setup("/Enemies/Menos-0001");
        frames[1] = setup("/Enemies/Menos-0002");
        frames[2] = setup("/Enemies/Menos-0003");
        frames[3] = setup("/Enemies/Menos-0004");
    }
}
