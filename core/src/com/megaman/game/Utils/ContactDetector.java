package com.megaman.game.Utils;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.megaman.logic.Boss;
import com.megaman.logic.Bullet;
import com.megaman.logic.Controller;
import com.megaman.logic.Enemy;
import com.megaman.logic.Entity;
import com.megaman.logic.HUD;
import com.megaman.logic.Megaman;

public class ContactDetector implements ContactListener{
	
	private boolean fall = false;
	private Boss levelboss;
	private Array<Entity> death;
	private Megaman megaman;
	private Array<Enemy> axebots;
	private Array<Enemy> cannons;
	private boolean reset;
	private boolean mustFall;
	private Array<Bullet> ammo;
	private Array<Bullet> lostAmmo;
	
	public ContactDetector(Array<Entity> deathzones, Megaman m, Array<Bullet> ammunition, Array<Enemy> axebot, Array<Enemy> cannon, Controller controller, Boss boss) {
		death = deathzones;
		megaman = m;
		reset = false;
		mustFall = false;
		ammo = ammunition;
		lostAmmo = new Array<Bullet>();
		axebots = axebot;
		cannons = cannon;
		levelboss = boss;
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
		
		//ENEMY COLLIDING AXEBOT
		for (int i = 0; i < axebots.size; i++) {
			if (!axebots.get(i).getIsDead()) {
				if (A.getBody().getUserData() == axebots.get(i).getBody().getUserData() || B.getBody().getUserData() == axebots.get(i).getBody().getUserData()) {
					if (A.getUserData() == "feet" || B.getUserData() == "feet") {
						megaman.getBody().setLinearVelocity(-75, 3);
						HUD.removeLife();
					}
					else if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
						megaman.getBody().setLinearVelocity(-75, 0);
						HUD.removeLife();
					}
					else if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
						megaman.getBody().setLinearVelocity(75, 0);
						HUD.removeLife();
					}
				}
			}
		}
		//ENEMY COLLIDING CANNON
		for (int i = 0; i < cannons.size; i++) {
			if (!cannons.get(i).getIsDead()) {
				if (A.getBody().getUserData() == cannons.get(i).getBody().getUserData() || B.getBody().getUserData() == cannons.get(i).getBody().getUserData()) {
					if (A.getUserData() == "feet" || B.getUserData() == "feet") {
						megaman.getBody().setLinearVelocity(-75, 3);
						HUD.removeLife();
					}
					else if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
						megaman.getBody().setLinearVelocity(-75, 0);
						HUD.removeLife();
					}
					else if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
						megaman.getBody().setLinearVelocity(75, 0);
						HUD.removeLife();
					}
				}
			}
		}
		
		//BOSS COLLISIONS
		if (!levelboss.getIsDead()) {
			if (A.getBody().getUserData() == levelboss.getBody().getUserData() || B.getBody().getUserData() == levelboss.getBody().getUserData()) {
				if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
					megaman.getBody().setLinearVelocity(150, 0);
					HUD.removeLife(levelboss.getPunchDamage());
				}
				else if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
					megaman.getBody().setLinearVelocity(-150, 0);
					HUD.removeLife(levelboss.getPunchDamage());
				}
				else if (A.getUserData() == "feet" || B.getUserData() == "feet") {
					megaman.getBody().setLinearVelocity(0, 3);
					HUD.removeLife(levelboss.getPunchDamage());
				}
			}
		
		//BOSS BULLETS COLLISIONS 
		for (int i = 0; i < levelboss.getBossBullets().size; i++) {
			if (A.getBody().getUserData() == levelboss.getBossBullets().get(i).getBody().getUserData() || B.getBody().getUserData() == levelboss.getBossBullets().get(i).getBody().getUserData()) {
				if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
					megaman.getBody().setLinearVelocity(-75, 0);
					HUD.removeLife(levelboss.getBulletDamage());
					levelboss.getBossBullets().get(i).setMustDie();
				}
				else if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
					megaman.getBody().setLinearVelocity(75, 0);
					HUD.removeLife(levelboss.getBulletDamage());
					levelboss.getBossBullets().get(i).setMustDie();
				}
				else if (A.getUserData() == "feet" || B.getUserData() == "feet") {
					megaman.getBody().setLinearVelocity(0, 3);
					HUD.removeLife(levelboss.getPunchDamage());
					levelboss.getBossBullets().get(i).setMustDie();
				}
				else if (A.getBody().getUserData() == "ground" || B.getBody().getUserData() == "ground") {
					levelboss.getBossBullets().get(i).setMustDie();
				}
			}
		}
		
		for (int i = 0; i < levelboss.getBossBullets().size; i++) {
			if (levelboss.getBossBullets().get(i).getMustDie()) {
				levelboss.addBulletsToDestroy(levelboss.getBossBullets().get(i));
			}
		}
		
		//ENEMIES BULLETS COLLISIONS 
			for (int i = 0; i < cannons.size; i++) {
				for (int j = 0; j < cannons.get(i).getEnemiesBullets().size; j++) {
					if (A.getBody().getUserData() == cannons.get(i).getEnemiesBullets().get(j).getBody().getUserData() || B.getBody().getUserData() == cannons.get(i).getEnemiesBullets().get(j).getBody().getUserData()) {
						if (A.getUserData() == "rightSide" || B.getUserData() == "rightSide") {
							megaman.getBody().setLinearVelocity(-75, 0);
							HUD.removeLife(cannons.get(i).getDamage());
							cannons.get(i).getEnemiesBullets().get(j).setMustDie();
						}
						else if (A.getUserData() == "leftSide" || B.getUserData() == "leftSide") {
							megaman.getBody().setLinearVelocity(75, 0);
							HUD.removeLife(cannons.get(i).getDamage());
							cannons.get(i).getEnemiesBullets().get(j).setMustDie();
						}
						else if (A.getUserData() == "feet" || B.getUserData() == "feet") {
							megaman.getBody().setLinearVelocity(0, 3);
							HUD.removeLife(cannons.get(i).getDamage());
							cannons.get(i).getEnemiesBullets().get(j).setMustDie();
						}
						else if (A.getBody().getUserData() == "ground" || B.getBody().getUserData() == "ground") {
							cannons.get(i).getEnemiesBullets().get(j).setMustDie();
						}
					}
				}
			}
			
			for (int j = 0; j < cannons.size; j++) {
				for (int i = 0; i < cannons.get(j).getEnemiesBullets().size; i++) {
					if (cannons.get(j).getEnemiesBullets().get(i).getMustDie()) {
						cannons.get(j).addBulletsToDestroy(cannons.get(j).getEnemiesBullets().get(i));
					}
				}
			}
		
	//MEGAMAN BULLET AGAINST BOSS
			for(int i = 0; i < ammo.size; i++) {
				if (A.getBody().getUserData() == ammo.get(i).getBody().getUserData() || B.getBody().getUserData() == ammo.get(i).getBody().getUserData()) {
					if (A.getBody().getUserData() == levelboss.getBody().getUserData() || B.getBody().getUserData() == levelboss.getBody().getUserData()) {
						levelboss.removeLife();
						lostAmmo.add(ammo.get(i));
						ammo.get(i).setMustDie();
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
									ammo.get(i).setMustDie();
									axebots.get(j).removeLife();
					}
				}
			}
		
		//BULLETS COLLISIONS WITH ENEMIES
				for (int i = 0; i < ammo.size; i++) {
						for (int j = 0; j < cannons.size; j++) {
							if (!cannons.get(j).getIsDead())
								if (A.getBody().getUserData() == cannons.get(j).getBody().getUserData() || B.getBody().getUserData() == cannons.get(j).getBody().getUserData())
										if (A.getBody().getUserData() == ammo.get(i).getBody().getUserData() || B.getBody().getUserData() == ammo.get(i).getBody().getUserData()) {
											lostAmmo.add(ammo.get(i));
											ammo.get(i).setMustDie();
											cannons.get(j).removeLife();
							}
						}
					}
		
	//MEGAMAN BULLETS COLLISION WITH MAP
		for (int i = 0; i < ammo.size; i++) {
			if (A.getBody().getUserData() == ammo.get(i).getBody().getUserData() || B.getBody().getUserData() == ammo.get(i).getBody().getUserData()) {
				if (A.getBody().getUserData() == "ground" || B.getBody().getUserData() == "ground") {
					lostAmmo.add(ammo.get(i));
					ammo.get(i).setMustDie();
				}
			}
		}
}
	
	@Override
	public void endContact(Contact contact) {
		reset = false;
		Fixture A = contact.getFixtureA();
		Fixture B = contact.getFixtureB();
		

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
	
	public boolean getMustFall () {
		return mustFall;
	}

	public void setMustFallFalse (){
		mustFall = false;
	}
	
	public boolean getFall () {
		return fall;
	}
}
