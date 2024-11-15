package main;

import OperationGenerator.OperationGenerator;
import OperationGenerator.Operation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class UI {
    public GamePanel gp;
    private Font arial_100, arial_40;
    private Font arial_title;
    private Graphics2D g2;

    private Image backgroundImage;

    public TitleScreen titleScreen;
    public PauseScreen pauseScreen;
    public OperationScreen operationScreen;
    public MessageDisplay messageDisplay;

    OperationGenerator generator = new OperationGenerator();
    Operation operation;
    int operandsToGen = 2;
    String op;
    int currentOption = 0;
    int foo = 0;

    public void setFoo(int foo) {
        this.foo = foo;
    }

    public boolean deadPlayer = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.arial_100 = new Font("Arial", Font.BOLD, 100);
        this.arial_40 = new Font("Arial", Font.PLAIN, 40);
        this.arial_title = new Font("Arial", Font.BOLD, 120);

        // Cargar la imagen de fondo
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/Main.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inicializar pantallas anidadas
        titleScreen = new TitleScreen();
        pauseScreen = new PauseScreen();
        operationScreen = new OperationScreen();
        messageDisplay = new MessageDisplay();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        // Simplified state machine
        switch (gp.gameState) {
            case GamePanel.titleState:
                titleScreen.draw();
                break;
            case GamePanel.playState:
                messageDisplay.draw();
                break;
            case GamePanel.pauseState:
                pauseScreen.draw(g2);
                break;
            case GamePanel.operationState:
                if (foo == 0) {
                    operation = generator.generateOperation(gp.player.level, operandsToGen);
                    op = operation.toString();
                    operationScreen.initialize(operation.getResult());
                    foo++;
                }
                operationScreen.draw();
                break;
            default:
                break;
        }
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getYforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        return gp.screenHeight / 2 - length / 2;
    }

    // Clase anidada para la pantalla de título
    public class TitleScreen {
        public int commandNum = 0;
        public int titleScreenState = 0;

        public void draw() {
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            if (titleScreenState == 0) {
                drawMainMenu();
            } else if (titleScreenState == 1) {
                drawCharacterSelection();
            }
        }

        public void drawMainMenu() {
            if (backgroundImage != null) {
                g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
            } else {
                // Si no hay imagen, rellenar con color de fondo negro
                g2.setColor(new Color(0, 0, 0));
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            }

            g2.setFont(arial_title);

            String title = "Math Omen";
            int x = getXforCenteredText(title);
            int y = gp.tileSize * 3;

            g2.setColor(Color.gray);
            g2.drawString(title, x, y + 80);
            g2.setColor(Color.WHITE);
            g2.drawString(title, x, y + 70);

            int alpha = 200;
            Color overlayColor = new Color(0, 0, 0, alpha);
            g2.setColor(overlayColor);
            g2.fillRoundRect(gp.screenWidth / 4, gp.screenHeight / 2, gp.screenWidth / 2, 300, 40, 40);

            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(6));
            g2.drawRoundRect(gp.screenWidth / 4, gp.screenHeight / 2, gp.screenWidth / 2, 300, 40, 40);

            g2.setFont(arial_40);

            drawMenuOption("NEW GAME", y + gp.tileSize * 6, 0);
            drawMenuOption("SCOREBOARD", y + gp.tileSize * 8, 1);
            drawMenuOption("QUIT", y + gp.tileSize * 10, 2);
        }

        public void drawCharacterSelection() {
            g2.setFont(g2.getFont().deriveFont(42F));
            g2.setColor(Color.white);

            String title = "Select your character";
            int x = getXforCenteredText(title);
            int y = gp.tileSize * 3;
            g2.drawString(title, x, y);

            drawMenuOption("Boy", y + gp.tileSize * 3, 0);
            drawMenuOption("Girl", y + gp.tileSize * 6, 1);
        }

        public void drawMenuOption(String text, int y, int commandIndex) {
            int x = getXforCenteredText(text);
            g2.drawString(text, x, y);
            if (commandNum == commandIndex) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    /*public class PauseScreen {
        public void draw() {
            g2.setFont(arial_100);
            g2.setColor(Color.WHITE);
            String text = "PAUSE";
            int x = getXforCenteredText(text);
            int y = getYforCenteredText(text);
            g2.drawString(text, x, y);
        }
    }*/

    // Clase anidada para la pantalla de pausa
    public class PauseScreen {

        private Font arial_100 = new Font("Arial", Font.PLAIN, 100); // Fuente para el título
        private Font arial_50 = new Font("Arial", Font.PLAIN, 50);  // Fuente para las opciones
        private int selectedOption = 0; // Para saber qué opción está seleccionada (0: Continuar, 1: Opciones, 2: Salir)

        public void draw(Graphics2D g2) {

            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(6));
            g2.drawRoundRect(gp.screenWidth / 4, gp.screenHeight / 4, gp.screenWidth / 2, 400, 40, 40);

            int alpha = 200;
            Color overlayColor = new Color(0, 0, 0, alpha);
            g2.setColor(overlayColor);
            g2.fillRoundRect(gp.screenWidth / 4, gp.screenHeight / 4, gp.screenWidth / 2, 400, 40, 40);


            // Título "PAUSE"
            g2.setFont(arial_100);
            g2.setColor(Color.WHITE);
            String text = "PAUSE";
            int x = getXforCenteredText(g2, text, arial_100);
            int y = getYforCenteredText(g2, text, arial_100);
            g2.drawString(text, x, y);

            // Opciones del menú
            g2.setFont(arial_50);

            // Continuar
            g2.setColor(selectedOption == 0 ? Color.YELLOW : Color.WHITE);
            String continueText = "Continuar";
            int continueX = getXforCenteredText(g2, continueText, arial_50);
            int continueY = y + 100; // Se coloca 100 píxeles abajo del título
            g2.drawString(continueText, continueX, continueY);

            // Opciones
            g2.setColor(selectedOption == 1 ? Color.YELLOW : Color.WHITE);
            String optionsText = "Opciones";
            int optionsX = getXforCenteredText(g2, optionsText, arial_50);
            int optionsY = continueY + 60; // Separado 60 píxeles de la opción anterior
            g2.drawString(optionsText, optionsX, optionsY);

            // Salir
            g2.setColor(selectedOption == 2 ? Color.YELLOW : Color.WHITE);
            String exitText = "Salir";
            int exitX = getXforCenteredText(g2, exitText, arial_50);
            int exitY = optionsY + 60; // Separado 60 píxeles de la opción anterior
            g2.drawString(exitText, exitX, exitY);
        }
        // Metodo para centrar el texto en el eje X
        private int getXforCenteredText(Graphics g2, String text, Font font) {
            int textWidth = g2.getFontMetrics(font).stringWidth(text);
            return (gp.screenWidth - textWidth) / 2;  // Asumiendo que el ancho de la pantalla es 800px
        }

        // Metodo para centrar el texto en el eje Y
        private int getYforCenteredText(Graphics g2, String text, Font font) {
            int textHeight = g2.getFontMetrics(font).getHeight();
            return (gp.screenHeight - textHeight) / 2;  // Asumiendo que el alto de la pantalla es 600px
        }

        // Métodos para manejar la selección de opciones (puedes adaptarlos con eventos de teclado o ratón)
        public void selectUp() {
            if (selectedOption > 0) {
                selectedOption--;
            }
        }

        public void selectDown() {
            if (selectedOption < 2) {
                selectedOption++;
            }
        }

        public int getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(int option) {
            this.selectedOption = option;
        }
    }


    // Clase anidada para la pantalla de operación
    public class OperationScreen {
        private double[] answers = new double[3];
        private double correctAnswer;
        int selectedOption = 0;
        String resultMessage = "";
        long resultTime = -1;
        boolean resultDisplayed = false;
        private static final int MESSAGE_DISPLAY_TIME = 500;

        public void initialize(double correctAnswer) {
            this.correctAnswer = correctAnswer;
            answers[0] = correctAnswer;
            answers[1] = correctAnswer + 1;
            answers[2] = correctAnswer - 1;
            shuffleArray(answers);
            selectedOption = 0;
            resultMessage = "";
            resultTime = -1;
            resultDisplayed = false;
        }

        public void draw() {
            int alpha = 200;
            Color overlayColor = new Color(0, 0, 0, alpha);

            g2.setFont(arial_40);

            int x = getXforCenteredText(op);
            int y = gp.screenHeight / 2 - 100;

            g2.setColor(overlayColor);
            g2.fillRect(gp.screenWidth / 4, gp.screenHeight / 4, gp.screenWidth / 2, gp.screenHeight / 2);

            g2.setColor(Color.WHITE);
            g2.drawString(op, x, y);

            for (int i = 0; i < 3; i++) {
                String optionText = String.format("%.2f", answers[i]);
                x = getXforCenteredText(optionText);
                y = gp.screenHeight / 2 + i * 50;


                if (i == selectedOption) {
                    g2.setColor(Color.YELLOW);
                } else {
                    g2.setColor(Color.WHITE);
                }

                g2.drawString(optionText, x, y);
            }

            if(!resultMessage.isEmpty()) {
                g2.setFont(arial_40);
                g2.setColor(Color.YELLOW);
                int messageX = getXforCenteredText(resultMessage);
                int messageY = gp.screenHeight / 2 + 160;
                g2.drawString(resultMessage, messageX, messageY);
                //selectOption();

            }

            if(resultDisplayed && System.currentTimeMillis() - resultTime >= MESSAGE_DISPLAY_TIME) {
                gp.gameState = gp.playState;
                resultMessage = "";
                resultDisplayed = false;
            }
        }

        public void selectOption() {

            boolean correct = isAnswerCorrect();
            if (correct) {
                // Acción si la respuesta es correcta
                resultMessage = "CORRECT!!!";
                gp.playSoundEffect(13);
            } else {
                // Acción si la respuesta es incorrecta
                resultMessage = "WRONG!!!";
                gp.playSoundEffect(12);
            }

            gp.ui.operationScreen.resultTime = System.currentTimeMillis();
            gp.ui.operationScreen.resultDisplayed = true;
        }

        public void navigateOptions(int direction) {
            selectedOption = (selectedOption + direction + 3) % 3;
        }

        private void shuffleArray(double[] array) {
            for (int i = array.length - 1; i > 0; i--) {
                int index = (int) (Math.random() * (i + 1));
                double temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }

        public boolean isAnswerCorrect() {
            return answers[selectedOption] == correctAnswer;
        }
    }

    // Clase anidada para mostrar mensajes temporales
    public class MessageDisplay {
        private boolean messageOn = true;
        private String message = "GAME OVER!";

        public void draw() {
            if (messageOn && deadPlayer) {
                g2.setFont(arial_100);
                g2.setColor(Color.RED);
                int textLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth();
                int x = gp.screenWidth / 2 - textLength / 2;
                int y = gp.screenHeight / 2 - (gp.tileSize * 3);
                g2.drawString(message, x, y);
            }
        }
    }
}
