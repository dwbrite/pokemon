package gui;

import gui.childElements.GuiElement;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by dwbrite on 5/12/16.
 */
public class GuiManager {
	private ArrayList<GuiElement> guiElements = new ArrayList<>();
	
	public void init(GameContainer gc) {
		//??
	}
	
	public void update(GameContainer gc) {
		for (int i = 0; i < guiElements.size(); i++)
			guiElements.get(i).update();
	}
	
	public void render(GameContainer gc, Graphics g) {
		for (int i = 0; i < guiElements.size(); i++)
			guiElements.get(i).render();
	}
	
	public void addGuiElement(GuiElement gel) {
		guiElements.add(gel);
	}
	
	public void removeGuiElement(GuiElement gel) {
		guiElements.remove(gel);
	}
	
	public void removeElement(int n) {
		guiElements.remove(n);
	}
	
	public GuiElement getElement(int n) { return guiElements.get(n); }
}
