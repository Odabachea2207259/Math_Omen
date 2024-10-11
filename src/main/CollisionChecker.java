package main;

import entity.Entity;
import entity.Player;

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
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        entity.canDown = true;
        entity.canUp = true;
        entity.canLeft = true;
        entity.canRight = true;

//        switch(entity.direction){
//            case "up":
//                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
//                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
//                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
//
//                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
//                    entity.canUp = false;
//                }
//                break;
//            case "down":
//                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
//                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
//                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
//
//                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
//                    entity.canDown = false;
//                }
//
//                break;
//            case "left":
//                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
//                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
//                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
//
//                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
//                    entity.canLeft = false;
//                }
//
//                break;
//            case "right":
//                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
//                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
//                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
//
//                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
//                    entity.canRight = false;
//                }
//
//                break;
//        }

        if (entity.kh.upPressed) {
            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
            tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];

            if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                entity.canUp = false;
            }
        }

        if (entity.kh.downPressed) {

            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
            tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

            if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                entity.canDown = false;
            }
        }
            if(entity.kh.leftPressed) {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                    entity.canLeft = false;
                }

            }
            if(entity.kh.rightPressed) {
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
                    entity.canRight = false;
                }

            }


    }


}
