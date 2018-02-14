package gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class AbstractGameState {
	public abstract void render(GameContainer gc, Graphics g);
	public abstract void update(GameContainer gc);
}
