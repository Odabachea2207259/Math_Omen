package entity.Enemies;

import entity.Enemy;
import entity.EnemyImages;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Add extends Enemy implements EnemyImages {
    public Add(GamePanel gamePanel, int startX, int startY) {
        super(gamePanel, startX, startY);
        this.nombre = "Suma";
        this.speed = 3;
        this.damage = 1;
        this.exp = 1;
        this.health = 2;
        loadEnemyImages();
    }

    @Override
    public void loadEnemyImages() {
        frames[0] = setup("/Enemies/Mas-0001");
        frames[1] = setup("/Enemies/Mas-0002");
        frames[2] = setup("/Enemies/Mas-0003");
        frames[3] = setup("/Enemies/Mas-0004");
    }
}
