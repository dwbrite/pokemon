package handlers.controls

import org.newdawn.slick.GameContainer

abstract class Controller {
    abstract fun update(gc: GameContainer)
}