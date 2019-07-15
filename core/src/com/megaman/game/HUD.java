package com.megaman.game;

public class HUD {

	private static int HP; 
	
	public HUD () {
		HP = 5;
	}
	
	public int getLife () {
		return HP;
	}
	
	public static void addLife () {
		if (HP < 5)
			HP++;
	}
	
	public static void removeLife () {
		if (HP > 0)
			HP--;
	}
	
	public static void resetLife () {
		HP = 5;
	}
}
