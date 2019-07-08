package com.megaman.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.megaman.game.Utils.Constants.PPM;;
public class Megaman extends Entity {
	
	private Body megamanBody; // CORPO PER MEGAMAN
	private BodyDef megamanBodyDef; // CARATTERISTICHE DEL CORPO DI MEGAMAN
	
	public Megaman (){
		super();
		setSpeed(3);
		megamanBodyDef = new BodyDef(); // CREA IL NUOVO BODY PER MEGAMAN
		megamanBodyDef.type = BodyDef.BodyType.DynamicBody; // SETTA IL BODY COME DINAMICO
		megamanBodyDef.position.set(5, 10); // IMPOSTA LA POSIZIONE ALLE COORDINATE X,Y;
		megamanBodyDef.fixedRotation = true; // FISSA L'IMMAGINE IN MODO DA NON PERMETTERE LA ROTAZIONE
		megamanBody = gameManager.getWorld().createBody(megamanBodyDef); //CREA IL CORPO NEL MONDO
		PolygonShape shape = new PolygonShape(); //CREA UNA FORMA PER IL CORPO DI MEGAMAN
		shape.setAsBox(32 / PPM / 2, 32 / PPM / 2); // CREA UNA FORMA QUADRATA DI 64*64 (32*32 ESTENDENDO DAL CENTRO) - LA DIVISIONE ELIMINA IL CONTORNO
		megamanBody.createFixture(shape, 1.0f); //ASSEGNA LA FORMA AL CORPO ASSEGNANDOGLI UNA MASSA
		shape.dispose(); //AVENDO ASSEGNATO LA FORMA, NON NE HO PIÙ BISOGNO E USO IL DISPOSE
	}
	
	public Body getMegamanBody () {
		return megamanBody;
	}
}
