package com.megaman.game.Utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.megaman.game.Bullet;
import com.megaman.game.Controller;
import com.megaman.game.Enemy;
import com.megaman.game.Entity;
import com.megaman.game.HUD;
import com.megaman.game.Megaman;

public class ContactDetector implements ContactListener{
	
	private Body deathZone;
	private Megaman megaman;
	private Array<Enemy> axebots;
	private boolean reset;
	private Array<Bullet> ammo;
	private Controller sameController;
	private Entity test;
	
	public ContactDetector(Entity dZ, Megaman m, Array<Bullet> ammunition, Array<Enemy> axebot, Controller controller) {
		deathZone = dZ.getBody();
		megaman = m;
		reset = false;
		ammo = ammunition;
		axebots = axebot;
		sameController = controller;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture A = contact.getFixtureA();
		Fixture B = contact.getFixtureB();
		
		if (A == null || B == null) return;
		if (A.getBody().getUserData() == null || B.getBody().getUserData() == null) return;
		
		//DEATHZONE COLLISIONS
		if (A.getBody().getUserData() == deathZone.getUserData() || B.getBody().getUserData() == deathZone.getUserData()) {
			if (A.getBody().getUserData() == megaman.getBody().getUserData() || B.getBody().getUserData() == megaman.getBody().getUserData()) {
				reset = true;
			}
		}
		
		//ENEMY COLLIDING
		for (int i = 0; i < axebots.size; i++) {
			if (A.getBody().getUserData() == axebots.get(i).getBody().getUserData() || B.getBody().getUserData() == axebots.get(0).getBody().getUserData()) {
				if (A.getBody().getUserData() == megaman.getBody().getUserData() || B.getBody().getUserData() == megaman.getBody().getUserData()) {
					if (sameController.getDirection())
						megaman.getBody().setLinearVelocity(75, 1);
					else
						megaman.getBody().setLinearVelocity(-75, 1);
					HUD.removeLife();
				}
			}
		}
}
	
	@Override
	public void endContact(Contact contact) {
		reset = false;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
	
	public boolean getReset() {
		return reset;
	}

}
