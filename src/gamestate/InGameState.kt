package gamestate

import entities.EntityManager
import entities.characters.Player
import handlers.Camera
import handlers.Controls
import handlers.Resources
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager

class InGameState : AbstractGameState() {
    init {
        //TODO: (re)move this?
        EntityManager.initAdd("player", Player(256 + 32, 0, Resources.SPRITESHEET["Player Brendan"]!!))
        Controls.givePriority(EntityManager.getEntity("player")!!)

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
