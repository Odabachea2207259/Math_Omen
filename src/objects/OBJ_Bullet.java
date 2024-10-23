package objects;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Bullet extends Projectile {
    GamePanel gp;

    public OBJ_Bullet(GamePanel gp) {
        super(gp);
        this.gp = gp;

        speed = 8;
        health = 80;
        damage = 1;
        alive = false;
    }
}
