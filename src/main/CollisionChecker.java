package main;

import entity.Enemy;
import entity.Entity;
import entity.Player;

public class CollisionChecker {
    GamePanel gp;
    boolean canMove = true;

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
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        entity.canDown = true;
        entity.canUp = true;
        entity.canLeft = true;
        entity.canRight = true;
        entity.speed = 6;


        if (entity.kh.upPressed || entity.direction.equals("up")) {
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];

            if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                entity.canUp = false;
            }
            else if (gp.tileManager.tile[tileNum1].slow == true || gp.tileManager.tile[tileNum2].slow == true) {
                entity.speed = 3;
            }
            else{
                canMove = true;
            }
        }

        if (entity.kh.downPressed || entity.direction.equals("down")) {

            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                entity.canDown = false;
            }
            else if (gp.tileManager.tile[tileNum1].slow == true || gp.tileManager.tile[tileNum2].slow == true) {
                entity.speed = 3;
            }
        }
        if(entity.kh.leftPressed || entity.direction.equals("left")) {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                    entity.canLeft = false;
                }
                else if (gp.tileManager.tile[tileNum1].slow == true || gp.tileManager.tile[tileNum2].slow == true) {
                    entity.speed = 3;
                }

        }
        if(entity.kh.rightPressed || entity.direction.equals("right")) {
            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                entity.canRight = false;
            }
            else if (gp.tileManager.tile[tileNum1].slow == true || gp.tileManager.tile[tileNum2].slow == true) {
                entity.speed = 3;
            }
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
                if(gp.player.invincible == false) {
                    gp.player.health -= enemies.damage;
                    gp.player.invincible = true;
                }
            }
            else{
                enemies.playerCollision = false;
            }
        }
    }

}
