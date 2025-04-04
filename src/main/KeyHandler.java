package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	public boolean UpPressed , DownPressed , RightPressed , LeftPressed,EnterPressed,shotKeyPressed;
	boolean showDebugText = false;
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		//TITLE STATE
		if(gp.gameState == gp.titleState) { titleState(code);}
		//Game state
		else if(gp.gameState == gp.playState || gp.gameState == gp.interactState){ playState(code);}
		//Pause State
		else if(gp.gameState == gp.pauseState) { pauseState(code);}
		//Dialogue State
		else if(gp.gameState == gp.dialogueState) {dialogueState(code);}	
		//character state
		else if(gp.gameState == gp.characterState) {characterState(code);}
		//options State
		else if(gp.gameState == gp.optionsState) {optionsState(code);}
		//gameover State
		else if(gp.gameState == gp.gameoverState) {gameoverState(code);}
		//trade State
		else if(gp.gameState == gp.tradeState) {tradeState(code);}
	}
	
	public void tradeState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			EnterPressed = true;
		}
		if(gp.ui.substate == 0) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum = (gp.ui.commandNum+2)%3;
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum = (gp.ui.commandNum+1)%3;
			}
			gp.playSE(8);
		}
		
		if(gp.ui.substate == 1) {
			npcInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.substate = 0;
			}
		}
		if(gp.ui.substate == 2) {
			playerInventory(code);
			if(code == KeyEvent.VK_ESCAPE) {
				gp.ui.substate = 0;
			}
		}
		
	}
	public void gameoverState(int code) {
		if(code == KeyEvent.VK_S) {
			gp.playSE(8);
			gp.ui.commandNum= (gp.ui.commandNum+1)%2;
		}
		if(code == KeyEvent.VK_W) {
			gp.playSE(8);
			gp.ui.commandNum = (gp.ui.commandNum+1)%2;
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
			}
			else if(gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.stopMusic();
				gp.restart();
			}
		}
	}
	public void optionsState(int code) {
		int maxCN = 2;
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code== KeyEvent.VK_ENTER) {
			EnterPressed = true;
		}
		switch(gp.ui.substate) {
		case 0:maxCN = 6;break;
		case 3:maxCN = 2;break;
		}
		if(code == KeyEvent.VK_S) {
			gp.playSE(8);
			gp.ui.commandNum= (gp.ui.commandNum+1)%maxCN;
		}
		if(code == KeyEvent.VK_W) {
			gp.playSE(8);
			gp.ui.commandNum = (gp.ui.commandNum+maxCN-1)%maxCN;
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.substate == 0) {
				if(gp.ui.commandNum==1) {
					gp.music.volumeScale = (gp.music.volumeScale+5)%6;
					gp.music.checkVolume();
					gp.playSE(8);
				}
				if(gp.ui.commandNum==2) {
					gp.sound.volumeScale = (gp.sound.volumeScale+5)%6;
					gp.playSE(8);
				}
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.substate == 0) {
				if(gp.ui.commandNum==1) {
					gp.music.volumeScale = (gp.music.volumeScale+1)%6;
					gp.music.checkVolume();
					gp.playSE(8);
				}
				if(gp.ui.commandNum==2) {
					gp.sound.volumeScale = (gp.sound.volumeScale+1)%6;
					gp.playSE(8);
				}
			}
		}
	}
	public void titleState(int code) {
		
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum= (gp.ui.commandNum+1)%3;
		}
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum = (gp.ui.commandNum+2)%3;
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0)
				gp.gameState = gp.playState;
				gp.playMusic(0);
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 1) {
				//some work
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 2)
				System.exit(0);
		}
	}
	
	public void playState(int code) {
		if(code == KeyEvent.VK_W) {
			UpPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			LeftPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			DownPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			RightPressed = true;
		}
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if(code == KeyEvent.VK_ENTER) {
			EnterPressed = true;
		}
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		//debug
		if(code == KeyEvent.VK_T) {
			if(showDebugText == false) {
				showDebugText = true;
			}
			else {
				showDebugText = false;
			}
		}
		if(code == KeyEvent.VK_R) {
			switch(gp.currentMap) {
			case 0:gp.tileM.loadMap("/maps/worldV3.txt",0);break;
			case 1:gp.tileM.loadMap("/maps/interior01.txt",1);break;
			}
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
		}
	}
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P){
			gp.gameState = gp.playState;
		}
	}
	
	public void characterState(int code) {
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		
		if(code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
		playerInventory(code);
	}
	
	public void playerInventory(int code) {
		if(code == KeyEvent.VK_W) {
			if(gp.ui.playerslotRow == 0)
				gp.ui.playerslotRow = 4;
			gp.ui.playerslotRow = (gp.ui.playerslotRow-1)%4;
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.playerslotCol== 0)
				gp.ui.playerslotCol = 5;
			gp.ui.playerslotCol = (gp.ui.playerslotCol-1)%5;
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.playerslotRow = (gp.ui.playerslotRow+1)%4;
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_D) {
			gp.ui.playerslotCol = (gp.ui.playerslotCol+1)%5;
			gp.playSE(8);
		}
	}
	
	public void npcInventory(int code) {
		if(code == KeyEvent.VK_W) {
			if(gp.ui.npcslotRow == 0)
				gp.ui.npcslotRow = 4;
			gp.ui.npcslotRow = (gp.ui.npcslotRow-1)%4;
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.npcslotCol== 0)
				gp.ui.npcslotCol = 5;
			gp.ui.npcslotCol = (gp.ui.npcslotCol-1)%5;
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.npcslotRow = (gp.ui.npcslotRow+1)%4;
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_D) {
			gp.ui.npcslotCol = (gp.ui.npcslotCol+1)%5;
			gp.playSE(8);
		}
	}
	
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			UpPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			LeftPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			DownPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			RightPressed = false;
		}
		if(code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}
		

	}

}
