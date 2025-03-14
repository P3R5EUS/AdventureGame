package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
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
		
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}


	public void setDefaultValues() {
		worldX = gp.tileSize*23;
		worldY = gp.tileSize*23;

		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		
		//player status
		maxLife = 6;
		life = maxLife;
		level = 1;
		strength  = 1;
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 500;
		maxMana = 4;
		mana = maxMana;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);

		attack = getAttack();
		defense = getDefense();
		
	}
	
	public void setDefaultPositions() {
		worldX = gp.tileSize*23;
		worldY = gp.tileSize*21;
		speed = 4;
		direction = "down";

	}
	
	public void restoreLifeAndMana() {
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	
	public void setItems() {
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
		
	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		
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
			
			//check interactive tiles collision
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			
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
		
		
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this)) {
			//set default coordinates of projectile
			projectile.set(worldX,worldY,direction,true,this);
			projectile.subtractResource(this);
			shotAvailableCounter = 0;
			//add it to the list
			for(int i=0;i<gp.projectile[1].length;i++) {
				if(gp.projectile[gp.currentMap][i] == null) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}
			gp.playSE(9);
		}
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter >60) {
				invincible = false;
				invincibleCounter= 0;
			}
		}
		if(shotAvailableCounter<30) {
			shotAvailableCounter++;
		}
		
		if(life>maxLife) {
			life = maxLife; 
		}
		if(mana>maxMana) {
			mana = maxMana; 
		}
		if(life<=0) {
			gp.playSE(11);
			gp.ui.commandNum = -1;
			gp.stopMusic();
			gp.gameState = gp.gameoverState;
		}
	}
	
	public void contactMonster(int i) {
		if(i!=999) {
			gp.playSE(6);
			int damage = gp.monster[gp.currentMap][i].attack - defense;
			if(damage<0) {damage = 0;}
			
			if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
				life-=damage;
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
			
			//check monster collision with updates worldX, worldY and solid area
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex,attack,currentWeapon.knockBackPower);
			
			//attack collsiion of axe and interactive tiles
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			damageProjectile(projectileIndex);
			
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

	public void damageMonster(int monsterIndex , int attack, int knockBackPower) {
		if(monsterIndex !=999) {
			if(gp.monster[gp.currentMap][monsterIndex].invincible == false) {
				gp.playSE(5);
				if(knockBackPower >0) {
					knockBack(gp.monster[gp.currentMap][monsterIndex],knockBackPower);
				}
				int damage = attack - gp.monster[gp.currentMap][monsterIndex].defense;
				if(damage<0) {damage = 0;}
				gp.monster[gp.currentMap][monsterIndex].life-=damage;
				
				gp.ui.addMessage(damage + "damage !");
				
				gp.monster[gp.currentMap][monsterIndex].invincible = true;
				gp.monster[gp.currentMap][monsterIndex].damageReaction();
				if(gp.monster[gp.currentMap][monsterIndex].life <=0) {
					gp.monster[gp.currentMap][monsterIndex].dying = true;
					gp.ui.addMessage("killed the "+ gp.monster[gp.currentMap][monsterIndex].name+" !");
					exp+=gp.monster[gp.currentMap][monsterIndex].exp;
					gp.ui.addMessage("Exp : "+gp.monster[gp.currentMap][monsterIndex].exp + "gained");
					checkLevelUp();
				}
			}	
		}			
	}
	
	public void damageInteractiveTile(int i) {
		if(i!=999 && gp.iTile[gp.currentMap][i].destructible == true && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible==false) {
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			//generate particle
			generateParticle(gp.iTile[gp.currentMap][i],gp.iTile[gp.currentMap][i]);
			
			if(gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();

			}
		}
	}
	public void checkLevelUp() {
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp= nextLevelExp*3;
			maxLife+=2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(4);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are Level "+level+" now!\n"+"You Feel Stronger !";
		}
	}

	public void pickUpObject(int i) {
		if(i!=999) {
			
			//pickup only items
			if(gp.obj[gp.currentMap][i].type ==type_pickUpOnly) {
				
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i]= null;
			}
			
			// inventory items
			else{
				String text ;
			if(inventory.size()!= maxInventorySize) {
				inventory.add(gp.obj[gp.currentMap][i]);
				gp.playSE(1);
				text = "got a "+gp.obj[gp.currentMap][i].name + "!";
			}else {
				text = "You cannot carry any more objects";	
			}
			gp.ui.addMessage(text);
			gp.obj[gp.currentMap][i] = null;
			}	
		}
	}

	public void interactNPC(int i) {
		if(i!=999) {
			gp.gameState = gp.interactState;
			if(gp.keyH.EnterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][i].speak();
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
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1",gp.tileSize,2*gp.tileSize);
			attackUp2 = setup("/player/boy_attack_up_2",gp.tileSize,2*gp.tileSize);
			attackDown1 = setup("/player/boy_attack_down_1",gp.tileSize,2*gp.tileSize);
			attackDown2 = setup("/player/boy_attack_down_2",gp.tileSize,2*gp.tileSize);
			attackLeft1 = setup("/player/boy_attack_left_1",2*gp.tileSize,gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2",2*gp.tileSize,gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1",2*gp.tileSize,gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2",2*gp.tileSize,gp.tileSize);	
		}
		else if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1",gp.tileSize,2*gp.tileSize);
			attackUp2 = setup("/player/boy_axe_up_2",gp.tileSize,2*gp.tileSize);
			attackDown1 = setup("/player/boy_axe_down_1",gp.tileSize,2*gp.tileSize);
			attackDown2 = setup("/player/boy_axe_down_2",gp.tileSize,2*gp.tileSize);
			attackLeft1 = setup("/player/boy_axe_left_1",2*gp.tileSize,gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2",2*gp.tileSize,gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1",2*gp.tileSize,gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2",2*gp.tileSize,gp.tileSize);	
		}
		
	}
	
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerslotCol,gp.ui.playerslotRow);
		if(itemIndex<inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			if(selectedItem.type == type_axe || selectedItem.type == type_sword) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	
	public void damageProjectile(int i) {
		if(i!=999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile,projectile);
		}
	}
	
	public void knockBack(Entity entity,int knockBackPower) {
		entity.direction = direction;
		entity.speed +=knockBackPower;
		entity.knockBack = true;
	}

}