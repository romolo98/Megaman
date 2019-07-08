package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.megaman.game.Utils.MapParser;

import static com.megaman.game.Utils.Constants.PPM;

public class gameManager {
	
	Controller controller;
	Megaman megaman;
	GraphicsManager gm;
	HUD hud;
	Array<Bullet> ammunition;
	int bulletCorrente;
	boolean[] shootThisBullet;
	private static OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap map;
	private Box2DDebugRenderer b2dr;
	static private World world;
	private final static float SCALE = 2.0f;
	
	
	public gameManager(GraphicsManager graphicManager) {
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/SCALE, h/SCALE);
		b2dr = new Box2DDebugRenderer();
		world = new World(new Vector2(0,-9.8f), false);
		map = new TmxMapLoader().load("Levels/Level1.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		MapParser.parseObjectLayer(world, map.getLayers().get("Collisions").getObjects());
		MapParser.parseObjectLayer(world, map.getLayers().get("Collisions2").getObjects());
		MapParser.parseObjectLayer(world, map.getLayers().get("Collisions3").getObjects());
		
		
		megaman = new Megaman();
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
		
		gm.drawMegaman(batch, controller, megaman);
		//gm.drawHud(batch, megaman, hud);
		
		batch.end();
		b2dr.render(world, camera.combined.scl(PPM)); //PIU' E' PICCOLO IL VALORE DI SCALA, PIU' E' GRANDE LA DISTANZA COPERTA DALLA CAMERA
		batch.begin();
		
		if (controller.getControlli(controller.SHOOT)) {
			ammunition.get(bulletCorrente).setDirection(controller.getDirection());
			if (ammunition.get(bulletCorrente).getDirection())
				ammunition.get(bulletCorrente).setPositionX(megaman.getMegamanBody().getPosition().x*PPM-64);
			else if (!ammunition.get(bulletCorrente).getDirection())
				ammunition.get(bulletCorrente).setPositionX(megaman.getMegamanBody().getPosition().x*PPM);
			ammunition.get(bulletCorrente).setPositionY(megaman.getMegamanBody().getPosition().y*PPM-32);
			System.out.println(ammunition.get(bulletCorrente).getPositionY());
			shootThisBullet[bulletCorrente] = true;
			increaseBullet();
			controller.setControlliFalse(controller.SHOOT);
		}
		for (int i=0;i<ammunition.size;i++) {
			if (shootThisBullet[i]) {
				gm.drawBullet(batch, ammunition.get(i),megaman, ammunition.get(i).getDirection());
				ammunition.get(i).physics();
			}
			if (ammunition.get(i).getPositionX()-ammunition.get(i).getSpeed() < -64 || ammunition.get(i).getPositionX()+ammunition.get(i).getSpeed() > megaman.getMegamanBody().getPosition().x+640*PPM){
				System.out.println(megaman.getMegamanBody().getPosition().x);
				ammunition.removeIndex(i);
				addBullet(i);
				shootThisBullet[i] = false;
			}
		}
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
		position.x = megaman.getMegamanBody().getPosition().x*PPM;
		position.y = megaman.getMegamanBody().getPosition().y*PPM;
		camera.position.set(position);
		
		camera.update();

	}
	
	public static OrthographicCamera getCamera() {
		return camera;
	}
	
	public Body bodyCreator (float x, float y, float width, float height, boolean type) {
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
}




















