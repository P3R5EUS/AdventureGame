package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UI;
import main.UtilityTool;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity{
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	int standCounter = 0;
	boolean moving = false;
	int pixelCounter = 0;

	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		solidArea = new Rectangle(1,1,46,46);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}





	public void setDefaultValues() {
		worldX = gp.tileSize*23;
		worldY = gp.tileSize*21;
		speed = 4;
		direction = "down";
		
		//player status
		maxLife = 10;
		life = maxLife;
		level = 1;
		strength  = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		attack = getAttack();
		defense = getDefense();
	}
	
	public int getAttack() {
		return attack = strength*currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity*currentShield.defenseValue;
	}
	public void update() {

		if(attacking == true) {
			
			attacking();
			
		}
		else if(moving == false) {

			if(keyH.UpPressed==true || keyH.DownPressed==true||keyH.LeftPressed==true || keyH.RightPressed==true) {
				if(keyH.UpPressed == true) {
					direction = "up";
				}
				else if(keyH.DownPressed == true) {
					direction = "down";
				}
				else if(keyH.RightPressed == true) {
					direction = "right";
				}
				else if(keyH.LeftPressed == true) {
					direction = "left";
				}

				moving = true;

				collisionOn = false;
				gp.cChecker.checkTile(this);

				//check object collision

				int objIndex = gp.cChecker.checkObject(this,true);
				pickUpObject(objIndex);
			}
			else {
				standCounter ++;

				if(standCounter >=20) {
					spriteNum = 1;
					standCounter = 0;
				}
			}
			
			//NPC COLLISION
			
			int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
			interactNPC(npcIndex);
			if(gp.gameState == gp.interactState && npcIndex == 999) {
				gp.gameState = gp.playState;
			}
			
			//check monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			//CHECK EVENT
			gp.eHandler.checkEvent();
			gp.keyH.EnterPressed = false;

			
		}


		if(moving == true) {
			//if collision is false player can move 

			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY-=speed;break;
				case "down":
					worldY+=speed;break;
				case "left":
					worldX-=speed;break;
				case "right":
					worldX+=speed;break;
				}
			}

			spriteCounter++;

			if(spriteCounter>5) {
				if(spriteNum ==1)
					spriteNum=2;
				else if(spriteNum ==2)
					spriteNum= 1;

				spriteCounter = 0;
			}
			pixelCounter += speed;
			if(pixelCounter>=48) {
				moving = false;
				pixelCounter = 0;
			}
		} 
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter >60) {
				invincible = false;
				invincibleCounter= 0;
			}
		}
	}
	
	public void contactMonster(int i) {
		if(i!=999) {
			if(invincible == false) {
				life--;
				gp.playSE(6);
				invincible = true;
			}
		}
	}
	
	public void attacking() {
		spriteCounter++;
		if(spriteCounter <=5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <=25) {
			spriteNum = 2;
			
			//save the current worldX,worldY,solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//adjust plahyer's worldX/Y for the attack area
			switch(direction) {
			case "up": worldY-=attackArea.height;break;
			case "down": worldY+=attackArea.height;break;
			case "left": worldX-=attackArea.width;break;
			case "right": worldX+=attackArea.width;break;
			}
			
			//attackarea becoes solidarea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			//check monsgter collision with updates worldX, worldY and solid area
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			//after checking restore original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}

	public void damageMonster(int monsterIndex) {
		if(monsterIndex !=999) {
			if(gp.monster[monsterIndex].invincible == false) {
				gp.monster[monsterIndex].life--;
				gp.playSE(5);
				gp.monster[monsterIndex].invincible = true;
				gp.monster[monsterIndex].damageReaction();
				if(gp.monster[monsterIndex].life <=0) {
					gp.monster[monsterIndex].dying = true;
				}
			}	
		}
	}





	public void pickUpObject(int i) {
		if(i!=999) {

		}
	}

	public void interactNPC(int i) {
		if(i!=999) {
			gp.gameState = gp.interactState;
			if(gp.keyH.EnterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}			
		}
		else {
			if(gp.keyH.EnterPressed == true) {
				gp.playSE(7);
				attacking = true;
			}
		}
	}
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "up":
			if(attacking == false) {
				if (spriteNum == 1)	image = up1;
				if(spriteNum ==2)image=up2;
			}
			else {
				tempScreenY -= gp.tileSize;
				if (spriteNum == 1)	image = attackUp1;
				if(spriteNum ==2)image=attackUp2;
			}
			break;
		case "down":
			if(attacking == false) {
				if (spriteNum == 1)	image = down1;
				if(spriteNum ==2)image=down2;	
			}
			else {
				if (spriteNum == 1)	image = attackDown1;
				if(spriteNum ==2)image=attackDown2;
			}
			break;
		case "left":
			if(!attacking) {
				if (spriteNum == 1)	image = left1;
				if(spriteNum ==2)image=left2;	
			}
			else {
				tempScreenX -= gp.tileSize;
				if (spriteNum == 1)	image = attackLeft1;
				if(spriteNum ==2)image=attackLeft2;
			}
			break;
		case "right":
			if(!attacking) {
				if (spriteNum == 1)	image = right1;
				if(spriteNum ==2)image=right2;
			}
			else {
				if (spriteNum == 1)	image = attackRight1;
				if(spriteNum ==2)image=attackRight2;
			}
			break;
		}

		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image, tempScreenX,tempScreenY,null);
		//reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


	}

	public void getPlayerImage() {
		up1 = setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
		up2 = setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
		down1 = setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
		down2 = setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
		left1 = setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
		left2 = setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
		right1 = setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
		right2 = setup("/player/boy_right_2",gp.tileSize,gp.tileSize);	
	}
	
	public void getPlayerAttackImage() {
		attackUp1 = setup("/player/boy_attack_up_1",gp.tileSize,2*gp.tileSize);
		attackUp2 = setup("/player/boy_attack_up_2",gp.tileSize,2*gp.tileSize);
		attackDown1 = setup("/player/boy_attack_down_1",gp.tileSize,2*gp.tileSize);
		attackDown2 = setup("/player/boy_attack_down_2",gp.tileSize,2*gp.tileSize);
		attackLeft1 = setup("/player/boy_attack_left_1",2*gp.tileSize,gp.tileSize);
		attackLeft2 = setup("/player/boy_attack_left_2",2*gp.tileSize,gp.tileSize);
		attackRight1 = setup("/player/boy_attack_right_1",2*gp.tileSize,gp.tileSize);
		attackRight2 = setup("/player/boy_attack_right_2",2*gp.tileSize,gp.tileSize);
	}

}
