package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel gp;
	public Config(GamePanel gp) {
		this.gp = gp;
	}
	
	public void saveConfig() {
		try {
		BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
		
		//FullScreen
		if(gp.fullScreenOn == true) {bw.write("On");}
		else {bw.write("Off");}
		bw.newLine();
		
		//musicVolume
		bw.write(String.valueOf(gp.music.volumeScale));
		bw.newLine();
		
		//se
		bw.write(String.valueOf(gp.sound.volumeScale));
		bw.newLine();
		
		bw.close();
		
		}catch(IOException e) {
			e.printStackTrace();	
		}
	}
	public void loadConfig() {
		try {
		BufferedReader br = new BufferedReader(new FileReader("config.txt"));
		
		String s = br.readLine();
		
		//FullScreen
		if(s.equals("On")) {gp.fullScreenOn = true;}
		else if(s.equals("Off")) {gp.fullScreenOn = false;}
		//music volume
		s = br.readLine();
		gp.music.volumeScale = Integer.parseInt(s);
		//se volume
		s = br.readLine();
		gp.sound.volumeScale = Integer.parseInt(s);
		
		br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
