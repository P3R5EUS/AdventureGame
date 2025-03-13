package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
	GamePanel gp;

	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp=gp;
		
		type = type_pickUpOnly;
		name = "Bronze Coin";
		down1 = setup("/objects/coin_bronze",gp.tileSize,gp.tileSize);
		value = 2;
		
	}
	
	public void use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("Coin +"+value);
		gp.player.coin+=value;
	}
}
