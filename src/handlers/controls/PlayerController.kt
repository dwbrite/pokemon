package handlers.controls

import entities.characters.GameCharacter
import org.newdawn.slick.GameContainer
import handlers.controls.Controls.InputDir.*

class PlayerController(private var player: GameCharacter) : Controller() {
    init {

    }

    override fun update(gc: GameContainer) {
        player.controls[UP] = (Controls.getInput(this, UP))
        player.controls[DOWN] = (Controls.getInput(this, DOWN))
        player.controls[LEFT] = (Controls.getInput(this, LEFT))
        player.controls[RIGHT] = (Controls.getInput(this, RIGHT))
    }
}