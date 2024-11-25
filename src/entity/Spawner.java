package entity;

import entity.Enemies.EnemyFactory;
import main.GamePanel;

import javax.swing.*;
import java.util.Random;

public class Spawner {
    GamePanel gp;
    Random randomNumbers = new Random(); //SE NECESITA GG
    Timer enemySpawnTimer;

    public Spawner(GamePanel gp) {
        this.gp = gp;
    }

    public void startEnemySpawnTimer() {
        int delay = 2000;

        enemySpawnTimer = new Timer(delay, e -> {

            int sign = randomNumbers.nextInt(2);
            int startX = (int) (Math.random() * gp.screenWidth/2);
            if(sign == 0){
                startX -= gp.screenWidth;
            }
            else{
                startX += gp.screenWidth;
            }
            sign = randomNumbers.nextInt(2);
            int startY = (int) (Math.random() * gp.screenHeight/2);
            if(sign == 0){
                startY -= gp.screenHeight;
            }
            else{
                startY += gp.screenHeight;
            }

            synchronized (gp.enemies) {
                Enemy newEnemy = EnemyFactory.createRandomEnemy(gp,startX,startY,gp.enemiesMultiplier);
                gp.enemies.add(newEnemy);
                System.out.println(newEnemy + "\n");
                System.out.println(gp.player + "\n");
            }

            gp.cantEnemies++;
        });

        enemySpawnTimer.start();
    }

    public void stopEnemySpawner() {
        enemySpawnTimer.stop();
    }
}
