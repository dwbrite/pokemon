package gamestate;

import handlers.controls.Controls;
import main.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import region.RegionManager;

import java.util.ArrayList;

public class GameStateMachine {
	public static final int EFFECT_GRASS_A = 0;
	
	public static final int EFFECT_GRASS_B = 1;
	
	public static final int EFFECT_WATER_A = 2;
	
	public static final int EFFECT_WATER_B = 3;
	
	public static final int EFFECT_CAVE_A = 4;
	
	public static final int EFFECT_CAVE_B = 5;
	
	public static final int inGameState = 0;
	
	public static final int battleState = 1;
	
	private static ArrayList<AbstractGameState> gameStates = new ArrayList<>();
	
	private static int currentState;
	
	private static long transitionStartTicks = 0;
	
	private static boolean battleTransitioning = false;
	
	private static int transitionEffect;
	
	public static void update(GameContainer gc) {
		gameStates.get(currentState).update(gc);
		Controls.INSTANCE.update(gc);
		if (battleTransitioning) {
			if (Main.ticks >= transitionStartTicks + 32 + 128) {
				//Done transitioning
				battleTransitioning = false; //TODO(" Maybe use more bools to show which part of the transition we're in")
			} else if (Main.ticks >= transitionStartTicks + 128) {
				//TODO(" Transition In")
			} else if (Main.ticks >= transitionStartTicks + 64) {//TODO(" Set proper transition effect")
				//Transition Out
				setGameState(battleState);
			}
		}
	}
	
	public static void render(GameContainer gc, Graphics g) {
		gameStates.get(currentState).render(gc, g);
		if (battleTransitioning) {
			//TODO(" battle transition")
			//draw transition image
			//something like "TransitionAnimation" + transitionEffect + ".gif"
			//using transitionStartTicks - Main.ticks to get the animation frame
		}
	}
	
	public static void init(GameContainer gc) {
		gameStates.add(new InGameState()); //0
		gameStates.add(new BattleState()); //1
		currentState = inGameState;
		RegionManager.init(gc);
	}
	
	public static AbstractGameState getGameState() {
		return gameStates.get(currentState);
	}
	
	public static void setGameState(int state) {
		currentState = state;
	}
	
	public static AbstractGameState getGameState(int stateId) {
		return gameStates.get(stateId);
	}
	
	public static void transitionToBattle(int transition) {
		battleTransitioning = true;
		transitionEffect = transition;
		transitionStartTicks = Main.ticks;
	}
	
	public static boolean isTransitioning() {
		return battleTransitioning;
	}
}
