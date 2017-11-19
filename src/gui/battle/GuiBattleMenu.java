package gui.battle;

import gamestate.GameStateMachine;
import gui.GuiMenu;
import gui.childElements.GuiBox;
import gui.childElements.GuiMenuOption;
import gui.childElements.GuiTextBuilder;

/**
 * Created by dwbrite on 5/14/16.
 */
public class GuiBattleMenu extends GuiMenu {
	public GuiBattleMenu(GuiBox box, GuiMenuOption[][] options) {
		super(box, options);
		this.setGuiManager(GameStateMachine.getGameState(1).getGuiManager());
	}

	@Override
	public void render() {
		box.draw();
		GuiTextBuilder tb = new GuiTextBuilder(""); //TODO: wtf man :/ everything is beaned dude.
	}
}