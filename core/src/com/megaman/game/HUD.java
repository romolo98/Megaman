package com.megaman.game;

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
		if (HP > 0)
			HP--;
	}
	
	public static void removeLife (int damage) {
		for (int i = 0; i < damage; i++) {
			if (HP == 0 && HB == 0)
				return;
			if (HP == 0 && HB > 0) 
					resetLife();	
				HP--;
			} 
	}
	public static void addBar() {
		if (HB < 4)
			HB++;
	}
	
	public static void removeBar() {
		if (HB > 0)
			HB--;
	}
	
	public static void resetLife () {
		HP = 5;
	}
	
	public void updateLife () {
		if (HP == 0) {
			if (HB > 0) {
				removeBar();
				resetLife();
			}
			if (HB == 0 && HP == 0) {
				System.out.println("SARESTI TEORICAMENTE MORTO");
			}
		}
	}
}
