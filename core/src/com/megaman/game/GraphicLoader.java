package com.megaman.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class GraphicLoader{
	
	//ArrayList <Texture> walk;
	float frameRates;
	private Texture inizioWalk;
	private Array<Texture> walkTexture;
	private Array<Texture> idleTexture;
	private Array<Texture> jumpTexture;
	private Animation<Texture> walk;
	private Animation<Texture> idle;
	private Animation<Texture> jump;
	
	public GraphicLoader() {
		frameRates = 0.060f;
		inizioWalk = new Texture("Megaman/Walk/inizio.png");
		walkTexture = new Array<Texture>();
		idleTexture = new Array<Texture>();
		jumpTexture = new Array<Texture>();
	}
	
	public void importImage () {
		for (int i=0;i<7;i++) {
			jumpTexture.add(new Texture("Megaman/Jump/"+i+".png"));
		}
			jump = new Animation<Texture>(0.080f,jumpTexture);
		for (int i=1;i<11;i++) {
			walkTexture.add(new Texture("Megaman/Walk/"+i+".png"));
		} 
			walk = new Animation<Texture>(frameRates,walkTexture);
		for (int i=0;i<27;i++) {
			idleTexture.add(new Texture("Megaman/Idle/"+i+".png"));
		}
			idle = new Animation<Texture>(frameRates,idleTexture);
	}
	
	public Animation<Texture> getIdle(){
		return idle;
	}
	
	public Animation<Texture> getWalk (){
		return walk;
	}
	
	public Texture getInizioWalk () {
		return inizioWalk;
	}
	
	public Animation<Texture> getJump (){
		return jump;
	}
}


























