package com.megaman.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.megaman.game.Utils.ContactDetector;

import static com.megaman.game.Utils.Constants.*;

public class Controller{
	
	int contMenu = 0;
	static boolean[] controlli;
	boolean notWalkRight;
	boolean notWalkLeft;
	static boolean shot;
	boolean direction;
	int contJump;
	double lastTime = 0;
	double actualTime = 0;
	double delay = 1200;
	boolean start;
	
	
	public Controller () {
		controlli = new boolean[14];
		notWalkRight = false;
		notWalkLeft = false;
		contJump = 0;
		direction = false; //FALSE = DESTRA
		start = false;
	}

	public boolean getDirection() {
		return direction;
	}
	
	public void setStart (boolean s) {
		start = s;
	}
	
	public void setDirection(boolean dir) {
		direction = dir;
	}
	
	public double getLastTime () {
		return lastTime;
	}
	
	public boolean getNotRight () {
		return notWalkRight;
	}
	public boolean getNotLeft () {
		return notWalkLeft;
	}
	
	public void winner () {
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			controlli[ENTER] = true;
		}
	}
	
	public void credits () {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			controlli[EXIT] = true;
		}
	}
	
	public int muoviMenu () {
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			controlli[EXIT] = true;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			contMenu -=1;
		}
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			contMenu +=1;
		}
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			controlli[ENTER] = true;
		}
		
		if (contMenu > 2) {
			contMenu = 0;
		}
		if (contMenu < 0) {
			contMenu = 2;
		}
		
		return contMenu;
	}
	
	public void muoviMegaman (Megaman megaman, ContactDetector detector) {
		
		actualTime = System.currentTimeMillis();
		
		if (!start) {

			if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
				controlli[EXIT] = true;
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.T))
			{
				controlli[SPAWN] = true;
				megaman.respawn();
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) && controlli[WALK_SHOOT]  && !detector.getMustFall()) {
				controlli[JUMP_SHOOT] = true;
				controlli[WALK_SHOOT] = false;
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) && (controlli[JUMP] || controlli[WALK_JUMP] || controlli[JUMP_SHOOT]) && !controlli[FALL]) {
				controlli[JUMP_SHOOT] = true;
				shot = true;
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) && controlli[FALL_SHOOT]) {
				shot = true;
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) && !controlli[JUMP] && !controlli[WALK_JUMP] && controlli[FALL]) {
				controlli[FALL_SHOOT] = true;
				controlli[FALL] = false;
				shot = true;
			}
			
			//LEFT
			if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
				direction = true;
				controlli[WALK_START] = true;
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT) && !detector.getMustFall()) {
				direction = true;
						
				if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !controlli[WALK_JUMP] && !controlli[JUMP_SHOOT]) {
					controlli[WALK_JUMP] = true;
				}
				else if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) && !controlli[JUMP_SHOOT] && !controlli[FALL_SHOOT]) {
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
			
			if (Gdx.input.isKeyPressed(Keys.RIGHT) && !detector.getMustFall()) {
				direction = false;
				if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !controlli[WALK_JUMP] && !controlli[JUMP_SHOOT]) {
					controlli[WALK_JUMP] = true;
				}
				else if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) && !controlli[JUMP_SHOOT] && !controlli[FALL_SHOOT]) {
					lastTime = System.currentTimeMillis();
					shot = true;
					controlli[WALK_SHOOT] = true;
				}
				else {
					controlli[WALK] = true;
				}
			}
			
			if (!Gdx.input.isKeyPressed(Keys.RIGHT)) {
				notWalkRight = true;
			}
			else {
				notWalkRight = false;
			}
			
			if (!Gdx.input.isKeyPressed(Keys.LEFT)) {
				notWalkLeft = true;
			}
			else {
				notWalkLeft = false;
			}
	
			if (Gdx.input.isKeyJustPressed(Keys.CONTROL_LEFT) && !controlli[WALK_SHOOT] && !controlli[JUMP] && !controlli[FALL] && !controlli[JUMP_SHOOT] && !controlli[FALL_SHOOT]) {
				controlli[SHOOT] = true;
				shot = true;
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !controlli[FALL]  && !detector.getMustFall()) {
				controlli[JUMP] = true;
			}
	
			if (isAllFalseExeptIdle()) {
				controlli[IDLE] = true;
				detector.setMustFallFalse();
			}
			else {
				controlli[IDLE] = false;
			}
		}
	}
	
	public boolean isAllFalseExeptIdle() {
		for (int i=0;i<controlli.length;i++) {
			if (controlli[i] && i != IDLE) {
				return false;
			}
		}
		return true;
	}
	
	public void setControlliFalse (int index) {
		controlli[index] = false;
	}
	
	public void setFalseExcept (int index) {
		for (int i = 0; i < controlli.length; i++)
			if (i != index)
				controlli[i] = false;
	}
	
	public void setFalseExcept (int index,int index2,int index3) {
		for (int i = 0; i < controlli.length; i++)
			if (i != index || i!=index2 || i!= index3)
				controlli[i] = false;
	}
	
	public boolean getControlli (int index) {
		return controlli[index];
	}
	
	public void setFallTrue () {
		controlli[FALL] = true;
	}
	
	public void setControlliTrue (int index) {
		controlli[index] = true;
	}
}
