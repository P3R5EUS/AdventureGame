package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import entity.Entity;
import object.OBJ_Heart;

public class UI {
	GamePanel gp;
	Graphics2D g2;

	Font maruMonica;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0;  //0:1st screen , 1:2ns Screen
	
	BufferedImage heart_full, heart_half, heart_blank;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
		try {
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create HUD object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
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
		
		if(gp.gameState == gp.pokeState) {
			drawChoiceScreen();
		}
		
		if(gp.gameState == gp.fightState) {
			drawFightScreen();
		}
		
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
	
	public void drawChoiceScreen() {
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);
		
		//title name
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "Wild "+gp.pokename+" has appeared";
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
		text = "Run!";
		x = getXforCenteredText(text);
		y+=gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize, y);
		}
		
		text = "Fight / Catch";
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
	
	public void drawFightScreen(){
		
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
	
}
