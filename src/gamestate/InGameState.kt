package gamestate

import entities.EntityManager
import entities.characters.GameCharacter
import entities.characters.Player
import handlers.Camera
import handlers.controls.Controls
import handlers.Resources
import handlers.controls.Controller
import handlers.controls.PlayerController
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager

class InGameState : AbstractGameState() {
    init {
        //TODO: (re)move this?
        EntityManager.initAdd("player", Player(256 + 32, 0, Resources.SPRITESHEET["Player Brendan"]!!))
        Controls.controllers.put("player", PlayerController(EntityManager.getEntity("player")!! as GameCharacter))
        Controls.givePriority(Controls.controllers["player"]!!)

        Camera.followEntity(EntityManager.getEntity("player")!!)
    }

    override fun render(gc: GameContainer, g: Graphics) {
        RegionManager.render(gc, g)
        guiman.render(gc, g)
    }

    override fun update(gc: GameContainer) {
        RegionManager.update(gc)
        Camera.update()
        guiman.update(gc)
        Controls.update(gc)
    }

}
