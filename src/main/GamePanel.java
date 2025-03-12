
package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.Color;
import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	//SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;

	public final int tileSize = originalTileSize*scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize*maxScreenCol;
	public final int screenHeight = tileSize*maxScreenRow;
	//world settings :-
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	//FPS
	int fps = 60;

	//SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	Sound sound = new Sound();
	public Player player = new Player(this,keyH);
	public EventHandler eHandler = new EventHandler(this);
	
	//entity and object
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10]; 
	ArrayList<Entity> entityList = new ArrayList<>();
	public Entity monster[] = new Entity[20];
	public ArrayList <Entity> projectileList = new ArrayList<>();
	
	//GAME STATE:-
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int interactState = 4;
	public final int characterState= 5;

	//pokemons
	public String pokename="";
	
	public UI ui = new UI(this);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		gameState = titleState; 
		
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {

		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta+= (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if(delta>=1) {
				// Update information like player position
				update();
				
				//draw the screen with updated info
				repaint();
				delta--;
				drawCount++;
			}

			if(timer>=1000000000) {
				//System.out.println("FPS : "+drawCount);
				drawCount = 0;
				timer = 0;
			}			
		}
	}
	public void update() {
		if(gameState == playState || gameState == interactState) {
			player.update();

			//NPC
			for(int i=0;i<npc.length;i++) {
				if(npc[i]!=null) {
					npc[i].update();
				}
			}
			
			for(int i=0;i<monster.length;i++) {
				if(monster[i]!=null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();	
					}
					else if (monster[i].alive == false){
						monster[i] = null;
					}
				}
			}
			
			for(int i=0;i<projectileList.size();i++) {
				if(projectileList.get(i)!=null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();	
					}
					else if (projectileList.get(i).alive == false){
						projectileList.remove(i);
					}
				}
			}
		}
		if(gameState == pauseState) {
			//nothing
		}
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		//debug
		long drawStart = 0;
		if(keyH.showDebugText == true) {
			drawStart = System.nanoTime();
		}
		
		//title screen
		if(gameState == titleState) {
			ui.draw(g2);
		}
		else {
			//tile draw
			tileM.draw(g2);
			
			//add entities to the list
			entityList.add(player);
			for(int i = 0;i<npc.length;i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0;i<obj.length;i++) {
				if(obj[i]!=null) {
					entityList.add(obj[i]);
				}
			}
			
			for(int i = 0;i<monster.length;i++) {
				if(monster[i]!=null) {
					entityList.add(monster[i]);
				}
			}
			
			for(int i = 0;i<projectileList.size();i++) {
				if(projectileList.get(i)!=null) {
					entityList.add(projectileList.get(i));
				}
			}
			
			
			//SORT usig worldY
			Collections.sort(entityList,new Comparator<Entity>() {
				
				@Override
				public int compare(Entity e1, Entity e2) {
					//compare entities by a specified condition				
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});
			
			//draw entities
			for(int i=0;i<entityList.size();i++) {
				entityList.get(i).draw(g2);
			}
			
			//empty entity list
			entityList.clear();
			
			//ui
			ui.draw(g2);
		}
		
		//debug
		if(keyH.showDebugText == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(new Font("Arial",Font.PLAIN,20));
			g2.setColor(Color.white);
			int x = 10;
			int y = 275;
			int lineHeight = 20;
			g2.drawString("WorldX : "+player.worldX, x, y);
			y+=lineHeight;
			g2.drawString("WorldY : "+player.worldY, x, y);
			y+=lineHeight;
			g2.drawString("Col : " +(player.worldX + player.solidArea.x)/tileSize , x, y);
			y+=lineHeight;
			g2.drawString("Row : " +(player.worldY + player.solidArea.y)/tileSize , x, y);
			y+=lineHeight;
			g2.drawString("Draw Time : " + passed,x,y);
		}
		g2.dispose();

	}

	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}

	public void stopMusic() {
		sound.stop();
	}

	public void playSE(int i) {
		sound.setFile(i);
		sound.play();
	}
}
