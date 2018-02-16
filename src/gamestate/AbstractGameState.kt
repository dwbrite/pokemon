package gamestate

import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics

abstract class AbstractGameState {
    abstract fun render(gc: GameContainer, g: Graphics)
    abstract fun update(gc: GameContainer)
}
