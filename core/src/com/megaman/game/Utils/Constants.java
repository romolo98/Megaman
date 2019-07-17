package com.megaman.game.Utils;

public class Constants {
	
	//PIXEL PER METER
	public static final float PPM = 64; //UNITÀ DI CONVERSIONE METRI PIXEL PER LIBGDX
	
	//ANIMATION MEGAMAN
	public static final int WALK = 0;
	public static final int JUMP = 1;
	public static final int FALL = 2;
	public static final int SHOOT = 3;
	public static final int WALK_SHOOT = 4;
	public static final int WALK_JUMP = 5;
	public static final int WALK_START = 6;
	public static final int QUIT = 7;
	public static final int IDLE = 8;
	public static final int SPAWN = 9;
	public static final int JUMP_SHOOT = 10;
	public static final int FALL_SHOOT = 11;
	
	//ANIMATION BOSS
	public static final int BOSS_PUNCH = 0;
	public static final int BOSS_WALK = 1;
	public static final int BOSS_JUMP = 2;
	
	//FIXTURE FILTERS
	public static final short DEFAULT_BIT = 1; 
	public static final short MEGAMAN_BIT = 2;
	public static final short ENEMY_BIT = 4;
}
