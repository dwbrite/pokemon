package main;

import entities.pokemon.Move;
import gameState.GameStateManager;
import handlers.Resources;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {
	
	public Game(String title) {
		super(title);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (!Resources.isReady()) {
			Resources.getSplashImage().draw();
		} else {
			GameStateManager.render(gc, g);
		}
	}
	
	public void init(GameContainer gc) throws SlickException {
		Resources.init();
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		if (!Resources.isReady()) {
			Resources.initPool();
			Move m = new Move(8);
			m.selectTarget();
			m.execute();
			GameStateManager.init(gc);
			Resources.INSTANCE.setReady(true);
		} else {
			GameStateManager.update(gc);
		}
		Main.ticks++;
	}
	
}
