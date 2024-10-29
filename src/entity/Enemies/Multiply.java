package entity.Enemies;

import entity.Enemy;
import entity.EnemyImages;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Multiply extends Enemy implements EnemyImages {
    public Multiply(GamePanel gamePanel, int startX, int startY) {
        super(gamePanel, startX, startY);
        this.nombre = "Multiplicación";
        this.speed = 4;
        this.damage = 8;
        this.exp = 3;
        this.health = 3;
        loadEnemyImages();
    }

    @Override
    public void loadEnemyImages() {
        frames[0] = setup("/Enemies/Multiplicacion-0001");
        frames[1] = setup("/Enemies/Multiplicacion-0002");
        frames[2] = setup("/Enemies/Multiplicacion-0003");
        frames[3] = setup("/Enemies/Multiplicacion-0004");
    }
}
