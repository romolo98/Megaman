package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import static com.megaman.game.Utils.Constants.*;

public class Controller{
	
	static boolean[] controlli;
	static boolean shot;
	boolean direction;
	int contJump;
	double lastTime = 0;
	double actualTime = 0;
	double delay = 1200;
	
	
	public Controller () {
		controlli = new boolean[9];
		contJump = 0;
		direction = false; //FALSE = DESTRA
	}

	public boolean getDirection() {
		return direction;
	}
	
	public void setDirection(boolean dir) {
		direction = dir;
	}
	
	public double getLastTime () {
		return lastTime;
	}
	
	public void muoviMegaman (Megaman megaman) {
		
		actualTime = System.currentTimeMillis();
		
		//LEFT
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			direction = true;
			controlli[WALK_START] = true;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			direction = true;
					
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !controlli[WALK_JUMP]) {
				controlli[WALK_JUMP] = true;
			}
			else if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT)) {
				lastTime = System.currentTimeMillis();
				shot = true;
				controlli[WALK_SHOOT] = true;
			}
			else {
				controlli[WALK] = true;
			}
		}
		
		//RIGHT
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT) && !controlli[JUMP]) {
			direction = false;
			controlli[WALK_START] = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			direction = false;
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !controlli[WALK_JUMP]) {
				controlli[WALK_JUMP] = true;
			}
			else if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT)) {
				lastTime = System.currentTimeMillis();
				shot = true;
				controlli[WALK_SHOOT] = true;
			}
			else {
				controlli[WALK] = true;
			}
		}

		if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) && !controlli[WALK_SHOOT]) {
			controlli[SHOOT] = true;
			shot = true;
		}
		
		
		if (isAllFalseExeptIdle()) {
			controlli[IDLE] = true;
		}
		else {
			controlli[IDLE] = false;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !controlli[WALK_JUMP] && !controlli[FALL]) {
			controlli[JUMP] = true;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (controlli[FALL]) {
			controlli[JUMP] = false;
			controlli[WALK_JUMP] = false;
		}

	}
	
	public boolean isAllFalseExeptIdle() {
		for (int i=0;i<controlli.length-1;i++) {
			if (controlli[i]) {
				return false;
			}
		}
		return true;
	}
	
	public void setControlliFalse (int index) {
		controlli[index] = false;
	}
	
	public void setFalseExcept (int index) {
		for (int i = 0; i < 6; i++)
			if (i != index)
			controlli[i] = false;
	}
	
	public boolean getControlli (int index) {
		return controlli[index];
	}
	
	public void setFallTrue () {
		controlli[FALL] = true;
	}
	
}
