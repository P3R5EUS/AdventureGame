package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class UI {
	GamePanel gp;
	Graphics2D g2;

	Font maruMonica;
	public boolean messageOn = false;
	
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;  //0:1st screen , 1:2ns Screen
	
	BufferedImage heart_full, heart_half, heart_blank,crystal_full,crystal_blank;
	
	public int slotCol = 0;
	public int slotRow = 0;
	
	//substates
	int substate = 0;
	
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		//load font
		InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
		try {
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		//create HUD object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		Entity crystal= new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
	}

	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		g2.setFont(maruMonica);
		g2.setColor(Color.WHITE);
		
		//title state
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		//playstate
		if(gp.gameState == gp.playState) {
			//do playstate stuff
			drawPlayerLife();
			drawMessage();
		}
		if(gp.gameState == gp.interactState) {
			drawPlayerLife();
			drawInteractButton();
		}
		
		//pause state
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		
		//dialoguestate
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		//characterState
		if(gp.gameState == gp.characterState) {
			drawPlayerLife();
			drawCharacterScreen();
			drawInventory();
		}
		
		//options State
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		if(gp.gameState == gp.gameoverState) {
			drawGameOverScreen();
		}
		
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));
		
		text = "Game Over";
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		
		g2.setColor(Color.white);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x-4, y-4);
		
		
		//retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y+=gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum==0) {
			g2.drawString(">", x-40, y);
		}
		//back to title screen
		text = "Quit";
		x = getXforCenteredText(text);
		y+=55;
		g2.drawString(text, x, y);
		if(commandNum==1) {
			g2.drawString(">", x-40, y);
		}
	}
	
 	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		//subwindow
		int frameX = 6*gp.tileSize;
		int frameY = gp.tileSize;
		int frameWidth = 8*gp.tileSize;
		int frameHeight = 10*gp.tileSize;
		
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		switch(substate) {
		case 0:options_top(frameX,frameY);break;
		case 1:options_fullScreenNotification(frameX,frameY);break;
		case 2:options_control(frameX,frameY);break;
		case 3:options_endGameConfirmation(frameX,frameY);break;
		}
		
		gp.keyH.EnterPressed =false;
	}
	
	public void options_top(int frameX, int frameY) {
		int textX;
		int textY;
		
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY+gp.tileSize;
		g2.drawString(text, textX, textY);
		
		//Full Screen On Off
		textX = frameX + gp.tileSize;
		textY += 2*gp.tileSize;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.EnterPressed == true) {
				if(gp.fullScreenOn == false)
					gp.fullScreenOn = true;
				else
					gp.fullScreenOn = false;
				
				substate = 1;
			}
		}
		
		//music
		textY+=gp.tileSize;
		g2.drawString("Music",textX,textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		
		//sound
		textY+=gp.tileSize;
		g2.drawString("SE",textX,textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		
		//control
		textY+=gp.tileSize;
		g2.drawString("Control",textX,textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.EnterPressed == true) {
				commandNum = 0;
				substate = 2;
			}
		}
		
		//end game
		textY+=gp.tileSize;
		g2.drawString("End Game",textX,textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.EnterPressed == true) {
				commandNum = 0;
				substate =3;
			}
		}
		
		//back
		textY+=gp.tileSize*2;
		g2.drawString("Back",textX,textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.EnterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		
		
		//right side 
		//full screen check box
		textX = frameX + (gp.tileSize*9)/2;
		textY = frameY + (gp.tileSize*5)/2;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY,24,24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		//music
		textY+=gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24*gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		//SE
		textY+=gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24*gp.sound.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig();
	}
	
	public void options_endGameConfirmation(int frameX,int frameY) {
		int textX = frameX+gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		String currentDialogue = "Quit the game and \nreturn to the Title Screen?";
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		
		//YES
		String  text = "YES";
		textX = getXforCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum ==0) {
			g2.drawString(">",textX-25,textY);
			if(gp.keyH.EnterPressed == true) {
				substate = 0;
				gp.gameState = gp.titleState;
				gp.stopMusic();
			}
		}
		//NO
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum ==1) {
			g2.drawString(">",textX-25,textY);
			if(gp.keyH.EnterPressed == true) {
				substate = 0;
				commandNum = 4;
			}
		}
	}
	
	
	public void options_control(int frameX, int frameY) {
		int textX;
		int textY;
		
		String text = "Controls";
		textX = getXforCenteredText(text);
		textY = frameY+gp.tileSize;
		g2.drawString(text, textX, textY);textY+=gp.tileSize;
		textX = frameX + gp.tileSize/2;
		g2.drawString("Move", textX, textY);textY+=gp.tileSize;
		g2.drawString("Confirm / Attack", textX, textY);textY+=gp.tileSize;
		g2.drawString("Shoot / Cast", textX, textY);textY+=gp.tileSize;
		g2.drawString("Character Screen", textX, textY);textY+=gp.tileSize;
		g2.drawString("Pause", textX, textY);textY+=gp.tileSize;
		g2.drawString("Options", textX, textY);textY+=gp.tileSize;
	
		
		textX = frameX + 11*gp.tileSize/2;
		textY = frameY+gp.tileSize*2;
		g2.drawString("W/A/S/D", textX, textY);textY+=gp.tileSize;
		g2.drawString("ENTER", textX, textY);textY+=gp.tileSize;
		g2.drawString("F", textX, textY);textY+=gp.tileSize;
		g2.drawString("C", textX, textY);textY+=gp.tileSize;
		g2.drawString("P", textX, textY);textY+=gp.tileSize;
		g2.drawString("ESC", textX, textY);textY+=gp.tileSize;
	
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum ==0) {
			g2.drawString(">",textX-25,textY);
			if(gp.keyH.EnterPressed == true) {
				substate = 0;
				commandNum = 3;
			}
		}
	
	}
	public void options_fullScreenNotification(int frameX,int frameY) {
		int textX = frameX + gp.tileSize;
		int textY = frameY + 3*gp.tileSize;
		
		currentDialogue = "The change will take \neffect after restarting \nthe game.";
		for(String line:currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		
		//back
		textY= frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.EnterPressed == true)
					substate= 0;
		}
	}
	public void drawInventory() {
		int frameX = gp.tileSize*12;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);

		//SLOTS
		final int slotXstart = frameX + 20;
		final int slotYstart = frameY + 20;
		int slotX = slotXstart;
		int slotY = slotYstart;
		int slotSize = gp.tileSize+3;
			
		//draw player items
		for(int i = 0;i<gp.player.inventory.size();i++) {
			
			//EQUIP CURSOR
			if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i)==gp.player.currentShield) {
				g2.setColor(new Color(240,190,90));
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10,10);
			}
			g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);
			slotX += slotSize;
			
			if(i%5==4&&i!=0) {
				slotX = slotXstart;
				slotY+=slotSize;
			}
		}
		
		
		//cursor
		int cursorX = slotXstart + (slotSize*slotCol);
		int cursorY = slotYstart + (slotSize*slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		//draw cursor
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		//description frame
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gp.tileSize*3;
		//drawdescription text
		int textX = dFrameX +20;
		int textY = dFrameY + gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(28F));
		int itemIndex = getItemIndexOnSlot();
		if(itemIndex<gp.player.inventory.size()) {
			drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

			for(String line:gp.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line,textX, textY);
				textY+=32;
			}
		}
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	public void drawMessage() {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		
		for(int i = 0;i<message.size();i++) {
			if(message.get(i)!=null) {
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				//messagecounter++;
				
				//adds to message counter
				int counter = messageCounter.get(i)+1;
				messageCounter.set(i, counter);
				
				messageY+=50;
				
				//removes from message counter
				if(messageCounter.get(i)>180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	
	private void drawCharacterScreen() {
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*11;
		
		drawSubWindow(frameX,frameY,frameWidth,frameHeight);
		
		//text
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int textX = frameX + 20 ;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 37;
		int tailX = (frameX+frameWidth) - 30;
		String value;
		int tailEnd = 0;
		
		//names
		value = String.valueOf(gp.player.level);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Level", textX, textY);
		textY+=lineHeight;
		
		
		value = String.valueOf(gp.player.life)+" / "+String.valueOf(gp.player.maxLife);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Life", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.mana)+" / "+String.valueOf(gp.player.maxMana);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Mana", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.strength);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Strength", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.dexterity);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Dexterity", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.attack);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Attack", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.defense);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Defense", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.exp);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Exp", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.nextLevelExp);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Next Level", textX, textY);
		textY+=lineHeight;
		
		value = String.valueOf(gp.player.coin);
		tailEnd = getXforAlignToRightText(value,tailX);
		g2.drawString(value, tailEnd, textY);
		g2.drawString("Coin", textX, textY);
		textY+=lineHeight-25;
		
		g2.drawImage(gp.player.currentWeapon.down1,tailX - gp.tileSize,textY,null );
		textY+=40;
		g2.drawString("Weapon", textX, textY);
		textY+=lineHeight-25;
		
		g2.drawImage(gp.player.currentShield.down1,tailX - gp.tileSize,textY+8,null );
		textY+=45;
		g2.drawString("Shield", textX, textY);
			
	}

	public void drawPlayerLife() {
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		int i = 0;
		
		while(i<gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x+=gp.tileSize;
		}
		
		//reset;
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		//actual health bar
		while(i<gp.player.life) {
			g2.drawImage(heart_half,x,y,null);
			i++;
			if(i<gp.player.life) {
				g2.drawImage(heart_full,x,y,null);
			}
			x+=gp.tileSize;
			i++;
		}
		
		//draw mana crystal
		x = gp.tileSize/2;
		y = (3*gp.tileSize)/2;
		i = 0;
		while(i<gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x+=gp.tileSize/1.5;
		}
		
		//reset
		x = gp.tileSize/2;
		y = (3*gp.tileSize)/2;
		i = 0;
		while(i<gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x+=gp.tileSize/1.5;
		}
		
	}
	
	public void drawTitleScreen() {
		
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
		
		//title name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "Pokemon Adventure";
		int x = getXforCenteredText(text);
		int y = 3*gp.tileSize;
		
		//shadow
		g2.setColor(Color.darkGray);
		g2.drawString(text,x+5,y+5);
		
		//main color
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		//blueboyimage
		x = gp.screenWidth/2 - (gp.tileSize);
		y += gp.tileSize*2;
		g2.drawImage(gp.player.down1, x, y,2*gp.tileSize,2*gp.tileSize,null);
	
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		text = "NEW GAME";
		x = getXforCenteredText(text);
		y+=gp.tileSize*4;
		g2.drawString(text, x, y);
		
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text);
		y+=gp.tileSize;
		g2.drawString(text, x, y);
		
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		text = "QUIT";
		x = getXforCenteredText(text);
		y+=gp.tileSize;
		g2.drawString(text, x, y);
		
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
	}
		
	public void drawDialogueScreen() {
		if (currentDialogue.equals("-")) {
			gp.gameState = gp.playState;
			return;
		}
		//window
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width  = gp.screenWidth - (4*gp.tileSize);
		int height= gp.tileSize*4;
		
		drawSubWindow(x,y,width,height);
		
		g2.setFont(maruMonica.deriveFont(Font.PLAIN,32F));
		x += gp.tileSize;
		y+=gp.tileSize;
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y+=40;
		}
	}
	
	public void drawInteractButton() {
		if (currentDialogue.equals("-")) {
			gp.gameState = gp.playState;
			return;
		}
		int x = gp.tileSize*(8);
		int y = gp.tileSize*9;
		int width = 7*gp.tileSize;
		int height = gp.tileSize;
		
		drawSubWindow(x,y,width,height);

		g2.setFont(maruMonica.deriveFont(Font.PLAIN,32F));
		y+=0.7*gp.tileSize;
		x+=0.5*gp.tileSize;
		String text = "Press ENTER to interact !";
		g2.drawString(text,x,y);
	}
	
	public void drawSubWindow(int x , int y , int width , int height) {
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	
	public void drawPauseScreen() {
		g2.setFont(maruMonica.deriveFont(Font.PLAIN,80F));

		String text = "PAUSED";
		int y = gp.screenHeight/2;
		int x = getXforCenteredText(text);
				
		g2.drawString(text, x, y);
		
	}

	public int getXforCenteredText(String text) {
		int length= (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
		return( (gp.screenWidth/2) - (length/2));

	}
	
	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}
