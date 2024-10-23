package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Division extends Enemy {
    public Division(GamePanel gamePanel, int startX, int startY) {
        super(gamePanel, startX, startY);
        this.speed = 2;
        this.damage = 15;
        this.exp = 3;
        loadEnemyImages();
    }

    @Override
    protected void loadEnemyImages() {

        frame1 = setup("/Enemies/Division-0001");
        frame2 = setup("/Enemies/Division-0002");
        frame3 = setup("/Enemies/Division-0003");
        frame4 = setup("/Enemies/Division-0004");
    }
}
