package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_Oldman extends Entity {
	public NPC_Oldman(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		getImage();
		setDialogue();
	}

	public void getImage() {
		up1 = setup("/npc/oldman_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/npc/oldman_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/npc/oldman_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/npc/oldman_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/npc/oldman_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/npc/oldman_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/npc/oldman_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/npc/oldman_right_2",gp.tileSize,gp.tileSize);	
	}

	public void setAction() {

		actionLockCounter++;
		if(actionLockCounter >= 120) {
			Random random = new Random();
			int i= random.nextInt(100)+1; //pick a random number from 1-100

			if(i<=25) {
				direction = "up";
			}
			if(i > 25 && i<=50) {
				direction = "down";
			}
			if (i > 50 && i <=75) {
				direction = "left";
			}
			if(i>75 && i<=100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}
	
	public void setDialogue() {
		dialogues[0] = "Hello, lad."; 
		dialogues[1] = "So you have come to this island \nin search of a pokemon ?."; 
		dialogues[2] = "I used to be a scientist who was \nintriguied by these cute little creatures."; 
		dialogues[3] = "Wait, Here , Select your \n1st pokemon from among these :-"; 
		dialogues[4] = "-";
	}
	
	public void speak() {		
		super.speak();
	}
}