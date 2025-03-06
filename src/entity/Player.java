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
		setDefaultValues();
		getPlayerImage();
	}





	public void setDefaultValues() {
		worldX = gp.tileSize*23;
		worldY = gp.tileSize*21;
		speed = 4;
		direction = "down";
		
		//player status
		maxLife = 10;
		life = maxLife;
	}

	public void update() {

		if(moving == false) {

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
				invincible = true;
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
	}
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		switch(direction) {
		case "up":
			if (spriteNum == 1)
				image = up1;
			if(spriteNum ==2)
				image=up2;
			break;
		case "down":
			if (spriteNum == 1)
				image = down1;
			if(spriteNum ==2)
				image=down2;
			break;
		case "left":
			if (spriteNum == 1)
				image = left1;
			if(spriteNum ==2)
				image=left2;
			break;
		case "right":
			if (spriteNum == 1)
				image = right1;
			if(spriteNum ==2)
				image=right2;
			break;
		}

		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image, screenX,screenY,null);
		//reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


	}

	public void getPlayerImage() {
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");	
	}

}
