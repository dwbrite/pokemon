package entities.particles

import entities.characters.GameCharacter
import util.Resources
import main.Main

class GrassJump(x: Int, y: Int, private var parent: GameCharacter) : Particle(x, y, Resources.PARTICLE["Grass Jump"]!!, 0.1) {

    private var isStepOffActivated = false
    private var stepOffTick: Long = 0

    override fun isDead(): Boolean {
        if (Main.ticks - startingTick >= 56 && !isStepOffActivated && (parent.x != x || parent.y != y)) {
            stepOffTick = Main.ticks + 11
            isStepOffActivated = true
        }
        return Main.ticks >= stepOffTick && isStepOffActivated
    }

    override fun uniqueUpdates() {
        val tickDifference = Main.ticks - startingTick
        if (tickDifference <= 56 && tickDifference != 0L) {
            if (tickDifference == 16L) {
                animation.nextFrame()
            } else if (tickDifference > 16 && tickDifference % 8 == 0L && tickDifference < 56) {
                animation.nextFrame()
            }
        }
    }
}