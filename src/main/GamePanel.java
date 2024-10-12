package main;

import entity.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    //Tamaño de los elementos
    final int originalTileSize = 16;
    final int scale = 3;
    //48 px x 48 px
    public final int tileSize = originalTileSize * scale;
    //4:3 ratio
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol; // 768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576 px
    // Tamaño del mapa
    private final int multiplicador = 9;
    public final int maxWorldCol = maxScreenCol * multiplicador;
    public final int maxWorldRow = maxScreenRow * multiplicador;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    // Mapa del juego principal.
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Random randomNumbers = new Random();
    public CollisionChecker cChecker = new CollisionChecker(this);
    // Hilo del juego principal
    Thread gameThread;
    int FPS = 60;
    // Objetos para el juego
    public Player player = new Player(this,keyHandler); // Jugados
    public final ArrayList<Enemy> enemies = new ArrayList<>(); // Lista de enemigos.
    // Label para la cantidad de enemigos.
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

        double drawInterval = (double) 1000000000 /FPS;
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

        synchronized(enemies) {
            for (Enemy enemy : enemies) {
                enemy.update(player.worldX, player.worldY);
            }
        }

        scoreCantEnemies.setText(cantEnemies + "");
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        player.draw(g2);

        for(Enemy enemy : enemies){
            enemy.draw(g2,player);
        }

        drawPlayerHealthBar(g2);

        g2.dispose();
    }

    private void startEnemySpawnTimer(GamePanel gp) {
        int delay = 2000;

        Timer enemySpawnTimer = new Timer(delay, _ -> {

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

    private void drawPlayerHealthBar(Graphics2D g2) {
        if(!player.isHealthBarVisible) return;

        int barWidth = 48; // Ancho de la barra de vida
        int barHeight = 6; // Altura de la barra de vida
        int healthWidth = (int) ((player.health / (double) player.maxHealth) * barWidth); // Longitud según la salud

        // Dibuja el fondo de la barra de vida
        g2.setColor(Color.RED);
        g2.fillRect(player.screenX - 1, player.screenY - 10, barWidth, barHeight); // Mueve la barra 10 píxeles arriba

        // Dibuja la barra de vida
        g2.setColor(Color.GREEN);
        g2.fillRect(player.screenX - 1, player.screenY - 10, healthWidth, barHeight);

        // Dibuja el contorno de la barra de vida
        g2.setColor(Color.BLACK);
        g2.drawRect(player.screenX - 1, player.screenY - 10, barWidth, barHeight); // Dibuja el borde
    }
}
