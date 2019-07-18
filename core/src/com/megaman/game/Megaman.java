package com.megaman.game;

import static com.megaman.game.Utils.Constants.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
public class Megaman extends Entity {
	
	public Megaman (){
		super();
		super.bodyCreator(gameManager.getSpawn().x/PPM, gameManager.getSpawn().y/PPM, PPM/2/PPM/2, PPM/2/PPM/2, false, 2.0f);
		feetSensorCreator();
		leftCollisionCreator();
		rightCollisionCreator();
		setSpeed(3);
	}
	
	public float getSpeed() {
		return super.getSpeed()/PPM;
	}
	
	public float getPositionX () {
		return this.getBody().getLinearVelocity().x;
	}
	
	public float getPositionY () {
		return this.getBody().getLinearVelocity().y;
	}
	
	public void setPositionX (float x) {
		this.getBody().setLinearVelocity(x, getPositionY());
	}
	
	public void setPositionY (float y) {
		this.getBody().setLinearVelocity(getPositionX(), y);
	}
	
	public void respawn () {
		this.getBody().setTransform(gameManager.getSpawn().x/PPM, gameManager.getSpawn().y/PPM, 0);
	}
	
	public void feetSensorCreator () {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.15f, 0, new Vector2(0, -0.30f), 0);
		super.entityBodyFixtureDef = new FixtureDef();
		super.entityBodyFixtureDef.shape = shape;
		super.entityBodyFixtureDef.isSensor = true;
		super.entityBody.createFixture(super.entityBodyFixtureDef).setUserData("feet");
		
		shape.dispose();
	}
	
	public void leftCollisionCreator () {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0, 0.15f, new Vector2(-0.26f, 0), 0);
		super.entityBodyFixtureDef = new FixtureDef();
		super.entityBodyFixtureDef.shape = shape;
		super.entityBodyFixtureDef.isSensor = true;
		super.entityBody.createFixture(super.entityBodyFixtureDef).setUserData("leftSide");
		
		shape.dispose();
	}
	
	public void rightCollisionCreator () {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0, 0.15f, new Vector2(0.26f, 0), 0);
		super.entityBodyFixtureDef = new FixtureDef();
		super.entityBodyFixtureDef.shape = shape;
		super.entityBodyFixtureDef.isSensor = true;
		super.entityBody.createFixture(super.entityBodyFixtureDef).setUserData("rightSide");
		
		shape.dispose();
	}
}







