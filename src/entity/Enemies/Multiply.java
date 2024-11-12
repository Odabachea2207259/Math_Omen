package entity.Enemies;

import entity.Enemy;
import main.GamePanel;

//import javax.imageio.ImageIO;
//import java.io.IOException;

public class Multiply extends Enemy {
    public Multiply(GamePanel gamePanel, int startX, int startY) {
        super(gamePanel, startX, startY);
        this.nombre = "Multiplicación";
        this.speed = 4;
        this.damage = 8;
        this.exp = 3;
        this.health = 3;
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
