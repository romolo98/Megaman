package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.sun.javafx.fxml.expression.KeyPath;

import static com.megaman.game.Utils.Constants.*;

public class Controller{
	
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
		controlli = new boolean[12];
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
	
	public void muoviMegaman (Megaman megaman) {
		
		actualTime = System.currentTimeMillis();

		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		if (!start) {

			if (Gdx.input.isKeyJustPressed(Keys.L))
			{
				HUD.removeLife();
			}
			if (Gdx.input.isKeyJustPressed(Keys.R)) {
				HUD.resetLife();
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.A)) {
				HUD.addLife();
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.T))
			{
				controlli[SPAWN] = true;
				megaman.respawn();
			}
			
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) && controlli[WALK_SHOOT]) {
				controlli[JUMP_SHOOT] = true;
				controlli[WALK_SHOOT] = false;
				shot = true;
				System.out.println("entro");
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
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
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
			
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
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
			
			if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !controlli[WALK_JUMP] && !controlli[FALL]) {
				controlli[JUMP] = true;
			}
	
			if (isAllFalseExeptIdle()) {
				controlli[IDLE] = true;
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
