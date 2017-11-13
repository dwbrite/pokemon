package gameState

import entities.EntityManager
import entities.objectEntities.Player
import entities.pokemon.Pokemon
import handlers.Camera
import handlers.Controls
import handlers.Resources
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager

class InGameState : AbstractGameState() {
    init {
        EntityManager.add("player", Player(256 + 32, 0, Resources.SPRITESHEET["Player Brendan"]!!))
        Controls.givePriority(EntityManager.getEntity("player")!!)
        val mew = Pokemon(150 + 1, intArrayOf(31, 31, 31, 31, 31, 31), false, true, 105, 0, Pokemon.Gender.GENDERLESS, Pokemon.Nature.BASHFUL)
        println(mew)
        (EntityManager.getEntity("player") as Player).setPartyMember(0, mew)

        Camera.followCharacter(EntityManager.getEntity("player"))
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
