package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	public boolean UpPressed , DownPressed , RightPressed , LeftPressed,EnterPressed;
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
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
		
		if(gp.gameState == gp.pokeState) {
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum= (gp.ui.commandNum+1)%3;
			}
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum = (gp.ui.commandNum+2)%3;
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0)
					gp.gameState = gp.playState;
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 1) {
					gp.gameState = gp.fightState;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 2)
					System.exit(0);
			}
		}
		
		//Game state
		if(gp.gameState == gp.playState || gp.gameState == gp.interactState || gp.gameState == gp.pokeState){
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
		}
		
		//Pause State
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_P){
				gp.gameState = gp.playState;
			}
		}
		//Dialogue State
		else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
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
