package com.megaman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicsManager {
	
	
	GraphicLoader graphicLoader;
	Megaman megaman;
	Controller controller;
	float elapsed;
	float salto;
	
	public GraphicsManager (){
		elapsed = 0;
		salto = 0;
		controller = new Controller();
		megaman = new Megaman();
		graphicLoader = new GraphicLoader();
		graphicLoader.importImage();
	}
	public void draw(SpriteBatch batch) {
		elapsed += Gdx.graphics.getDeltaTime();
		controller.muoviMegaman(megaman);
		
		if (controller.controlli[controller.WALK_START]) {
			batch.draw(graphicLoader.getInizioWalk(),megaman.getPositionX(),megaman.getPositionY());
			controller.setControlliFalse(controller.WALK_START);
		}
		if (controller.getControlli(controller.FALL)) {
			batch.draw(graphicLoader.getFall().getKeyFrame(elapsed, true), megaman.getPositionX(), megaman.getPositionY());
		}
		if (controller.getControlli(controller.WALK)) {
			if (controller.getControlli(controller.WALK_JUMP)) {
				controller.setControlliFalse(controller.WALK);
				salto+= Gdx.graphics.getDeltaTime();
				batch.draw(graphicLoader.getJump().getKeyFrame(elapsed, true), megaman.getPositionX(), megaman.getPositionY());
				if (graphicLoader.getJump().isAnimationFinished(salto)) {
					controller.setControlliFalse(controller.WALK_JUMP);
					salto = 0;
					controller.setFallTrue();
				}
			}
			else if (!controller.getControlli(controller.FALL)){ 
					batch.draw(graphicLoader.getWalk().getKeyFrame(elapsed, true), megaman.getPositionX(),megaman.getPositionY());
					controller.setControlliFalse(controller.WALK_JUMP);					
					controller.setControlliFalse(controller.WALK);
			}
		}
		if (controller.getControlli(controller.JUMP)) {
			batch.draw(graphicLoader.getJump().getKeyFrame(elapsed), megaman.getPositionX(), megaman.getPositionY());
			salto+= Gdx.graphics.getDeltaTime();
			if (graphicLoader.getJump().isAnimationFinished(salto)) {
				controller.setControlliFalse(controller.JUMP);
				salto = 0;
				controller.setFallTrue();
			}
		}
		else {
			controller.setTuttiControlliFalse();
			batch.draw(graphicLoader.getIdle().getKeyFrame(elapsed/2,true), megaman.getPositionX(), megaman.getPositionY());
		}
	
		
	}
}









