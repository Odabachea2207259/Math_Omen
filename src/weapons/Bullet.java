package weapons;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bullet {
    // Constantes
    private static final int BULLET_SPEED = 10; // Ajusta la velocidad según sea necesario
    public static final int BULLET_WIDTH = 10; // Ancho de la bala
    public static final int BULLET_HEIGHT = 10; // Altura de la bala
    private static final int MAX_DISTANCE = 500; // Distancia maxima que puede recorrer
    public boolean isActive;

    // Objetos
    private final GamePanel gamePanel;
    private BufferedImage image;
    // Movimiento en el mapa
    private int worldX, worldY;
    private final double deltaX;
    private final double deltaY;
    private double traveledDistance;


    public Bullet(GamePanel gamePanel, int startX, int startY, int targetX, int targetY) {
        this.gamePanel = gamePanel;
        this.worldX = startX;
        this.worldY = startY;
        this.isActive = true;

        // Cargamos la imagen de la bala
        loadBulletImage();

        // Calcula la dirección hacia el objetivo
        double angle = Math.atan2(targetY - startY, targetX - startX);
        this.deltaX = Math.cos(angle) * BULLET_SPEED;
        this.deltaY = Math.sin(angle) * BULLET_SPEED;

        // Inicializa la distancia recorrida
        this.traveledDistance = 0;
    }

    public void update() {
        // Mueve la bala en la dirección calculada
        worldX += (int) deltaX;
        worldY += (int) deltaY;

        // Aumenta la distancia recorrida
        traveledDistance += BULLET_SPEED;

        // Verifica si ha alcanzado la distancia máxima o está fuera de los límites
        if (traveledDistance >= MAX_DISTANCE || isOutOfBounds()) {
            isActive = false; // Marca la bala como inactiva
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, worldX, worldY, null);
    }

    public boolean hasReachedMaxDistance() {
        return traveledDistance >= MAX_DISTANCE;
    }

    public boolean isOutOfBounds() {
        // Cambia estos valores según el tamaño del mundo
        return worldX < 0 || worldX > gamePanel.worldWidth || worldY < 0 || worldY > gamePanel.worldHeight;
    }

    private void loadBulletImage() {
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/weapons/bullet1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
