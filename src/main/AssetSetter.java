package main;

import entity.NPC_Oldman;
import monster.MON_GreenSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObject() {

	}

	public void setNPC() {
		gp.npc[0] = new NPC_Oldman(gp);
		gp.npc[0].worldX = 22*gp.tileSize;
		gp.npc[0].worldY = 21*gp.tileSize;

	}
	
	public void setMonster() {
		gp.monster[0] = new MON_GreenSlime(gp);
		gp.monster[0].worldX = gp.tileSize*23;
		gp.monster[0].worldY = 36*gp.tileSize;
		
		gp.monster[1] = new MON_GreenSlime(gp);
		gp.monster[1].worldX = gp.tileSize*23;
		gp.monster[1].worldY = 37*gp.tileSize;
	}
}
