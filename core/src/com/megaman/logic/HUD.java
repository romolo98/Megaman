package com.megaman.logic;

public class HUD {

	private static int HP; 
	private static int HB;
	
	public HUD () {
		HP = 5;
		HB = 5;
	}
	
	public int getLife () {
		return HP;
	}
	
	public int getHealthBars () {
		return HB;
	}
	
	public static void addLife () {
		if (HP < 5)
			HP++;
	}
	
	public static void removeLife () {
		if (HP > 0) {
			HP--;
		if (HP == 0 && HB > 0) {
			resetLife();
			removeBar();
			}
		if (HP == 0 && HB == 0)
			return;
		}
	}
	
	public static void removeLife (int damage) {
		for (int i = 0; i < damage; i++) {
			HP--;
			if (HP == 0 && HB > 0) {
				resetLife();
				removeBar();
			}
			if (HP <= 0 && HB == 0)
				HP = 0;
		}
	}
	public static void addBar() {
		if (HB < 5)
			HB++;
	}
	
	public static void removeBar() {
		if (HB > 0)
			HB--;
	}
	
	public static void resetLife () {
		HP = 5;
	}
}
