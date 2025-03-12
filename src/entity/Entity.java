package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	
	//charcter attributes
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	public int level;
	public int ammo;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public int maxMana;
	public int mana;
	public Projectiles projectile;
	

	//ITEm ATTRIBUTES
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	public int value;
	
	public int worldX,worldY;
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
	public Rectangle attackArea = new Rectangle(0,0,0,0);
	
	public BufferedImage image,image2,image3;
	public boolean collision = false;
	public boolean invincible = false;
	boolean attacking  = false;
	public int invincibleCounter = 0;
	boolean hpBarOn = false;
	int hpBarCounter = 0;
	public int shotAvailableCounter = 0;
	//TYPE
	public int type;// 0 = player , 1 = NPC , 2 = MONSTER
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickUpOnly = 7;
	//character status
	public boolean alive = true;
	public boolean dying =false;
	int dyingCounter = 0;
	public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	
	//loads image for each object and entity
	public BufferedImage setup(String imagepath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image =null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagepath+".png")); 
			image = uTool.scaleImage(image, width,height);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void setAction() {

	}
	
	public void damageReaction() {
		
	}
	
	public void checkDrop() {
		
	}
	
	public void dropItem(Entity droppedItem) {
		for(int i = 0;i<gp.obj.length;i++) {
			if(gp.obj[i]==null) {
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX;
				gp.obj[i].worldY = worldY;
				break;
			}
		}
	}
	public void damagePlayer(int attack) {
		if(gp.player.invincible == false) {
			gp.player.life--;
			gp.playSE(6);
			
			int damage = attack - gp.player.defense;
			if(damage<0) {damage = 0;}
			gp.player.life-=damage;
		
			gp.player.invincible = true;
		}
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
		
		if(this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
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
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter >40) {
				invincible = false;
				invincibleCounter= 0;
			}
		}
		
		if(shotAvailableCounter<30) {
			shotAvailableCounter++;
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
			
			//monster hp bar
			if(type ==type_monster && hpBarOn == true) {
				double onScale = (double)(gp.tileSize/maxLife);
				double hpBarValue = onScale*life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX - 1, screenY - 16, gp.tileSize+2, 12);
				g2.setColor(new Color(255,0,30));
				g2.fillRect(screenX,screenY - 15,(int)hpBarValue,10);	
				
				hpBarCounter++;
				if(hpBarCounter >600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			if(invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2,0.5f);
			}
			if(dying == true) {
				dyingAnimation(g2);
			}
			g2.drawImage(image,screenX,screenY,null);
			changeAlpha(g2,1F);
		}

	}
	
	public void changeAlpha(Graphics2D g2,float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++ ;
		
		int i = 5;
		if(dyingCounter <=i) {changeAlpha(g2,0f);}
		if(dyingCounter >i && dyingCounter<=i*2) {changeAlpha(g2,1f);}
		if(dyingCounter >i*2 && dyingCounter<=i*3) {changeAlpha(g2,0f);}
		if(dyingCounter >i*3 && dyingCounter<=i*4) {changeAlpha(g2,1f);}
		if(dyingCounter >i*4 && dyingCounter<=i*5) {changeAlpha(g2,0f);}
		if(dyingCounter >i*5 && dyingCounter<=i*6) {changeAlpha(g2,1f);}
		if(dyingCounter > i*6) {
			alive = false;
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
	
	public void use(Entity entity) {}
}