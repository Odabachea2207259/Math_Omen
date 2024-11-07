package main;

import entity.*;
import entity.Enemies.EnemyFactory;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private final int multiplicador = 9;
    public final int maxWorldCol = maxScreenCol * multiplicador;
    public final int maxWorldRow = maxScreenRow * multiplicador;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS DEL JUEGO
    int FPS = 60;

    TileManager tileManager = new TileManager(this); //CONSTRUCTOR MAPA
    KeyHandler keyHandler = new KeyHandler(this); //MANEJA LAS ENTRADAS DE TECLADO
    Random randomNumbers = new Random(); //SE NECESITA GG
    public CollisionChecker cChecker = new CollisionChecker(this); //CHECKA COLISIONES DE JUGADORES Y ENEMIGOS

    Thread gameThread; //HILO PRINCIPAL DEL JUEGO


    public Player player = new Player(this,keyHandler); //JUGADOR
    public ArrayList<Enemy> enemies = new ArrayList<>(); //TODOS LOS ENEMIGOS
    public ArrayList<Projectile> projectileList = new ArrayList<>(); //PROJECTILES ""EN PRUEBA""
    ArrayList<Entity> entityList = new ArrayList<>();

    JLabel scoreCantEnemies; //PARA VER CUANTOS ENEMIGOS EN PANTALLA
    int cantEnemies = 0;

    //COSAS DE LA UI, MENU, JUEGO, PAUSA
    public UI ui = new UI(this); //UI DEL JUEGO COMPLETO
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

    //CONSTRUCTOR
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        //Mejorar el rendimiento
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        setupGame();
        startEnemySpawnTimer(this);
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreCantEnemies = scoreLabel;
    }

    public void setupGame(){
        //INICIA PROGRAMA EN EL TITULO
        gameState = titleState;
    }

    public void startGameThread(){
        //INICIA HILO DEL JUEGO
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

        //SI EL JUEGO ESTA EN ESTADO PLAY
        if(gameState == playState){
            synchronized (player){
                if (player.alive) {
                    player.update(); //ACTUALIZAR JUGADOR

                }
            }

//            synchronized(enemies) {
//                for (Enemy enemy : enemies) {
//                    if (enemy.alive) {
//                        enemy.update(player.worldX, player.worldY); //ACTUALIZAR CADA ENEMIGO CON LA UBICACION DEL JUGADOR
//                    }
//                    else {
//                        enemies.remove(enemy);
//                    }
//                }
//            }

            synchronized (enemies){
                for(int i = 0; i < enemies.size(); i++){
                    Enemy e = enemies.get(i);
                    if(e.alive){
                        e.update(player.worldX, player.worldY);
                    }
                    else {
                        enemies.remove(e);
                    }
                }
            }


            //""PRUEBA""
            synchronized (projectileList) {
                for (int i = 0; i < projectileList.size(); i++) {
                    Projectile p = projectileList.get(i);
                    if(p.alive){
                        p.update();
                    }
                    else{
                        projectileList.remove(i);
                    }
                }
            }

            scoreCantEnemies.setText("Cantidad de enemigos: " + enemies.size() + ""); //ACTUALIZA CANT ENEMIGOS
        }

        //LO QUE HARA SI EL JUEGO ESTA EN PAUSA
        if(gameState == pauseState){
            //FALTA IMPLEMENTAR MENU DE PAUSA EN UI
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //DIBUJAR EL TITULO
        if(gameState == titleState){
            ui.draw(g2); // COMO EL GAMESTATE ESTA POR DEFAULT EN TITLESTATE SE DIBUJARA EL TITULO
        }
        else{

            tileManager.draw(g2); //MAPA

            entityList.add(player);

            player.draw(g2); //JUGADOR

            synchronized (enemies) {
                for (Enemy enemy : enemies) {
                    entityList.add(enemy);
                    enemy.draw(g2, player); //CADA ENEMIGO
                }
            }

            for(Projectile p : projectileList){
                p.draw(g2);
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);

                    return result;
                }
            });

//            for(Entity entity : entityList){
//                entity.draw(g2);
//            }

            entityList.clear();

            for (Projectile projectile : projectileList) {
                projectile.draw(g2); //LOS PROYECTILES ""EN PRUEBA""
            }

            ui.draw(g2); //UI
        }

        //BORRA TODO
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
                Enemy newEnemy = EnemyFactory.createRandomEnemy(this,startX,startY);
                enemies.add(newEnemy);
                System.out.println(newEnemy + "\n");
            }

            cantEnemies++;
        });

        enemySpawnTimer.start();
    }

}
