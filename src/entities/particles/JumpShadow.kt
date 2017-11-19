package entities.particles

import entities.characters.GameCharacter
import handlers.Resources

/**
 * Created by dwbrite on 5/15/16.
 */
class JumpShadow(x: Int, y: Int, private val parent: GameCharacter) : Particle(x, y, Resources.PARTICLE["Jump Shadow"]!!, -1.0) {

    init { setTickLimit(16) }

    constructor(parent: GameCharacter) : this(parent.x, parent.y, parent)

    override fun uniqueUpdates() {
        this.x = parent.x
        this.y = parent.y
    }
}
