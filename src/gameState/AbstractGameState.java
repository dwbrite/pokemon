package gameState;

import gui.GuiManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class AbstractGameState {
	protected GuiManager guiman = new GuiManager();
	
	public abstract void render(GameContainer gc, Graphics g);
	
	public abstract void update(GameContainer gc);
	
	public GuiManager getGuiManager() {
		return guiman;
	}
}
