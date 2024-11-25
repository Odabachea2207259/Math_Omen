package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

public class Substract extends Enemy {
    public Substract(GamePanel gamePanel, int startX, int startY, int multiplier) {
        super(gamePanel, startX, startY);
        this.nombre = "Menos";
        this.speed = 2;
        this.damage = 10 * multiplier;
        this.exp = 2;
        this.health = 5 * multiplier;
        this.growl = 8;
        loadEnemyImages();
    }

    @Override
    protected void loadEnemyImages() {

        frame1 = setup("/Enemies/Menos-0001");
        frame2 = setup("/Enemies/Menos-0002");
        frame3 = setup("/Enemies/Menos-0003");
        frame4 = setup("/Enemies/Menos-0004");
    }
}
