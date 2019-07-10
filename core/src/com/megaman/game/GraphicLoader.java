package com.megaman.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

public class GraphicLoader{
	
	//ArrayList <Texture> walk;
	float frameRates;
	private Texture inizioWalk;
	private Texture background;
	private Texture bullet;
	private Array<Texture> spawnTexture;
	private Array<Texture> shootTexture;
	private Array<Texture> specialMoveTexture;
	private Array<Texture> fallTexture;
	private Array<Texture> walkTexture;
	private Array<Texture> idleTexture;
	private Array<Texture> jumpTexture;
	private Array<Texture> shootingTexture;
	private Array<Texture> hud;
	private Animation<Texture> spawnAnimation;
	private Animation<Texture> specialMove;
	private Animation<Texture> fall;
	private Animation<Texture> walk;
	private Animation<Texture> idle;
	private Animation<Texture> jump;
	private Animation<Texture> shooting;
	private Animation<Texture> shoot;
	
	
	public GraphicLoader() {
		frameRates = 0.060f;
		inizioWalk = new Texture("Megaman/Walk/inizio.png");
		bullet = new Texture("Bullets/ChargeShot/1.png");
		background = new Texture("Background.jpg");
		spawnTexture = new Array<Texture>();
		walkTexture = new Array<Texture>();
		idleTexture = new Array<Texture>();
		jumpTexture = new Array<Texture>();
		fallTexture = new Array<Texture>();
		shootingTexture = new Array<Texture>();
		specialMoveTexture = new Array<Texture>();
		shootTexture = new Array<Texture>();
		hud = new Array<Texture>();
	}
	
	public void importImage () {
		
		for (int i = 0; i < 6; i++) {
			spawnTexture.add(new Texture("Megaman/Spawn/"+i+".png"));
		}
			spawnAnimation = new Animation<Texture>(0.080f, spawnTexture);
		
		for (int i = 0; i < 3; i++) {
			jumpTexture.add(new Texture("Megaman/Jump/"+i+".png"));
		}
			jump = new Animation<Texture>(0.080f,jumpTexture);
			
		for (int i = 1; i < 11; i++) {
			walkTexture.add(new Texture("Megaman/Walk/"+i+".png"));	
		} 
			walk = new Animation<Texture>(0.080f,walkTexture);
			
		for (int i = 0; i < 24; i++) {
			idleTexture.add(new Texture("Megaman/Idle/"+i+".png"));
		}
			idle = new Animation<Texture>(frameRates,idleTexture);
			
		for (int i = 0; i < 11; i++) {
			fallTexture.add(new Texture("Megaman/Fall/"+i+".png"));
		}
			fall = new Animation<Texture>(0.040f ,fallTexture);
			
		for (int i = 0; i < 10; i++) {
			shootingTexture.add(new Texture("Megaman/WalkShoot/"+i+".png"));
		}
			shooting = new Animation<Texture>(frameRates, shootingTexture);
		
		for (int i = 0;i < 2; i++) {
			shootTexture.add(new Texture("Megaman/Shoot/"+i+".png"));
		}
			shoot = new Animation<Texture>(0.080f, shootTexture);
			
		for (int i = 0; i < 6; i++) {
			hud.add(new Texture("HUD/"+i+".png"));
		}
	}
	
	public Animation<Texture> getSpawn(){
		return spawnAnimation;
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
	
	public Animation<Texture> getFall (){
		return fall;
	}
	
	public Animation<Texture> getShooting (){
		return shooting;
	}
	
	public Texture getBackground() {
		return background;
	}
	
	public Texture getBullet() {
		return bullet;
	}
	
	public Animation<Texture> getSpecialMove() {
		return specialMove;
	}
	
	public Animation<Texture> getShoot (){
		return shoot;
	}
	
	public Texture getHud(int HP) {
		return hud.get(HP);
	}

}
