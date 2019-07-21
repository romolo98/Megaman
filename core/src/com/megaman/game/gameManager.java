package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.megaman.game.Utils.ContactDetector;
import com.megaman.game.Utils.MapParser;

import static com.megaman.game.Utils.Constants.*;

public class gameManager {
	Entity deathZone;
	Controller controller;
	Megaman megaman;
	Boss boss;
	GraphicsManager gm;
	HUD hud;
	Array<Entity> deathzones;
	Array<Bullet> ammunition;
	Array<Bullet> ammunitionToDestroy;
	Array<Enemy> axeBot;
	Array<Float> explosionTimers;
	Music level1;
	int numSalto;
	float forceX;
	float forceY;
	private ContactDetector detector;
	private static OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	private static TiledMap map;
	private Box2DDebugRenderer b2dr;
	static private World world;
	private final static float SCALE = 2.0f;
	private static float startWidth;
	private static float startHeight;
	private static int levelWidth = 0;
	private static int levelHeight = 0;
	double delay = 1200;
	double lastTime = 0;
	double actualTime = 0;
	
	
	
	public gameManager(GraphicsManager graphicManager) {

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/SCALE, h/SCALE);
		b2dr = new Box2DDebugRenderer();
		world = new World(new Vector2(0,-9.81f), false);
		map = new TmxMapLoader().load("Levels/Level1.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		MapProperties mapProperties = map.getProperties();
		levelWidth = mapProperties.get("width", Integer.class);
		levelHeight = mapProperties.get("height", Integer.class);
		MapParser.parseObjectLayer(world, map.getLayers().get("Collision").getObjects());
		ammunitionToDestroy = new Array<Bullet>();
		level1 = Gdx.audio.newMusic(Gdx.files.internal("Audio/Level1Theme.mp3"));

		axeBot = new Array<Enemy>(getAxebotSpawn().size);
		explosionTimers = new Array<Float>(getAxebotSpawn().size);
		for (int i = 0; i < getAxebotSpawn().size; i++) {
			axeBot.add(new Enemy(i));
			explosionTimers.add(0f);
		}
		
		deathzones = new Array<Entity>(getDeath().size);
		for (int i = 0; i < getDeath().size; i++) {
			deathzones.add(new Entity());
			deathzones.get(i).sensorCreator(getDeath().get(i).x/PPM+getDeath().get(i).getWidth()/PPM/2, getDeath().get(i).y/PPM+getDeath().get(i).getHeight()/PPM/2, getDeath().get(i).getWidth()/PPM/2, getDeath().get(i).getHeight()/PPM/2, true);
			deathzones.get(i).getBody().setUserData("death");
		}

		megaman = new Megaman();
		boss = new Boss();
		

		
		ammunition = new Array<Bullet>();
		
		controller = new Controller();
		
		detector = new ContactDetector(deathzones, megaman, ammunition, axeBot, controller, boss);
		world.setContactListener(detector);
		numSalto = 0;
		
		
		gm = graphicManager;
		hud = new HUD();
		
	}
	public void run(SpriteBatch batch) {
		//UTILIZZA IL RENDER DELLA MAPPA SULLO STESSO SPRITEBATCH
		batch.end();
		mapRenderer.render();
		batch.begin();
		
		level1.play();
		
		updateBoss(boss,megaman);
		
		controller.muoviMegaman(megaman,detector);
		gm.drawMegamanX(batch, controller, megaman);
		gm.drawHud(batch, megaman, hud);
		gm.drawBossX(batch, boss);
		
		for (int i = 0; i < getAxebotSpawn().size; i++) {
			if (!axeBot.get(i).isDead) {
				gm.drawEnemy(batch, axeBot.get(i));
			}
			if (axeBot.get(i).getIsDead() && !axeBot.get(i).getExplosionState()) {
				gm.drawExplosion(batch, getAxebotSpawn().get(i).x/PPM, getAxebotSpawn().get(i).y/PPM);
				explosionTimers.set(i, explosionTimers.get(i) + Gdx.graphics.getDeltaTime());
			}
			if (gm.getGL().getExplosion().isAnimationFinished(explosionTimers.get(i))) {
				explosionTimers.set(i, 0f);
				axeBot.get(i).setHasExploded();
			}
		}
		
		batch.end();
		b2dr.render(world, camera.combined.scl(PPM)); //PIU' E' PICCOLO IL VALORE DI SCALA, PIU' E' GRANDE LA DISTANZA COPERTA DALLA CAMERA
		batch.begin();
		
		updateMegaman();
		updateBullet(batch);
		updateBulletBoss(batch);
	}

	public void disposer () {
		b2dr.dispose();
		world.dispose();
		mapRenderer.dispose();
		map.dispose();
	}
	
	static public World getWorld() {
		return world;
	}
	
	public void cameraUpdate (float delta) {
		Vector3 position = camera.position;
		//INTERPOLAZIONE DELLA CAMERA		
		position.x = camera.position.x + (megaman.getBody().getPosition().x*PPM - camera.position.x) * .1f;
		position.y = camera.position.y + (megaman.getBody().getPosition().y*PPM - camera.position.y) * .1f;
		
		startWidth = camera.viewportWidth/2;
		startHeight = camera.viewportHeight/2;
		
		cameraBoundaries(camera, startWidth, startHeight, levelWidth * PPM - startWidth * 2, levelHeight * PPM - startHeight * 2);
		
		camera.position.set(position);
		camera.update();
	}
	
	public static OrthographicCamera getCamera() {
		return camera;
	}
	
	public Megaman getMegaman() {
		return megaman;
	}
	
	public OrthogonalTiledMapRenderer getRenderer() {
		return mapRenderer;
	}
	
	public static final float getScale() {
		return SCALE;
	}
	
	public void updateMegaman () {
		forceX = 0;
		forceY = megaman.getPositionY();
		
		actualTime = System.currentTimeMillis();
		lastTime = controller.getLastTime();
		
		controller.setStart(!gm.getStart());
		
		// DEBUG DOPPIO INPUT
		if (controller.getControlli(JUMP) && controller.getControlli(SHOOT)) {
			controller.setControlliFalse(SHOOT);
		}
		if (controller.getControlli(FALL) && controller.getControlli(FALL_SHOOT)) {
			controller.setControlliFalse(FALL);
		}
		
		// RESPAWN
		if (detector.getReset()) {
			controller.setControlliTrue(SPAWN);
			megaman.respawn();
		}
		
		// SETTIAMO LO SPAWN DI MEGAMAN
		if (controller.getControlli(SPAWN)) {
			gm.resetFirstSpawn();
			controller.setStart(!gm.getStart());
			controller.setControlliFalse(SPAWN);
		}
		// SETTIAMO A FALSE LO SHOOT
		if (gm.getShootDone()) {
			controller.setControlliFalse(SHOOT);
		}
		
		// SET FALL TRUE AND FALSE
		if (isMegamanFalling()) {
			if (!controller.getControlli(FALL_SHOOT))
				controller.setFallTrue();
			else {
				controller.setControlliFalse(FALL);
			}
			numSalto = 0;
			// DEBUG VISNU
			controller.setControlliFalse(WALK_SHOOT);
			controller.setControlliFalse(JUMP);
			controller.setControlliFalse(SHOOT);
			controller.setControlliFalse(WALK_JUMP);
			controller.setControlliFalse(WALK_START);
			controller.setControlliFalse(JUMP_SHOOT);
		}
		else {
			controller.setControlliFalse(FALL);
			controller.setControlliFalse(FALL_SHOOT);
			gm.setFallAnimationZero();
		}
		
		// SET DI WALK_START
		if (controller.getControlli(WALK_START)){
			if (!controller.getDirection())
				forceX = 5f;
			else
				forceX = -5f;
			controller.setControlliFalse(WALK_START);
		}
		
		// SET DI JUMP
		if (controller.getControlli(JUMP) && !controller.getControlli(FALL) && numSalto < 1) {
			forceY+=5;
			numSalto++;
			if (controller.getControlli(JUMP_SHOOT)) {
				controller.setControlliFalse(JUMP);
			}
		}
		// SET DI WALK_SHOOT
		
		if (!controller.getDirection()) {
			if ((controller.getControlli(WALK_SHOOT) && actualTime > delay + lastTime) || controller.getNotRight()) {
				controller.setControlliFalse(WALK_SHOOT);
			}
		}
		else {
			if ((controller.getControlli(WALK_SHOOT) && actualTime > delay + lastTime) || controller.getNotLeft()) {
				controller.setControlliFalse(WALK_SHOOT);
			}
		}
		// SET DI WALK, WALK_JUMP
		if (controller.getControlli(WALK)) {
			if (!controller.getDirection()) 
				forceX = 5f;
			else
				forceX = -5f;
			if (controller.getControlli(WALK_JUMP) && !isMegamanFalling() && numSalto < 1) {
				forceY += 4;
				numSalto++;
			}
			controller.setControlliFalse(WALK);
		}
		// SPOSTAMENTO FISICO DI MEGAMAN

			megaman.getBody().setLinearVelocity(forceX, forceY);
		}
	
	public void updateBullet(SpriteBatch batch) {		
		if (controller.shot) {
			ammunition.add(new Bullet(megaman));
			ammunition.peek().setDirection(controller.getDirection());
			
			controller.shot = false;
		}
		for (Bullet i: ammunition) {
				gm.drawBullet(batch, i,i.getDirection());
				i.physics(); 
		}
	}
	
	public void updateBoss (Boss boss,Megaman megaman) {
			boss.bossIA(megaman);
			if (!boss.isDead) {
			if (gm.getAnimationBossJumpDone()) {
				if (!boss.isBossFalling()) {
					gm.setAnimationBossJumpDoneFalse();
					gm.setBossJumpFrame();
					boss.setBossActionFalse(BOSS_JUMP);
				}
			}
			if (gm.getAnimationBossPunchDone()) {
				if ((megaman.getBody().getPosition().x - boss.getBody().getPosition().x) > 1 || ((boss.getBody().getPosition().x - megaman.getBody().getPosition().x) > 1)) {
				gm.setAnimationBossPunchDoneFalse();
				gm.setBossPunchFrame();
				boss.setBossActionFalse(BOSS_PUNCH);
				}
			}
		}
	}
	public void updateBulletBoss (SpriteBatch batch) {
		
		for (Bullet i: boss.getBossBullets()) {
			gm.drawBossBullet(batch,i,i.getDirection());
			i.physicsIA(megaman,boss);
			i.setShoot(true);
		}
	}
	
	public static void cameraBoundaries (Camera camera, float leftX, float leftY, float rightX, float rightY) {
		Vector3 boundariesPosition = camera.position;
		
		if (boundariesPosition.x < leftX) {
			boundariesPosition.x = leftX;
		}
		
		if (boundariesPosition.y < leftY) {
			boundariesPosition.y = leftY;
		}
		
		if (boundariesPosition.x > leftX + rightX) {
			boundariesPosition.x = leftX + rightX;
		}
		
		if (boundariesPosition.y > leftY + rightY) {
			boundariesPosition.y = leftY + rightY;
		}
		
		camera.position.set(boundariesPosition);
		camera.update();
	}
	
	public static int getLevelWidth () {
		return levelWidth;
	}
	
	public static int getLevelHeight() {
		return levelHeight;
	}
	
	public static float getStartWidth() {
		return startWidth;
	}
	
	public static float getStartHeight() {
		return startHeight;
	}
	
	public static Vector2 getSpawn () {
		Vector2 position = new Vector2();
		Rectangle rect = new Rectangle();
		for (MapObject object : map.getLayers().get("Start").getObjects()) {
			if (object instanceof RectangleMapObject) {
				rect = ((RectangleMapObject)object).getRectangle();
			}
		}
		return rect.getCenter(position);
	}
	
	public static Array<Rectangle> getDeath() {
		Array<Rectangle> death = new Array<Rectangle>();
		for (MapObject object : map.getLayers().get("HealthLose").getObjects()) {
			if (object instanceof RectangleMapObject) {
				death.add(((RectangleMapObject)object).getRectangle());
			}
		}
		return death;
	}
	
	public static Array<Rectangle> getAxebotSpawn() {
		Array<Rectangle> axebotSpawn = new Array<Rectangle>();
		for (MapObject object : map.getLayers().get("AxebotSpawn").getObjects()) {
			if (object instanceof RectangleMapObject) {
				axebotSpawn.add(((RectangleMapObject)object).getRectangle());
			}
		}
		return axebotSpawn;
	}
	
	public static Rectangle getBossSpawn() {
		Rectangle rect = new Rectangle();
		for (MapObject object : map.getLayers().get("BossSpawn").getObjects()) {
			if (object instanceof RectangleMapObject) {
				rect = ((RectangleMapObject)object).getRectangle();
			}
		}
		return rect;
	}
	
	boolean isMegamanFalling () {
		if (megaman.getBody().getLinearVelocity().y < -1.3) {
			return true;
		}
		return false;
	}
	
	public void bossDestroyer() {
		if (boss.getMustDie())
		{
			world.destroyBody(boss.getBody());
			boss.getBody().setUserData(null);
			boss.setBodyNull();
			boss.setDeath();
			
			for (int i = 0; i < boss.getBossBullets().size; i++) {
				boss.getBossBullets().get(i).setMustDie();
			}
		}
	}
	
	public void enemiesDestroyer() {
		for (int i = 0; i < axeBot.size; i++) {
			if (axeBot.get(i).getMustDie()) {
				world.destroyBody(axeBot.get(i).getBody());
				axeBot.get(i).getBody().setUserData(null);
				axeBot.get(i).setBodyNull();
				axeBot.get(i).setDeath();
			}
		}
	}
	
	public void bossBulletDestroyer() {
		for (int i = 0; i < boss.getBossBullets().size; i++) {
			if (boss.getBossBullets().get(i).getMustDie()) {
				world.destroyBody(boss.getBossBullets().get(i).getBody());
				boss.getBossBullets().get(i).setBodyNull();
				boss.getBossBullets().get(i).setDeath();
			}
		}
		boss.getBossBullets().removeAll(boss.getBulletsToDestroy(), true);
		boss.clearBulletsToDestroy();
	}
	
	public void MegamanBulletDestroyer() {
		ammunitionToDestroy = detector.ammoToRemove();
		
		for (int i = 0; i < ammunition.size; i++) {
			if (ammunition.get(i).getMustDie()) {
				world.destroyBody(ammunition.get(i).getBody());
				ammunition.get(i).setBodyNull();
				ammunition.get(i).setDeath();
			}
		}
		ammunition.removeAll(ammunitionToDestroy, true);
		ammunitionToDestroy.clear();
	}
}




















