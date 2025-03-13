package main;

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
		int i = 0;
		gp.obj[i] = new OBJ_Coin_Bronze(gp);
		gp.obj[i].worldX = gp.tileSize*25;
		gp.obj[i].worldY = 19*gp.tileSize;
		i++;
		gp.obj[i] = new OBJ_Heart(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		gp.obj[i] = new OBJ_ManaCrystal(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = gp.tileSize*33;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Shield_Blue(gp);
		gp.obj[i].worldX = gp.tileSize*35;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Potion_Red(gp);
		gp.obj[i].worldX = gp.tileSize*22;
		gp.obj[i].worldY = gp.tileSize*27;
		i++;
	}

	public void setNPC() {
		gp.npc[0] = new NPC_Oldman(gp);
		gp.npc[0].worldX = 22*gp.tileSize;
		gp.npc[0].worldY = 21*gp.tileSize;

	}
	
	public void setMonster() {
		int i = 0;
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*21;
		gp.monster[i].worldY = 38*gp.tileSize;
		i++;
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*42;
		i++;
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*24;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*34;
		gp.monster[i].worldY = gp.tileSize*42;
		i++;
		gp.monster[i] = new MON_GreenSlime(gp);
		gp.monster[i].worldX = gp.tileSize*38;
		gp.monster[i].worldY = gp.tileSize*42;
	}
	
	public void setInteractiveTiles() {
		int i=0;
		gp.iTile[i] = new IT_Dry_Tree(gp,27,12);
		i++;
		
		gp.iTile[i] = new IT_Dry_Tree(gp,28,12);
		i++;
		
		gp.iTile[i] = new IT_Dry_Tree(gp,29,12);
		i++;
		
		gp.iTile[i] = new IT_Dry_Tree(gp,30,12);
		i++;
		
		gp.iTile[i] = new IT_Dry_Tree(gp,31,12);
		i++;
		
		gp.iTile[i] = new IT_Dry_Tree(gp,32,12);
		i++;
		
		gp.iTile[i] = new IT_Dry_Tree(gp,33,12);
		i++;
	}
}
