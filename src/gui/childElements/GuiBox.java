package gui.childElements;

import handlers.Misc;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by dwbrite on 5/12/16.
 */
public class GuiBox {
	public static SpriteSheet BORDER_MENU;
	
	public static SpriteSheet BORDER_DIALOGUE;
	
	public static SpriteSheet BORDER_BATTLE;
	
	private int x, y;
	
	private int width, height;
	
	private SpriteSheet border;
	
	private int borderType;
	
	public GuiBox(int x, int y, int tilesWide, int tilesHigh, SpriteSheet border) {
		this.x = x;
		this.y = y;
		this.width = tilesWide;
		this.height = tilesHigh;
		this.border = border;
		if (this.border == BORDER_MENU)
			borderType = 0;
		if (this.border == BORDER_DIALOGUE)
			borderType = 1;
		if (this.border == BORDER_BATTLE)
			borderType = 2;
	}
	
	public static void init() {
		try {
			BORDER_MENU = new SpriteSheet("TileSets/Borders/Borders.png", 8, 8);
			BORDER_DIALOGUE = new SpriteSheet("TileSets/Borders/dialogue.png", 8, 8);
			BORDER_BATTLE = new SpriteSheet("TileSets/Borders/Battle.png", 8, 8);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void draw() {
		switch (borderType) {
			case 0:
				drawMenuBox();
				break;
			case 1:
				drawDialogueBox();
				break;
			case 2:
				drawBattleBox();
				break;
		}
	}
	
	private void drawBattleBox() {
		int nnx = x;
		int nny;
		for (int nx = 0; nx < width; nx++) {
			nny = y;
			for (int ny = 0; ny < height; ny++) {
				if (ny == 0) {
					if (nx == 0) { border.getSprite(0 + 0, 0).draw(nnx, nny); } else if (nx == 1) {
						border.getSprite(0 + 1, 0).draw(nnx, nny);
					} else if (nx == width - 1) {
						border.getSprite(0 + 4, 0).draw(nnx, nny);
					} else if (nx == width - 2) {
						border.getSprite(0 + 3, 0).draw(nnx, nny);
					} else { border.getSprite(0 + 2, 0).draw(nnx, nny); }
				} else if (ny == height - 1) {
					if (nx == 0) { border.getSprite(8 + 0, 0).draw(nnx, nny); } else if (nx == 1) {
						border.getSprite(8 + 1, 0).draw(nnx, nny);
					} else if (nx == width - 1) {
						border.getSprite(8 + 4, 0).draw(nnx, nny);
					} else if (nx == width - 2) {
						border.getSprite(8 + 3, 0).draw(nnx, nny);
					} else { border.getSprite(8 + 2, 0).draw(nnx, nny); }
				} else {
					if (nx == 0) { border.getSprite(5 + 0, 0).draw(nnx, nny); } else if (nx == width - 1) {
						border.getSprite(5 + 2, 0).draw(nnx, nny);
					} else { border.getSprite(5 + 1, 0).draw(nnx, nny); }
				}
				nny += 8; //needless optimization. Saves between 2 and 3.75 cpu cycles each loop.
			}
			nnx += 8; //needless optimization. Saves between 2.25 and 4 cpu cycles each loop.
		}
	}
	
	private void drawMenuBox() {
		int brdr = Misc.INSTANCE.getUserBorder();
		int nnx = x;
		int nny;
		for (int nx = 0; nx < width; nx++) {
			nny = y;
			for (int ny = 0; ny < height; ny++) {
				if (ny == 0) {
					if (nx == 0) { border.getSprite(0 + 0, brdr).draw(nnx, nny); } else if (nx == width - 1) {
						border.getSprite(0 + 2, brdr).draw(nnx, nny);
					} else { border.getSprite(0 + 1, brdr).draw(nnx, nny); }
				} else if (ny == height - 1) {
					if (nx == 0) { border.getSprite(6 + 0, brdr).draw(nnx, nny); } else if (nx == width - 1) {
						border.getSprite(6 + 2, brdr).draw(nnx, nny);
					} else { border.getSprite(6 + 1, brdr).draw(nnx, nny); }
				} else {
					if (nx == 0) { border.getSprite(3 + 0, brdr).draw(nnx, nny); } else if (nx == width - 1) {
						border.getSprite(3 + 2, brdr).draw(nnx, nny);
					} else { border.getSprite(3 + 1, brdr).draw(nnx, nny); }
				}
				nny += 8; //needless optimization. Saves between 2 and 3.75 cpu cycles each loop.
			}
			nnx += 8; //needless optimization. Saves between 2.25 and 4 cpu cycles each loop.
		}
	}
	
	private void drawDialogueBox() {
		int nnx = x;
		int nny;
		for (int nx = 0; nx < width; nx++) {
			nny = y;
			for (int ny = 0; ny < height; ny++) {
				if (ny == 0) {
					if (nx == 0) { border.getSprite(0 + 0, 0).draw(nnx, nny); } else if (nx == 1) {
						border.getSprite(0 + 1, 0).draw(nnx, nny);
					} else if (nx == width - 1) {
						border.getSprite(0 + 4, 0).draw(nnx, nny);
					} else if (nx == width - 2) {
						border.getSprite(0 + 3, 0).draw(nnx, nny);
					} else { border.getSprite(0 + 2, 0).draw(nnx, nny); }
				} else if (ny == height - 1) {
					if (nx == 0) { border.getSprite(0 + 8, 0).draw(nnx, nny); } else if (nx == 1) {
						border.getSprite(1 + 8, 0).draw(nnx, nny);
					} else if (nx == width - 1) {
						border.getSprite(4 + 8, 0).draw(nnx, nny);
					} else if (nx == width - 2) {
						border.getSprite(3 + 8, 0).draw(nnx, nny);
					} else { border.getSprite(2 + 8, 0).draw(nnx, nny); }
				} else {
					if (nx == 0) { border.getSprite(0 + 5, 0).draw(nnx, nny); } else if (nx == width - 1) {
						border.getSprite(2 + 5, 0).draw(nnx, nny);
					} else { border.getSprite(1 + 5, 0).draw(nnx, nny); }
				}
				nny += 8; //needless optimization. Saves between 2 and 3.75 cpu cycles each loop.
			}
			nnx += 8; //needless optimization. Saves between 2.25 and 4 cpu cycles each loop.
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
