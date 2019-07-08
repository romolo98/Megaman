package com.megaman.game.Utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class MapParser {
	public static void parseObjectLayer (World world, MapObjects objects) {
		for (MapObject object : objects) {
			Shape shape;
			if (object instanceof PolylineMapObject) {
				shape = createPolyline((PolylineMapObject) object);
			}
			else {
				continue; //PROVVISORIO
			}
			
			Body collisions;
			BodyDef collisionDef = new BodyDef();
			collisionDef.type = BodyDef.BodyType.StaticBody; 
			collisions = world.createBody(collisionDef);
			collisions.createFixture(shape, 1.0f);
			shape.dispose();
	}
}

	private static ChainShape createPolyline(PolylineMapObject collisionLine) {
		float[] boundaries = collisionLine.getPolyline().getTransformedVertices();
		Vector2[] worldBoundaries = new Vector2[boundaries.length/2];
		
		for (int i = 0; i < worldBoundaries.length; i++) {
			worldBoundaries[i] = new Vector2(boundaries[i*2] / Constants.PPM, boundaries[i * 2 + 1] / Constants.PPM);
		}
		
		ChainShape collisionForm = new ChainShape();
		collisionForm.createChain(worldBoundaries);
			
		return collisionForm;
	}
}