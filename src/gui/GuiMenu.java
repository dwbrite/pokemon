package gui;

import gameState.GameStateManager;
import gui.childElements.GuiBox;
import gui.childElements.GuiElement;
import gui.childElements.GuiMenuOption;
import gui.childElements.GuiTextBuilder;

/**
 * Created by dwbrite on 5/12/16.
 */
public class GuiMenu extends GuiElement {
	
	protected GuiBox box;
	
	protected GuiMenuOption[][] options;
	
	protected int optionsX;
	
	protected int optionsY;
	
	protected int lastInputCheck;
	
	protected boolean multipleColumns = false;
	
	protected int numRows[];
	
	protected boolean canInput = true;
	
	protected boolean didInput = false;
	
	protected GuiElement[] parentElements = null;
	
	public GuiMenu(GuiBox box, GuiMenuOption[][] options) {
		super(true);
		this.box = box;
		this.options = options;
		multipleColumns = options.length > 1;
		numRows = new int[options[0].length];
		for (int i = 0; i < options.length; i++) {
			numRows[i] = options[i].length;
		}
		optionsX = 0;
		optionsY = 0;
	}
	
	@Override
	public void render() {
		box.draw();
		GuiTextBuilder tb = new GuiTextBuilder("");
		//...
	}
	
	@Override
	public void uniqueUpdate() {

	}

//TODO: fix
    /*/
        if(true) {
            //        = 0; // Presumably optionsY = 0;
            if(wasAtTop)
                optionsY = numRows[optionsX] - 1;
            else
                optionsY--;
        } else if (Controls.getInput(Controls.DOWN, this)) {
            boolean wasAtBot = optionsY == numRows[optionsX] - 1;
            if(wasAtBot)
                optionsY = 0;
            else
                optionsY++;
        } else if (Controls.getInput(Controls.LEFT, this)) {
        } else if (Controls.getInput(Controls.RIGHT, this)) {
        } else if (Controls.getInput(Controls.A, this)) {
            options[optionsX][optionsY].activate();
        } else {
            canInput = true;
        }
                if(!canInput) {
            lastInputCheck = 0;
        }
        lastInputCheck++;
    }
        }
        //*/
	
	public void back() {
		if (parentElements != null) {
			//remove self
			remove();
			//add parent elements
			for (int i = parentElements.length - 1; i >= 0; i--) {
				guiman.addGuiElement(parentElements[i]);
			}
		} else {
			// if it's allowed to go back... ie: in battles you can't go back from the first menu
			// TODO: Find more cases
			if (GameStateManager.getGameState() == GameStateManager.getGameState(GameStateManager.battleState)) {
				remove();
			}
			//smile and wave
		}
	}
	
	public void setParentMenu(GuiElement[] parent) {
		parentElements = parent;
	}
}

