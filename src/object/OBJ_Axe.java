package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		type = type_axe;
		name = "WoodCutter's Axe";
		down1 = setup("/objects/axe",gp.tileSize,gp.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "["+name+"]\nThis cuts trees";
		
		price = 15;
		knockBackPower = 10;
	}

}
