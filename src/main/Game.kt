package main

import gamestate.GameStateMachine
import gui.GuiManager
import util.Resources
import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

class Game(title: String) : BasicGame(title) {

    override fun render(gc: GameContainer, g: Graphics) {
        if (!Resources.isReady) {
            Resources.splashImage.draw()
        } else {
            GameStateMachine.render(gc, g)
        }
    }

    override fun init(gc: GameContainer) {
        Resources.init()
    }

    override fun update(gc: GameContainer, delta: Int) {
        if (!Resources.isReady) {
            Resources.initPool()
            GameStateMachine.init(gc)
            GuiManager.init(gc)
            Resources.isReady = true
        } else {
            GameStateMachine.update(gc)
        }
        Main.ticks++
    }

}
