package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x+entity.solidArea.width;

		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y+entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow= entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1,tileNum2;
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];

			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true ) {
				entity.collisionOn = true;
			}

			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true ) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol-1][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol-1][entityBottomRow];

			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true ) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol= (entityRightWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol+1][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol+1][entityBottomRow];

			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision ==true ) {
				entity.collisionOn = true;
			}
			break;
		}
	}

	public int checkObject(Entity entity, boolean player) {
		int index  = 999;

		for(int i = 0;i<gp.obj[1].length;i++) {
			if(gp.obj[gp.currentMap][i] != null) {
				//get entity's solid area position
				entity.solidArea.x += entity.worldX ; 
				entity.solidArea.y += entity.worldY;

				//objects:-
				gp.obj[gp.currentMap][i].solidArea.x += gp.obj[gp.currentMap][i].worldX;
				gp.obj[gp.currentMap][i].solidArea.y += gp.obj[gp.currentMap][i].worldY;

				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed;
				break;
				case "down":entity.solidArea.y += entity.speed;
				break;
				case "left":entity.solidArea.x -= entity.speed;
				break;
				case "right":entity.solidArea.x += entity.speed;
				break;
				}

				if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
					if(gp.obj[gp.currentMap][i].collision == true) {
						entity.collisionOn =true;
					}
					if(player == true) {
						index = i;
					}
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y =entity.solidAreaDefaultY;
				gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
				gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	//npc or monster collision
	public int checkEntity(Entity entity, Entity[][] target) {
		int index  = 999;

		for(int i = 0;i<target[1].length;i++) {
			if(target[gp.currentMap][i] != null) {
				//get entity's solid area position
				entity.solidArea.x += entity.worldX ; 
				entity.solidArea.y += entity.worldY;

				//objects:-
				target[gp.currentMap][i].solidArea.x += target[gp.currentMap][i].worldX;
				target[gp.currentMap][i].solidArea.y += target[gp.currentMap][i].worldY;

				switch(entity.direction) {
				case "up": entity.solidArea.y -= entity.speed;
				break;
				case "down":entity.solidArea.y += entity.speed;				
				break;
				case "left":entity.solidArea.x -= entity.speed;
				break;
				case "right":entity.solidArea.x += entity.speed;
				break;
				}
				if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
					if(target[gp.currentMap][i]!=entity) {
						entity.collisionOn = true;   
						index = i;	
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y =entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		//get entity's solid area position
		entity.solidArea.x += entity.worldX ; 
		entity.solidArea.y += entity.worldY;

		//objects:-
		gp.player.solidArea.x += gp.player.worldX;
		gp.player.solidArea.y += gp.player.worldY;

		switch(entity.direction) {
		case "up": entity.solidArea.y -= entity.speed;
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true; 
			contactPlayer= true;
			entity.worldY += entity.speed;
		}
		break;
		case "down":entity.solidArea.y += entity.speed;
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;   
			contactPlayer= true;
			entity.worldY -= entity.speed;
		}
		break;
		case "left":entity.solidArea.x -= entity.speed;
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer= true;
			entity.worldX += entity.speed;
		}
		break;
		case "right":entity.solidArea.x += entity.speed;
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer= true;
			entity.worldX -= entity.speed;
		}
		break;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y =entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}
}
