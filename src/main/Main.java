package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Math Omen");

        GamePanel gamePanel = new GamePanel();

        JLabel cantEnemies = new JLabel("Puntaje: 0");
        cantEnemies.setFont(new Font("Arial", Font.BOLD, 24));  // Cambia el estilo si quieres
        cantEnemies.setHorizontalAlignment(SwingConstants.CENTER);

        window.add(gamePanel, BorderLayout.CENTER);
        window.add(cantEnemies, BorderLayout.NORTH);

        window.pack();

        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setScoreLabel(cantEnemies);

        gamePanel.startGameThread();
    }
}