package gui.battle.unique;

import gamestate.GameStateMachine;
import gui.childElements.GuiBox;
import gui.childElements.GuiElement;
import gui.childElements.GuiTextBuilder;
import main.Main;

/**
 * Created by dwbrite on 5/15/16.
 */
public class GuiBattleTextWwpd extends GuiElement {
	private int width = Main.WIDTH / 8, height = 6;
	
	private int x = 0, y = Main.HEIGHT - (8 * height);
	
	private GuiBox box;
	
	private GuiTextBuilder guitext;
	
	private String text;
	
	public GuiBattleTextWwpd(String text) {
		super(false); // This does *not* need priority
		box = new GuiBox(x, y, width, height, GuiBox.BORDER_BATTLE);
		guitext = new GuiTextBuilder(text);
		guitext.setPosition(x + 12, y + 10);
		this.text = text;
		this.setGuiManager(GameStateMachine.getGameState(1).getGuiManager());
	}
	
	@Override
	public void render() {
		box.draw();
		guitext.draw();
	}
	
	@Override
	public void uniqueUpdate() {
	
	}
}
