package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
	GamePanel gp;
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		name = "Red Potion";
		down1 = setup("/objects/potion_red",gp.tileSize,gp.tileSize);
		value = 5;
		description = "["+name+"]\nHeals your life by "+value+".";
		price = 30;
	}
	
	
	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You Drank the "+name+"\nYour life has been recovered by "+value+".";
		entity.life+=value;
		if(gp.player.life>gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
	}

}
