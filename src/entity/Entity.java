package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	public int worldX,worldY;
	public int speed;
	GamePanel gp;
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	int dialogueIndex = 0;
	public int actionLockCounter;
	String dialogues[] = new String[20];
	
	public BufferedImage image,image2,image3;
	public String name;
	public boolean collision = false;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public int type;
	// 0 = player , 1 = NPC , 2 = MONSTER
	
	//character status
	public int maxLife;
	public int life;
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	
	//loads image for each object and entity
	public BufferedImage setup(String imagepath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image =null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagepath+".png")); 
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void setAction() {

	}
	
	//updates the images and positions of entities
	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		
		if(this.type == 2 && contactPlayer == true) {
			if(gp.player.invincible == false) {
				gp.player.life--;
				gp.player.invincible = true;
			}
		}
		
		
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
	}
	
	//draws each entity and object at desired location
	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		BufferedImage image = null;

		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize < gp.player.worldX+gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

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
			case "none":
				image = down1;
				break;
			}

			g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);

		}

	}
	
	//gives the power of speech to the entities
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			return;
		}
		if(gp.gameState == gp.dialogueState) {
			gp.ui.currentDialogue = dialogues[dialogueIndex];
			dialogueIndex++;	
			
			switch(gp.player.direction) {
			case"up":direction = "down";break;
			case"down":direction = "up";break;
			case"left":direction = "right";break;
			case"right":direction = "left";break;
			}
		
		}

	}
}
