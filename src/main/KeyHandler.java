package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	public boolean UpPressed , DownPressed , RightPressed , LeftPressed,EnterPressed;
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
			gp.tileM.loadMap("/maps/worldV2.txt");
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
		

	}

}
