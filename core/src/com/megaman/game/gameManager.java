package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.megaman.game.Utils.ContactDetector;
import com.megaman.game.Utils.MapParser;

import static com.megaman.game.Utils.Constants.*;

public class gameManager {
	
	Entity deathZone;
	Controller controller;
	Megaman megaman;
	GraphicsManager gm;
	HUD hud;
	Array<Bullet> ammunition;
	int bulletCorrente;
	int numSalto;
	float forceX;
	float forceY;
	boolean[] shootThisBullet;
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
		
		Entity platform = new Entity();
		platform.bodyCreator(5, 2,12 / PPM, 32 / PPM, true, 1);
		platform.getBody().setUserData("platform");
		megaman = new Megaman();
		Vector2 death = new Vector2();
		deathZone = new Entity();	
		deathZone.sensorCreator(getDeath().getCenter(death).x/PPM,getDeath().getCenter(death).y/PPM, getDeath().getWidth()/PPM/2, getDeath().getHeight()/PPM/2, true);
		deathZone.getBody().setUserData("deathZone");
		shootThisBullet = new boolean[50];
		ammunition = new Array<Bullet>(50);
		for (int i=0;i<50;i++) {
			ammunition.add(new Bullet(megaman));
			ammunition.get(i).getBody().setGravityScale(0);
		}
		
		detector = new ContactDetector(deathZone, megaman, ammunition, platform);
		world.setContactListener(detector);
		numSalto = 0;
		controller = new Controller();
		
		gm = graphicManager;
		hud = new HUD();
		bulletCorrente = 0;

	}
	public void run(SpriteBatch batch) {
		//UTILIZZA IL RENDER DELLA MAPPA SULLO STESSO SPRITEBATCH
		batch.end();
		mapRenderer.render();
		batch.begin();
		
		controller.muoviMegaman(megaman);
		gm.drawMegaman(batch, controller, megaman);
		gm.drawHud(batch, megaman, hud);
		
		batch.end();
		b2dr.render(world, camera.combined.scl(PPM)); //PIU' E' PICCOLO IL VALORE DI SCALA, PIU' E' GRANDE LA DISTANZA COPERTA DALLA CAMERA
		batch.begin();
		
		updateMegaman();
		updateBullet(batch);
		
	}
	
	
	public void addBullet (int index) {
		ammunition.insert(index, new Bullet(megaman));
		ammunition.get(index).getBody().setGravityScale(0);
	}
	
	public void increaseBullet() {
		bulletCorrente++;
		if (bulletCorrente == 50) {
			bulletCorrente = 0;
			
		}
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
		for (int i = 0; i < ammunition.size; i++)
		{
			if (!shootThisBullet[i]) {
				ammunition.get(i).setPositionX(megaman.getBody().getPosition().x*PPM - PPM/2);
				ammunition.get(i).setPositionY(megaman.getBody().getPosition().y*PPM - PPM/2);
			}
		}
		
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
				forceX = 5;
			else
				forceX = -5;
			controller.setControlliFalse(WALK_START);
		}
		
		// SET DI JUMP
		if (controller.getControlli(JUMP) && !controller.getControlli(FALL) && numSalto < 1) {
			forceY+=6;
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
				forceX = 5;
			else
				forceX = -5;
			if (controller.getControlli(WALK_JUMP) && !isMegamanFalling() && numSalto < 1) {
				forceY += 4;
				numSalto++;
			}
			controller.setControlliFalse(WALK);
		}
		// SPOSTAMENTO FISICO DI MEGAMAN
		megaman.getBody().setLinearVelocity(forceX,forceY);
	}
	
	public void updateBullet(SpriteBatch batch) {
		if (controller.shot) {
			ammunition.get(bulletCorrente).setDirection(controller.getDirection());
			if (ammunition.get(bulletCorrente).getDirection())
				ammunition.get(bulletCorrente).setPositionX(megaman.getBody().getPosition().x*PPM-PPM);
			else
				ammunition.get(bulletCorrente).setPositionX(megaman.getBody().getPosition().x*PPM);
			ammunition.get(bulletCorrente).setPositionY(megaman.getBody().getPosition().y*PPM-PPM/2);
			shootThisBullet[bulletCorrente] = true;
			increaseBullet();
			controller.shot = false;
		}
		for (int i=0;i<ammunition.size;i++) {
			if (shootThisBullet[i]) {
				gm.drawBullet(batch, ammunition.get(i),ammunition.get(i).getDirection());
				ammunition.get(i).physics();
			} 
			//THE PROBLEM DOESN'T LIE HERE! SEARCH ELSEWHERE!
			if (ammunition.get(i).getPositionX() + PPM < 0 || ammunition.get(i).getPositionX()+ammunition.get(i).getPositionX() > 1280){
				world.destroyBody(ammunition.get(i).getBody());
				System.out.println("DISTRUGGERE! DISTRUGGERE!");
				ammunition.removeIndex(i);
				addBullet(i);
				shootThisBullet[i] = false;
			}
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
	
	public static Rectangle getDeath() {
		Vector2 deathPos = new Vector2();
		Rectangle rect = new Rectangle();
		for (MapObject object : map.getLayers().get("HealthLose").getObjects()) {
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

}




















