package handlers.controls

import entities.characters.GameCharacter
import handlers.controls.Controls.InputDir.*
import handlers.controls.Controls.InputKey.SEL
import handlers.controls.Controls.InputKey.START
import org.newdawn.slick.GameContainer

class GuiController(private var player: GameCharacter) : Controller() {
    init {

    }

    override fun update(gc: GameContainer) {
        //TODO("Implement GUI controls")
        /*
        (Controls.getInput(this, UP))
        (Controls.getInput(this, DOWN))
        (Controls.getInput(this, LEFT))
        (Controls.getInput(this, RIGHT))
        (Controls.getInput(this, START))
        ...
        */
    }
}