package gamestate

import gui.GuiManager
import handlers.controls.Controls
import main.Main
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager

import java.util.ArrayList

object GameStateMachine {
    val EFFECT_GRASS_A = 0
    val EFFECT_GRASS_B = 1
    val EFFECT_WATER_A = 2
    val EFFECT_WATER_B = 3
    val EFFECT_CAVE_A = 4
    val EFFECT_CAVE_B = 5

    val inGameState = 0
    val battleState = 1

    private val gameStates = ArrayList<AbstractGameState>()
    private var currentState: Int = 0

    private var transitionStartTicks: Long = 0
    var isTransitioning = false
        private set
    private var transitionEffect: Int = 0

    val gameState: AbstractGameState
        get() = gameStates[currentState]

    fun update(gc: GameContainer) {
        gameStates[currentState].update(gc)
        Controls.update(gc)

        //TODO("Move transitions elsewhere | Complete transitions")
        if (isTransitioning) {
            if (Main.ticks >= transitionStartTicks + 32 + 128) {
                //Done transitioning
                isTransitioning = false
            } else if (Main.ticks >= transitionStartTicks + 128) {
                //Transition In
            } else if (Main.ticks >= transitionStartTicks + 64) {
                //Transition Out
                setGameState(battleState)
            }
        }

        GuiManager.update(gc)
    }

    fun render(gc: GameContainer, g: Graphics) {
        gameStates[currentState].render(gc, g)
        GuiManager.render(gc, g)

        if (isTransitioning) {
            //TODO(" battle transition")
            //draw transition image
            //something like "TransitionAnimation" + transitionEffect + ".gif"
            //using transitionStartTicks - Main.ticks to get the animation frame
        }
    }

    fun init(gc: GameContainer) {
        gameStates.add(InGameState()) //0
        gameStates.add(BattleState()) //1
        currentState = inGameState
        RegionManager.init(gc)
    }

    fun setGameState(state: Int) {
        currentState = state
    }

    fun getGameState(stateId: Int): AbstractGameState {
        return gameStates[stateId]
    }

    fun transitionToBattle(transition: Int) {
        isTransitioning = true
        transitionEffect = transition
        transitionStartTicks = Main.ticks
    }
}
