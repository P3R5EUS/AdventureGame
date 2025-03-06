package main;

import java.awt.Dimension;
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
	
	//GAME STATE:-
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int interactState = 4;
	public final int pokeState = 5;
	public final int fightState = 6;

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
		int time = 1;

		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta+= (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if(delta>=1) {
				// Update information like player position
				if(gameState == playState) {
					time++;
				}
				update();
				
				//
				if(time%600==0) {
					time++;
					gameState = pokeState;
					
					String pokenames[] = {"1","2","3","4"};
					pokename = pokenames[(int)(Math.random()*4)];
					
				}
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
					monster[i].update();
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
			for(int i=0;i<entityList.size();i++) {
				entityList.remove(i);
			}
			
			//ui
			ui.draw(g2);
		}

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
