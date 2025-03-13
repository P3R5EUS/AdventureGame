package main;

import entity.NPC_Merchant;
import entity.NPC_Oldman;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_Dry_Tree;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*25;
		gp.obj[mapNum][i].worldY = 19*gp.tileSize;
		i++;
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*33;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*35;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*27;
		i++;
	}

	public void setNPC() {
		int mapNum = 0;
		int i = 0;
		gp.npc[mapNum][i] = new NPC_Oldman(gp);
		gp.npc[mapNum][i].worldX = 22*gp.tileSize;
		gp.npc[mapNum][i].worldY = 21*gp.tileSize;
		i++;
		
		mapNum = 1;
		i=0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = 12*gp.tileSize;
		gp.npc[mapNum][i].worldY = 7*gp.tileSize;
		i++;
	}
	
	public void setMonster() {
		int mapNum = 0;

		int i = 0;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*21;
		gp.monster[mapNum][i].worldY = 38*gp.tileSize;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*42;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*24;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*34;
		gp.monster[mapNum][i].worldY = gp.tileSize*42;
		i++;
		gp.monster[mapNum][i] = new MON_GreenSlime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*38;
		gp.monster[mapNum][i].worldY = gp.tileSize*42;
	}
	
	public void setInteractiveTiles() {
		int mapNum = 0;

		int i=0;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,27,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,28,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,29,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,30,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,31,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,32,12);
		i++;
		
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,33,12);
		i++;
		
		
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,13,40);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,14,40);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,15,40);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,16,40);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,17,40);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,18,40);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,13,41);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,12,41);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,11,41);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,10,41);
		i++;
		gp.iTile[mapNum][i] = new IT_Dry_Tree(gp,10,40);
		i++;
		
	}
}
