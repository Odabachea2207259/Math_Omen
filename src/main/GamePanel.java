package main;

import entity.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16; //Tamaño de los elementos
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48 px x 48 px

    //4:3 ratio
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol; // 768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576 px

    private int multiplicador = 9;
    public final int maxWorldCol = maxScreenCol * multiplicador;
    public final int maxWorldRow = maxScreenRow * multiplicador;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Random randomNumbers = new Random();
    public CollisionChecker cChecker = new CollisionChecker(this);

    Thread gameThread;

    public Player player = new Player(this,keyHandler);

    public ArrayList<Enemy> enemies = new ArrayList<>();

    JLabel scoreCantEnemies;
    int cantEnemies = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);

        //Mejorar el rendimiento
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        startEnemySpawnTimer(this);
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreCantEnemies = scoreLabel;
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){

        player.update();

        for(Enemy enemy : enemies){
            enemy.update(player.worldX,player.worldY);
        }

        scoreCantEnemies.setText(cantEnemies + "");
        //checkCollision();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        player.draw(g2);

        for(Enemy enemy : enemies){
            enemy.draw(g2,player);
        }

        g2.dispose();
    }

    private void startEnemySpawnTimer(GamePanel gp) {
        int delay = 2000;

        Timer enemySpawnTimer = new Timer(delay, e -> {

            int sign = randomNumbers.nextInt(2);
            int startX = (int) (Math.random() * screenWidth/2);
            if(sign == 0){
                startX -= screenWidth;
            }
            else{
                startX += screenWidth;
            }
            sign = randomNumbers.nextInt(2);
            int startY = (int) (Math.random() * screenHeight/2);
            if(sign == 0){
                startY -= screenHeight;
            }
            else{
                startY += screenHeight;
            }

            synchronized (enemies) {
                enemies.add(new Enemy(gp, startX, startY));
            }

            cantEnemies++;
        });

        enemySpawnTimer.start();
    }

}
