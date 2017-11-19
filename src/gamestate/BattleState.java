package gamestate;

import battle.BattleManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class BattleState extends AbstractGameState {
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		BattleManager.INSTANCE.render(gc, g);
		guiman.render(gc, g);
	}
	
	@Override
	public void update(GameContainer gc) {
		BattleManager.INSTANCE.update(gc);
		guiman.update(gc);
	}
	
}
