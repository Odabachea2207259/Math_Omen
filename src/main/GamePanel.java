package main;

import entity.*;
import serialization.*;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


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
    public CollisionChecker cChecker = new CollisionChecker(this); //CHECKA COLISIONES DE JUGADORES Y ENEMIGOS

    Sound backgroundMusic  = new Sound();
    Sound soundEffect  = new Sound();
    public int currentSongIndex = -1;

    Thread gameThread; //HILO PRINCIPAL DEL JUEGO
    int time = 0;

    public Player player = new Player(this,keyHandler); //JUGADOR
    public Spawner spawner = new Spawner(this);
    public ArrayList<Enemy> enemies = new ArrayList<>(); //TODOS LOS ENEMIGOS
    public int enemiesMultiplier = 1;
    public ArrayList<Projectile> projectileList = new ArrayList<>(); //PROJECTILES

    public ArrayList<User> users = new ArrayList<>();
    public Saver saver = new Saver(this);

    JLabel scoreCantEnemies; //PARA VER CUANTOS ENEMIGOS EN PANTALLA
    public int cantEnemies = 0;

    //COSAS DE LA UI, MENU, JUEGO, PAUSA
    public UI ui = new UI(this); //UI DEL JUEGO COMPLETO
    public int gameState;
    public static final int titleState = 0;
    public static final int playState = 1;
    public static final int pauseState = 2;
    public static final int operationState = 3;
    public static final int characterState = 4;
    public static final int registerState = 5;

    //CONSTRUCTOR
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        //Mejorar el rendimiento
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        setupGame();
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreCantEnemies = scoreLabel;
    }

    public void setupGame(){
        //INICIA PROGRAMA EN EL TITULO
        gameState = titleState;
        playMusic(2);
        saver.leerArchivo("res/Game.ser");
        users.sort((u1, u2) -> Integer.compare(u2.getPuntos(), u1.getPuntos()));
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
            changeMusic(0);
            if(time == 0){
                spawner.startEnemySpawnTimer();
                time++;
            }

            synchronized (player){
                if (player.alive) {
                    player.update(); //ACTUALIZAR JUGADOR
                }
                else{
                    gameState = registerState;
                }
            }

            synchronized (enemies){
                if(ui.operationScreen.wrong){
                    enemiesMultiplier++;
                    ui.operationScreen.wrong = false;
                }
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
            //stopMusic();
        }

        if(gameState == operationState){
            //stopMusic();
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //DIBUJAR EL TITULO
        if(gameState == titleState){
            changeMusic(2);
            enemies.clear();
            projectileList.clear();
            player.setDefaultValues();
            time = 0;
            ui.draw(g2); // COMO EL GAMESTATE ESTA POR DEFAULT EN TITLESTATE SE DIBUJARA EL TITULO
        }
        else{

            tileManager.draw(g2); //MAPA

            player.draw(g2); //JUGADOR

            synchronized (enemies) {
                for (Enemy enemy : enemies) {
                    enemy.draw(g2, player); //CADA ENEMIGO
                }
            }

            for (Projectile projectile : projectileList) {
                projectile.draw(g2); //LOS PROYECTILES ""EN PRUEBA""
            }

            ui.draw(g2); //UI
            player.drawExperienceBar(g2);
            player.drawScore(g2);
        }

        //BORRA TODO
        g2.dispose();
    }

    public void playMusic(int i) {
        backgroundMusic.setFile(i);
        backgroundMusic.play();
        backgroundMusic.loop();
    }

    public void stopMusic(){
        backgroundMusic.stop();
        currentSongIndex = -1;
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }

    public void changeMusic(int i){
        if(i == currentSongIndex){return;}

        stopMusic();
        playMusic(i);
        currentSongIndex = i;
    }
}