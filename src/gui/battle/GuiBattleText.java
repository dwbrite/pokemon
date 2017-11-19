package gui.battle;

import gamestate.GameStateMachine;
import gui.childElements.GuiBox;
import gui.childElements.GuiElement;
import gui.childElements.GuiTextBuilder;
import main.Main;

/**
 * Created by dwbrite on 5/14/16.
 */
public class GuiBattleText extends GuiElement {
	public int finishTimer = 30; //60 = 1s
	
	private int width = Main.WIDTH / 8, height = 6;
	
	private int x = 0, y = Main.HEIGHT - (8 * height);
	
	private GuiBox box;
	
	private GuiTextBuilder guitext;
	
	private String text;
	
	private boolean textHasFinished;
	
	private boolean hasFinished = false;
	
	private int stringPosition = 0;
	
	private int finishedWait = 0;
	
	public GuiBattleText(String text, int finishTimer) {
		super(false);
		box = new GuiBox(x, y, width, height, GuiBox.BORDER_BATTLE);
		guitext = new GuiTextBuilder("");
		guitext.setPosition(x + 12, y + 10);
		this.text = text;
		this.finishTimer = finishTimer;
		this.setGuiManager(GameStateMachine.getGameState(1).getGuiManager());
	}
	
	@Override
	public void render() {
		box.draw();
		guitext.draw();
	}
	
	@Override
	public void uniqueUpdate() {
		if (textHasFinished) {
			finishedWait++;
		} else {
			if (stringPosition == text.length() + 1) {
				textHasFinished = true;
			} else if (Main.ticks % 4 == 0) {
				guitext.setString(text.substring(0, stringPosition));
				stringPosition++;
			}
		}
		checkFinished();
	}
	
	public void checkFinished() {
		if (finishedWait >= finishTimer) {
			finishedAction();
		}
	}
	
	public void finishedAction() {
	
	}
}
