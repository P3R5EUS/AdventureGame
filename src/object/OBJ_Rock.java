package object;

import java.awt.Color;

import entity.Player;
import entity.Projectiles;
import main.GamePanel;

public class OBJ_Rock extends Projectiles{
	GamePanel gp;
	
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Rock";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
		
	}
	public void getImage() {
		up1 = setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
		up2 = setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
		down1 = setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
		left1= setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
		left2 = setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
		right1  = setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
		right2 = setup("/projectiles/rock_down_1",gp.tileSize,gp.tileSize);
	}
	
	public boolean haveResource(Player player) {
		boolean haveResource = false;
		if(player.ammo>=useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Player player) {
		player.ammo -= useCost;
	}
	
	public Color getParticleColor() {
		Color color = new Color(40,50,0);
		return color;
	}
	
	
	public int getparticleSize() {
		int size = 6;
		return size;
	}
	public int getParticleSpeed() {
		int speed =1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
