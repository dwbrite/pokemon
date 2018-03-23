package util.controls

import entities.characters.GameCharacter
import util.Direction.*
import util.InputKey.SEL
import util.InputKey.START
import org.newdawn.slick.GameContainer

class PlayerController(private var player: GameCharacter) : Controller() {
    init {

    }

    override fun update(gc: GameContainer) {
        player.controls[UP] = (Controls.getInput(this, UP))
        player.controls[DOWN] = (Controls.getInput(this, DOWN))
        player.controls[LEFT] = (Controls.getInput(this, LEFT))
        player.controls[RIGHT] = (Controls.getInput(this, RIGHT))

        //so, hokej, next we need to check for non-directional input.
        if (Controls.getInput(this, START)) {
            //TODO(" Open up the menu and set the controller to the menu controller.")
            //Note: This may be buggy and open and close the menu a bunch.
            println("start")
        }
        if (Controls.getInput(this, SEL)) {
            player.transportMode = when (player.transportMode) {
                GameCharacter.TransportMode.BIKE -> GameCharacter.TransportMode.WALK
                GameCharacter.TransportMode.WALK -> GameCharacter.TransportMode.BIKE
                else -> player.transportMode
            }
            //TODO(" Use the set item")
            println("select")
        }
    }
}