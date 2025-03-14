package object;

import java.awt.Color;

import entity.Entity;
import entity.Player;
import entity.Projectiles;
import main.GamePanel;

public class OBJ_Fireball extends Projectiles{
	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		knockBackPower = 0;
		getImage();
		
	}
	public void getImage() {
		up1 = setup("/projectiles/fireball_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/projectiles/fireball_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/projectiles/fireball_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/projectiles/fireball_down_2",gp.tileSize,gp.tileSize);
		left1= setup("/projectiles/fireball_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/projectiles/fireball_left_2",gp.tileSize,gp.tileSize);
		right1  = setup("/projectiles/fireball_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/projectiles/fireball_right_2",gp.tileSize,gp.tileSize);
	}
	
	public boolean haveResource(Player player) {
		boolean haveResource = false;
		if(player.mana>=useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	
	public void subtractResource(Player player) {
		player.mana -= useCost;
	}
	
	public Color getParticleColor() {
		Color color = new Color(240,50,0);
		return color;
	}
	
	public int getparticleSize() {
		int size = 10;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
