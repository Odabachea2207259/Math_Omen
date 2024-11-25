package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

public class Power extends Enemy {
    public Power(GamePanel gamePanel, int startX, int startY, int multiplier) {
        super(gamePanel, startX, startY);
        this.nombre = "Exponente";
        this.speed = 4;
        this.damage = 6 * multiplier;
        this.exp = 4;
        this.health = 1 * multiplier;
        this.growl = 7;
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
