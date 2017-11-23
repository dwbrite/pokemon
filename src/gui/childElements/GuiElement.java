package gui.childElements;

import gui.GuiManager;
import handlers.controls.Controls;

/**
 * Created by dwbrite on 5/12/16.
 */
public abstract class GuiElement {
	protected GuiManager guiman;
	
	public GuiElement(boolean controlPriority) {
		if (controlPriority) {
			//Controls.INSTANCE.givePriority(this);
		}
	}
	
	public abstract void render();
	
	public void update() {
		if (guiman == null) {
			throw new NullPointerException("You must set a GuiManager for this object.");
		}
		uniqueUpdate();
	}
	
	public abstract void uniqueUpdate();
	
	public void remove() {
		//Controls.INSTANCE.removePriority(this);
		guiman.removeGuiElement(this);
	}
	
	public void setGuiManager(GuiManager guiman) {
		this.guiman = guiman;
		guiman.addGuiElement(this);//TODO: this shit //future note: maybe not?
	}
}
