package entities.particles

import entities.characters.GameCharacter
import handlers.Resources
import main.Main

/**
 * Created by dwbrite on 5/15/16.
 */
class NormalGrassParticle(x: Int, y: Int, private val parent: GameCharacter) : Particle(x, y, Resources.PARTICLE["Grass"]!!, -1.0) {

    private var hasParentFinishedStep = false
    private var isStepOffActivated = false
    private var stepOffTick: Long = 0
    private var pokemonChecked = false

    override fun isDead(): Boolean {
        if (parent.x == x && parent.y == y) {
            hasParentFinishedStep = true
        }

        if (Main.ticks - startingTick >= 52 && !isStepOffActivated && (parent.x != x || parent.y != y)) {
            stepOffTick = Main.ticks + 11
            isStepOffActivated = true
        }

        return Main.ticks >= stepOffTick && isStepOffActivated
    }

    override fun uniqueUpdates() {
        val tickDifference = Main.ticks - startingTick
        if (tickDifference <= 40 && tickDifference != 0L) {
            if (tickDifference % 10 == 0L) {
                animation.nextFrame()
            }

            if (tickDifference == 10L) {
                depthOffset = 0.1
            }
        }
    }
}
