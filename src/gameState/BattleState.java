package gameState;

import battle.BattleManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class BattleState extends AbstractGameState {
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		BattleManager.render(gc, g);
		guiman.render(gc, g);
	}
	
	@Override
	public void update(GameContainer gc) {
		BattleManager.update(gc);
		guiman.update(gc);
	}
	
}
