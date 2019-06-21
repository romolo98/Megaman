package com.megaman.game;

public class HUD {

	private int HP; 
	
	public HUD () {
		HP = 5;
	}

	
	public int getLife () {
		return HP;
	}
	
	public void setLife (boolean damage, boolean healing) {
		
		if (damage)
			HP--;
		if (healing)
			HP++;
	}
	
	public void resetLife () {
		HP = 5;
	}
}
