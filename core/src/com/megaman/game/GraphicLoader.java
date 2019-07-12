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
	private Array<Texture> jumpShootTexture;
	private Array<Texture> shootingTexture;
	private Array<Texture> hud;
	private Array<Texture> hudPartTwo;
	private Array<Texture> startText;
	private Array<Texture> fallShootTexture;
	private Animation<Texture> spawnAnimation;
	private Animation<Texture> specialMove;
	private Animation<Texture> fall;
	private Animation<Texture> walk;
	private Animation<Texture> idle;
	private Animation<Texture> jump;
	private Animation<Texture> jumpShoot;
	private Animation<Texture> shooting;
	private Animation<Texture> shoot;
	private Animation<Texture> animatedHUD;
	private Animation<Texture> startAnimation;
	private Animation<Texture> fallShoot;
	
	
	public GraphicLoader() {
		frameRates = 0.060f;
		inizioWalk = new Texture("Megaman/Walk/inizio.png");
		bullet = new Texture("Bullets/ChargeShot/1.png");
		background = new Texture("Background.jpg");
		spawnTexture = new Array<Texture>();
		walkTexture = new Array<Texture>();
		idleTexture = new Array<Texture>();
		jumpTexture = new Array<Texture>();
		jumpShootTexture = new Array<Texture>();
		fallShootTexture = new Array<Texture>();
		fallTexture = new Array<Texture>();
		shootingTexture = new Array<Texture>();
		specialMoveTexture = new Array<Texture>();
		shootTexture = new Array<Texture>();
		hud = new Array<Texture>();
		hudPartTwo = new Array<Texture>();
		startText = new Array<Texture>();
	}
	
	public void importImage () {
		
		for (int i = 0; i < 9; i++) {
			spawnTexture.add(new Texture("Megaman/Spawn/"+i+".png"));
		}
			spawnAnimation = new Animation<Texture>(0.120f, spawnTexture);
		
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
			
		for (int i = 0; i < 6; i++) {
			fallTexture.add(new Texture("Megaman/Fall/"+i+".png"));
		}
			fall = new Animation<Texture>(0.080f ,fallTexture);
			
		for (int i = 0; i < 10; i++) {
			shootingTexture.add(new Texture("Megaman/WalkShoot/"+i+".png"));
		}
			shooting = new Animation<Texture>(frameRates, shootingTexture);
		
		for (int i = 0;i < 2; i++) {
			shootTexture.add(new Texture("Megaman/Shoot/"+i+".png"));
		}
			shoot = new Animation<Texture>(0.080f, shootTexture);
			
		for (int i = 0; i < 3; i++) {
			jumpShootTexture.add(new Texture("Megaman/JumpShoot/"+i+".png"));
		}
			jumpShoot = new Animation<Texture>(0.080f, jumpShootTexture);
			
		for (int i = 0; i < 3; i++) {
			fallShootTexture.add(new Texture("Megaman/FallShoot/"+i+".png"));
		}
			fallShoot = new Animation<Texture>(0.080f,fallShootTexture);
		
		for (int i = 0; i < 6; i++) {
			hud.add(new Texture("HUD/"+i+".png"));
		}
		
		for (int i = 0; i < 44; i++) {
			hudPartTwo.add(new Texture("HUD/EyesClosedResized/"+i+".png"));
		}
		animatedHUD = new Animation<Texture>(0.05f, hudPartTwo);
		
		for (int i = 0; i < 11; i++)
			startText.add(new Texture("Intro/"+i+".png"));
		
		startAnimation = new Animation<Texture>(0.08f, startText);
	}
	
	public Texture getBackground() {
		return background;
	}
	
	public Texture getBullet() {
		return bullet;
	}
	
	public Texture getInizioWalk () {
		return inizioWalk;
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
	
	public Animation<Texture> getJump (){
		return jump;
	}
	
	public Animation<Texture> getFall (){
		return fall;
	}
	
	public Animation<Texture> getShooting (){
		return shooting;
	}
	
	public Animation<Texture> getSpecialMove() {
		return specialMove;
	}
	
	public Animation<Texture> getShoot (){
		return shoot;
	}
	
	public Animation<Texture> getJumpShoot () {
		return jumpShoot;
	}
	
	public Texture getHud(int HP) {
		return hud.get(HP);
	}
	
	public Animation<Texture> getAnimatedHUD(){
		return animatedHUD;
	}
	
	public Animation<Texture> getFallShoot(){
		return fallShoot;
	}
	
	public Animation<Texture> getStartText(){
		return startAnimation;
	}

}
