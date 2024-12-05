package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

public class Add extends Enemy {
    public Add(GamePanel gamePanel, int startX, int startY, int multiplier) {
        super(gamePanel, startX, startY);
        this.nombre = "Suma";
        this.speed = 3;
        this.damage = multiplier;
        this.exp = 1;
        this.health = 2 * multiplier;
        this.growl = 4;
        loadEnemyImages();
    }

    @Override
    protected void loadEnemyImages() {

        frame1 = setup("/Enemies/Mas-0001");
        frame2 = setup("/Enemies/Mas-0002");
        frame3 = setup("/Enemies/Mas-0003");
        frame4 = setup("/Enemies/Mas-0004");
    }
}
