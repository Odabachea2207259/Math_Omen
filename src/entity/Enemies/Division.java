package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

public class Division extends Enemy {
    public Division(GamePanel gamePanel, int startX, int startY, int multiplier) {
        super(gamePanel, startX, startY);
        this.nombre = "Division";
        this.speed = 2;
        this.damage = 15 * multiplier;
        this.exp = 3;
        this.health = 4 * multiplier;
        this.growl = 5;
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
