package gamestate

import entities.EntityManager
import entities.characters.GameCharacter
import entities.characters.Player
import gui.GuiManager
import handlers.Camera
import handlers.Resources
import handlers.controls.Controls
import handlers.controls.PlayerController
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager
import java.util.*

class InGameState : AbstractGameState() {
    var player: Player

    init {
        //TODO(" (re)move this?")
        player = Player(256 + 32, 0, Resources.SPRITESHEET["Player Brendan"]!!)
        EntityManager.initAdd("player", player)
        Controls.controllers.put("player", PlayerController(player as GameCharacter))
        Controls.givePriority(Controls.controllers["player"]!!)
        Camera.followEntity(player)
    }

    override fun render(gc: GameContainer, g: Graphics) {
        RegionManager.render(gc, g)
        GuiManager.render(gc, g)
    }

    override fun update(gc: GameContainer) {
        RegionManager.update(gc)
        Camera.update()

        fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start

        if (player.frameNum == 0 && player.currentAction != GameCharacter.Action.IDLING) {
            when (player.forwardCollisionType) {
                2 -> {
                    //TODO(" implement proper RNG")
                    if ((1..32).random() == 1) {
                        println("battle")
                        player.frameNum = 0
                        player.busy = true
                        player.currentAction = GameCharacter.Action.IDLING
                    }
                }
            }
        }

    }

}
