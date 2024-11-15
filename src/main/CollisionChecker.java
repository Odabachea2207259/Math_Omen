package main;

import entity.Enemy;
import entity.Player;
import entity.Projectile;

public class CollisionChecker {
    GamePanel gp;


    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }


    public void checkTile(Player entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow;
        int entityBottomRow;

        int tileNum1, tileNum2;

        entity.canDown = true;
        entity.canUp = true;
        entity.canLeft = true;
        entity.canRight = true;
        entity.speed = 6;


        entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];

        if (gp.tileManager.tile[tileNum1].collision) {
            entity.canUp = false;
        }
        else if (gp.tileManager.tile[tileNum1].slow || gp.tileManager.tile[tileNum2].slow) {
            entity.speed = 3;
        }

        entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
            entity.canDown = false;
        }
        else if (gp.tileManager.tile[tileNum1].slow || gp.tileManager.tile[tileNum2].slow) {
            entity.speed = 3;
        }

        entityTopRow = entityTopWorldY / gp.tileSize;
        entityBottomRow = entityBottomWorldY / gp.tileSize;

        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
            entity.canLeft = false;
        }
        else if (gp.tileManager.tile[tileNum1].slow || gp.tileManager.tile[tileNum2].slow) {
            entity.speed = 3;
        }



        entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
        tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

        if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
            entity.canRight = false;
        }
        else if (gp.tileManager.tile[tileNum1].slow || gp.tileManager.tile[tileNum2].slow) {
            entity.speed = 3;
        }
    }

    public void checkCollisionWithOtherEnemies(Enemy actualEnemy) {
        for (Enemy otherEnemy : gp.enemies) {
            if (otherEnemy != actualEnemy) {
                // Detecta si los rectángulos están intersectando
                if (actualEnemy.solidArea.intersects(otherEnemy.solidArea)) {
                    // Calcula las diferencias de posición entre los dos enemigos
                    int dx = actualEnemy.solidArea.x - otherEnemy.solidArea.x;
                    int dy = actualEnemy.solidArea.y - otherEnemy.solidArea.y;

                    // Verifica la colisión por los lados
                    if (Math.abs(dx) > Math.abs(dy)) {
                        if (dx > 0) {
                            actualEnemy.canRight = false;
                            //System.out.println("Colisión en el lado derecho de " + otherEnemy);
                            actualEnemy.worldX += actualEnemy.speed;  // Mueve al enemigo hacia la derecha
                        } else {
                            actualEnemy.canLeft = false;
                            //System.out.println("Colisión en el lado izquierdo de " + otherEnemy);
                            actualEnemy.worldX -= actualEnemy.speed;  // Mueve al enemigo hacia la izquierda
                        }
                    } else {
                        if (dy > 0) {
                            actualEnemy.canDown = false;
                            //System.out.println("Colisión en la parte inferior de " + otherEnemy);
                            actualEnemy.worldY += actualEnemy.speed;  // Mueve al enemigo hacia abajo
                        } else {
                            actualEnemy.canUp = false;
                            //System.out.println("Colisión en la parte superior de " + otherEnemy);
                            actualEnemy.worldY -= actualEnemy.speed;  // Mueve al enemigo hacia arriba
                        }
                    }
                }
                else{
                    actualEnemy.canRight = true;
                    actualEnemy.canLeft = true;
                    actualEnemy.canUp = true;
                    actualEnemy.canDown = true;
                }
            }
        }
    }

    public void checkCollisionWithPlayer(int playerX, int playerY) {
        for (Enemy enemies : gp.enemies) {
            if (enemies.solidArea.intersects(playerX,playerY, gp.tileSize,gp.tileSize)) {
                if(!gp.player.invincible) {
                    gp.player.health -= enemies.damage;
                    gp.player.invincible = true;
                }
            }
            else{
                enemies.playerCollision = false;
            }
        }
    }

    public void checkProjectile(Projectile projectile){
        for(Enemy enemies : gp.enemies){
            if(projectile.worldX + gp.tileSize/2 > enemies.worldX &&
               projectile.worldX + gp.tileSize/2 < enemies.worldX + gp.tileSize &&
               projectile.worldY + gp.tileSize/2 > enemies.worldY &&
               projectile.worldY + gp.tileSize/2 < enemies.worldY + gp.tileSize &&
               projectile.canDamage) {
                enemies.health -= projectile.damage + gp.player.damage;
                gp.playSoundEffect(enemies.growl);
                projectile.alive = false;
                projectile.canDamage = false;
            }
        }
    }

}
