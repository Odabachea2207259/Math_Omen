package serialization;

import java.io.*;
import main.GamePanel;

public class Saver {
    GamePanel gamePanel;
    public Saver(GamePanel gp) {
        this.gamePanel = gp;
    }

    public void leerArchivo(String archivo) {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(archivo));
            try {
                while (true) { // Continúa leyendo hasta que se lance EOFException
                    gamePanel.users.add((User) is.readObject());
                }
            } catch (EOFException eof) {
                // Fin del archivo alcanzado
            } finally {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribirArchivo(String archivo) {
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(archivo));
            for(User user : gamePanel.users){
                os.writeObject(user);
            }
            os.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
