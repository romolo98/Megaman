package com.megaman.game;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.megaman.game.Utils.MapParser;

import static com.megaman.game.Utils.Constants.*;

public class gameManager {
	
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
		
		megaman = new Megaman();
		numSalto = 0;
		//Body platform = bodyCreator(5, 10, 128, 32, true);
		
		
		ammunition = new Array<Bullet>(50);
		shootThisBullet = new boolean[50];
		controller = new Controller();
		
		gm = graphicManager;
		hud = new HUD();
		bulletCorrente = 0;
		for (int i=0;i<50;i++) {
			ammunition.add(new Bullet());
		}
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
		ammunition.insert(index, new Bullet());
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
		position.x = camera.position.x + (megaman.getMegamanBody().getPosition().x*PPM - camera.position.x) * .1f;
		position.y = camera.position.y + (megaman.getMegamanBody().getPosition().y*PPM - camera.position.y) * .1f;
		
		startWidth = camera.viewportWidth/2;
		startHeight = camera.viewportHeight/2;
		
		cameraBoundaries(camera, startWidth, startHeight, levelWidth * PPM - startWidth * 2, levelHeight * PPM - startHeight * 2);
		
		camera.position.set(position);
		camera.update();
	}
	
	public static OrthographicCamera getCamera() {
		return camera;
	}
	
	/*public Body bodyCreator (float x, float y, float width, float height, boolean type) {
		Body bodyEntity;
		BodyDef entityDef = new BodyDef();
		
		if (type)
		entityDef.type = BodyDef.BodyType.StaticBody; // SETTA IL BODY COME DINAMICO
		else
		entityDef.type = BodyDef.BodyType.DynamicBody;
		
		entityDef.position.set(x, y); // IMPOSTA LA POSIZIONE ALLE COORDINATE X = 0; Y = 0;
		entityDef.fixedRotation = true; // FISSA L'IMMAGINE IN MODO DA NON PERMETTERE LA ROTAZIONE
		bodyEntity = gameManager.getWorld().createBody(entityDef); //CREA IL CORPO NEL MONDO
		PolygonShape shape = new PolygonShape(); //CREA UNA FORMA PER IL CORPO DI MEGAMAN
		shape.setAsBox(width / PPM, height / PPM); // CREA UNA FORMA QUADRATA DI 64*64 (32*32 ESTENDENDO DAL CENTRO)
		bodyEntity.createFixture(shape, 1.0f); //ASSEGNA LA FORMA AL CORPO ASSEGNANDOGLI UNA MASSA
		shape.dispose(); //AVENDO ASSEGNATO LA FORMA, NON NE HO PIÙ BISOGNO E USO IL DISPOSE
		return bodyEntity;
	}*/
	
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
		
		
		//SETTIAMO A FALSE LO SHOOT
		if (gm.getShootDone()) {
			controller.setControlliFalse(SHOOT);
		}
		
		//SET FALL TRUE AND FALSE
		if (isMegamanFalling()) {
			controller.setFallTrue();
			numSalto = 0;
		}
		else {
			controller.setControlliFalse(FALL);
		}
		
		// SET DI WALK_START
		if (controller.getControlli(WALK_START)){
			if (!controller.getDirection())
				forceX = 5;
			else
				forceX = -5;
			controller.setControlliFalse(WALK_START);
		}
		
		//SET DI JUMP
		if (controller.getControlli(JUMP) && !controller.getControlli(FALL) && numSalto < 2) {
			forceY+=3;
			numSalto++;
		}
		// SET DI WALK, WALK_SHOOT, WALK_JUMP
		if (controller.getControlli(WALK)) {
			if (!controller.getDirection()) 
				forceX = 5;
			else
				forceX = -5;
			if (controller.getControlli(WALK_SHOOT) && actualTime > delay + lastTime) {
				controller.setControlliFalse(WALK_SHOOT);
			}
			if (controller.getControlli(WALK_JUMP) && !isMegamanFalling() && numSalto < 1) {
				forceY += 4;
				numSalto++;
			}
			controller.setControlliFalse(WALK);
		}
		//SPOSTAMENTO FISICO DI MEGAMAN
		megaman.getMegamanBody().setLinearVelocity(forceX,forceY);
	}
	
	public void updateBullet(SpriteBatch batch) {
		if (controller.shot) {
			ammunition.get(bulletCorrente).setDirection(controller.getDirection());
			if (ammunition.get(bulletCorrente).getDirection())
				ammunition.get(bulletCorrente).setPositionX(megaman.getMegamanBody().getPosition().x*PPM-PPM);
			else if (!ammunition.get(bulletCorrente).getDirection())
				ammunition.get(bulletCorrente).setPositionX(megaman.getMegamanBody().getPosition().x*PPM);
			ammunition.get(bulletCorrente).setPositionY(megaman.getMegamanBody().getPosition().y*PPM-PPM/2);
			shootThisBullet[bulletCorrente] = true;
			increaseBullet();
			controller.shot = false;
		}
		for (int i=0;i<ammunition.size;i++) {
			if (shootThisBullet[i]) {
				gm.drawBullet(batch, ammunition.get(i),megaman, ammunition.get(i).getDirection());
				ammunition.get(i).physics();
			}
			if (ammunition.get(i).getPositionX()-ammunition.get(i).getSpeed() < -64 || ammunition.get(i).getPositionX()+ammunition.get(i).getSpeed() > megaman.getMegamanBody().getPosition().x+640*PPM){
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
	
	public static Vector2 getDeath() {
		Vector2 deathPos = new Vector2();
		Rectangle rect = new Rectangle();
		for (MapObject object : map.getLayers().get("HealthLose").getObjects()) {
			if (object instanceof RectangleMapObject) {
				rect = ((RectangleMapObject)object).getRectangle();
			}
		}
		return rect.getPosition(deathPos);
	}
	
	boolean isMegamanFalling () {
		if (megaman.getMegamanBody().getLinearVelocity().y < -1.3) {
			return true;
		}
		return false;
	}

}




















