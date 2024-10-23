package entity.Enemies;

import entity.Enemies.*;
import entity.Enemy;
import main.GamePanel;
import java.util.Random;

public class EnemyFactory {
    private static final Random random = new Random();

    public static Enemy createRandomEnemy(GamePanel gamePanel, int startX, int startY) {
        int enemyType = random.nextInt(5) + 1;
        return switch (enemyType) {
            case 1 -> new Add(gamePanel, startX, startY);
            case 2 -> new Substract(gamePanel, startX, startY);
            case 3 -> new Multiply(gamePanel, startX, startY);
            case 4 -> new Division(gamePanel, startX, startY);
            case 5 -> new Power(gamePanel, startX, startY);
            default -> new Add(gamePanel, startX, startY); // Fallback
        };
    }
}
