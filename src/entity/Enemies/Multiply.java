package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

//import javax.imageio.ImageIO;
//import java.io.IOException;

public class Multiply extends Enemy {
    public Multiply(GamePanel gamePanel, int startX, int startY, int multiplier) {
        super(gamePanel, startX, startY);
        this.nombre = "Multiplicación";
        this.speed = 3;
        this.damage = 8 * multiplier;
        this.exp = 3;
        this.health = 3 * multiplier;
        this.growl = 6;
        loadEnemyImages();
    }

    @Override
    protected void loadEnemyImages() {

        frame1 = setup("/Enemies/Multiplicacion-0001");
        frame2 = setup("/Enemies/Multiplicacion-0002");
        frame3 = setup("/Enemies/Multiplicacion-0003");
        frame4 = setup("/Enemies/Multiplicacion-0004");
    }
}
