package com.megaman.logic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Entity {
	private float speed;
	Body entityBody;
	BodyDef entityBodyDef;
	Fixture entityBodyFixture;
	FixtureDef entityBodyFixtureDef;
	
	public Entity () {
		entityBodyDef = new BodyDef();
		entityBodyFixtureDef = new FixtureDef();
	}
	
	float getSpeed() {
		return speed; 
	}
	
	float getPositionX () {
		return 0;
	}
	
	float getPositionY () {
		return 0;
	}
	
	int getWidth () {
		return 0;
	}
	
	int getHeight () {
		return 0;
	}
	
	void setSpeed (int x) {
		speed = x;
	}
	
	public Body getBody() {
		return entityBody;
	}
	
	public void bodyCreator (float x, float y, float width, float height, boolean type, float mass) {

		if (type)
			entityBodyDef.type = BodyType.StaticBody; // SETTA IL BODY COME DINAMICO
		else
			entityBodyDef.type = BodyType.DynamicBody;
		
		entityBodyDef.position.set(x, y); // IMPOSTA LA POSIZIONE ALLE COORDINATE X = 0; Y = 0;
		entityBodyDef.fixedRotation = true; // FISSA L'IMMAGINE IN MODO DA NON PERMETTERE LA ROTAZIONE
		entityBody = gameManager.getWorld().createBody(entityBodyDef); //CREA IL CORPO NEL MONDO
		PolygonShape shape = new PolygonShape(); //CREA UNA FORMA PER IL CORPO DI MEGAMAN
		shape.setAsBox(width, height); // CREA UNA FORMA QUADRATA DI 64*64 (32*32 ESTENDENDO DAL CENTRO)
		entityBody.createFixture(shape, mass); //ASSEGNA LA FORMA AL CORPO ASSEGNANDOGLI UNA MASSA
		shape.dispose();
		entityBody.setUserData(this);
	}
	
	public void sensorCreator (float x, float y, float width, float height, boolean type) {
		entityBodyDef.fixedRotation = true;
		if (type)
			entityBodyDef.type = BodyType.StaticBody;
		else
			entityBodyDef.type = BodyType.DynamicBody;
		
		entityBodyDef.position.set(x, y);
		entityBodyDef.fixedRotation = true;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		entityBodyFixtureDef.shape = shape;
		entityBodyFixtureDef.density = 1.0f;
		entityBodyFixtureDef.isSensor = true;
		
		entityBody = gameManager.getWorld().createBody(entityBodyDef);
		entityBody.createFixture(entityBodyFixtureDef).setUserData(this);
	}

}








