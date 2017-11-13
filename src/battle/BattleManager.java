package battle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by dwbrite on 4/13/16.
 */
public class BattleManager {
	public static AbstractBattle currentBattle;
	
	public static void render(GameContainer gc, Graphics g) {
		currentBattle.render(gc, g);
	}
	
	public static void update(GameContainer gc) {
		currentBattle.update(gc);
	}
}

