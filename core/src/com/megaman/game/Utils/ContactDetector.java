package com.megaman.game.Utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.megaman.game.Boss;
import com.megaman.game.Bullet;
import com.megaman.game.Controller;
import com.megaman.game.Enemy;
import com.megaman.game.Entity;
import com.megaman.game.HUD;
import com.megaman.game.Megaman;
import com.megaman.game.gameManager;

public class ContactDetector implements ContactListener{
	
	private Boss levelboss;
	private boolean collidingBullet;
	private Array<Entity> death;
	private Megaman megaman;
	private boolean onTheGround;
	private Array<Enemy> axebots;
	private boolean reset;
	private boolean mustFall;
	private Array<Bullet> ammo;
	private Array<Bullet> lostAmmo;
	private Controller sameController;
	private Array<Enemy> destroyedEnemies;
	int ciao = 0;
	
	public ContactDetector(Array<Entity> deathzones, Megaman m, Array<Bullet> ammunition, Array<Enemy> axebot, Controller controller, Boss boss) {
		death = deathzones;
		megaman = m;
		reset = false;
		onTheGround = false;
		mustFall = false;
		ammo = ammunition;
		lostAmmo = new Array<Bullet>();
		axebots = axebot;
		sameController = controller;
		collidingBullet = false;
		levelboss = boss;
		destroyedEnemies = new Array<Enemy>();
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture A = contact.getFixtureA();
		Fixture B = contact.getFixtureB();
		
		if (A == null || B == null) return;
		if (A.getBody().getUserData() == null || B.getBody().getUserData() == null) return;
		
		//DEATHZONE COLLISIONS
		for (int i = 0; i < death.size; i++) {
			if (A.getBody().getUserData() == death.get(i).getBody().getUserData() || B.getBody().getUserData() == death.get(i).getBody().getUserData()) {
				if (A.getBody().getUserData() == megaman.getBody().getUserData() || B.getBody().getUserData() == megaman.getBody().getUserData()) {
					reset = true;
				}
			}
		}
		
		//GROUND COLLISIONS
		if (A.getUserData() == "feet" || B.getUserData() == "feet") {
				onTheGround = true;
		}
		
		//LEFT COLLISIONS
		if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
			if (A.getBody().getUserData() == "ground" || B.getBody().getUserData() == "ground") {
				mustFall = true;
			}
		}
		//RIGHT COLLISIONS
		if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
			if (A.getBody().getUserData() == "ground" || B.getBody().getUserData() == "ground") {
				mustFall = true;
			}
		}
		
		//ENEMY COLLIDING 
		for (int i = 0; i < axebots.size; i++) {
			if (!axebots.get(i).getIsDead()) {
				if (A.getBody().getUserData() == axebots.get(i).getBody().getUserData() || B.getBody().getUserData() == axebots.get(i).getBody().getUserData()) {
					if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
							megaman.getBody().setLinearVelocity(-75, 1);
						HUD.removeLife();
					}
					else if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
							megaman.getBody().setLinearVelocity(75, 1);
						HUD.removeLife();
				}
			}
		}
	}		
		
		//BOSS COLLISIONS
		
		if (!levelboss.getIsDead()) {
			if (A.getBody().getUserData() == levelboss.getBody().getUserData() || B.getBody().getUserData() == levelboss.getBody().getUserData()) {
				if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
						megaman.getBody().setLinearVelocity(150, 1);
						HUD.removeLife(levelboss.getPunchDamage());
				}
				else if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
						megaman.getBody().setLinearVelocity(-150, 1);
						HUD.removeLife(levelboss.getPunchDamage());
				}
			}
		
		//BOSS BULLETS COLLISIONS 
			if (A.getBody().getUserData() == "bossBullet" || B.getBody().getUserData() == "bossBullet") {
				if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
						megaman.getBody().setLinearVelocity(-75, 1);
						HUD.removeLife(levelboss.getBulletDamage());
						levelboss.addBulletsToDestroy(levelboss.getBossBullets().peek());
				}
				else if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
					megaman.getBody().setLinearVelocity(75, 1);
						HUD.removeLife(levelboss.getBulletDamage());
						levelboss.addBulletsToDestroy(levelboss.getBossBullets().peek());
				}
			}
			
			/*if (A.getBody().getUserData() == "bossBullet" || B.getBody().getUserData() == "bossBullet") {
				if (A.getBody().getUserData() == "ground" || B.getBody().getUserData() == "ground")
					levelboss.addBulletsToDestroy(levelboss.getBossBullets().peek());
			}*/
			
			
			for(int i = 0; i < ammo.size; i++) {
				if (A.getBody().getUserData() == ammo.get(i).getBody().getUserData() || B.getBody().getUserData() == ammo.get(i).getBody().getUserData()) {
					if (A.getBody().getUserData() == levelboss.getBody().getUserData() || B.getBody().getUserData() == levelboss.getBody().getUserData()) {
						levelboss.removeLife();
						lostAmmo.add(ammo.get(i));
					}
				}
			}
		}
		
	//BULLETS COLLISIONS WITH ENEMIES
		for (int i = 0; i < ammo.size; i++) {
				for (int j = 0; j < axebots.size; j++) {
					if (!axebots.get(j).getIsDead())
						if (A.getBody().getUserData() == axebots.get(j).getBody().getUserData() || B.getBody().getUserData() == axebots.get(j).getBody().getUserData())
								if (A.getBody().getUserData() == ammo.get(i).getBody().getUserData() || B.getBody().getUserData() == ammo.get(i).getBody().getUserData()) {
									lostAmmo.add(ammo.get(i));
									axebots.get(j).removeLife();
					}
				}
			}
		
	/*//MEGAMAN BULLETS COLLISION WITH MAP
		for (int i = 0; i < ammo.size; i++) {
			if (A.getBody().getUserData() == ammo.get(i).getBody().getUserData() || B.getBody().getUserData() == ammo.get(i).getBody().getUserData()) {
				if (A.getBody().getUserData() == "ground" || B.getBody().getUserData() == "ground")
					lostAmmo.add(ammo.get(i));
			}
		}
	*/
}
	
	@Override
	public void endContact(Contact contact) {
		reset = false;
		Fixture A = contact.getFixtureA();
		Fixture B = contact.getFixtureB();
		
		if (A.getUserData() != "feet" && B.getUserData() != "feet") {
			onTheGround = false;
		}
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
	
	public Array<Bullet> ammoToRemove () {
		return lostAmmo;
	}
	
	public void allLostAmmo () {
		lostAmmo.clear();
	}
	
	public boolean getOnTheGround () {
		return onTheGround;
	}
	public boolean getMustFall () {
		return mustFall;
	}

	public void setMustFallFalse (){
		mustFall = false;
	}
}
