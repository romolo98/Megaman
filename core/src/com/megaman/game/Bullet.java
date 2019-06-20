package com.megaman.game;

public class Bullet extends Entity {
	public Bullet() {
		super();
	}

	public void physics (boolean update) {
		if (update) {
			this.setPositionX(this.getPositionX()+7);
		}
	}
	
}
