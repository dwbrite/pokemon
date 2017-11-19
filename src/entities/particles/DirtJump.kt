package entities.particles

import entities.characters.GameCharacter
import handlers.Resources
import main.Main

public class DirtJump(x: Int, y: Int, private var parent: GameCharacter): Particle(x, y, Resources.PARTICLE["Dirt Jump"]!!, -1.0) {

    init { setTickLimit(32) }

    override fun uniqueUpdates() {
        if (Main.ticks - startingTick < 16) {
            this.x = parent.x
            this.y = parent.y
        } else {
            if (Main.ticks - startingTick == 16L) {
                this.x = parent.x
                this.y = parent.y
            }
            this.depthOffset = 0.1
            if ((Main.ticks - startingTick) % 8 == 0L) {
                this.animation.nextFrame()
            }
        }
    }
}