package entities

import gamestate.InGameState
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import region.RegionManager

abstract class AbstractEntity(x: Int, y: Int) {
    var x: Int = 0
        protected set
    var y: Int = 0
        protected set
    protected var width: Int = 0
    protected var height: Int = 0

    var depth: Double = 0.toDouble()

    var xMapOffset: Int = 0
        protected set
    var yMapOffset: Int = 0
        protected set

    protected var collisionType = 1

    protected lateinit var spriteSheet: Image

    protected var currentImage: Image? = null

    init {
        this.x = x
        this.y = y

        width = 16
        height = 16
    }

    abstract fun update(gc: GameContainer)

    abstract fun render(gc: GameContainer, g: Graphics)

    fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun setCollision() {
        if (collisionType != 0) {
            val x1 = (x + xMapOffset) / 16
            val y1 = (y + yMapOffset) / 16

            val x2 = (x + xMapOffset + (width - 1)) / 16
            val y2 = (y + yMapOffset + (height - 1)) / 16

            InGameState.setCollisionValue(x1, y1, collisionType)
			InGameState.setCollisionValue(x1, y2, collisionType)
			InGameState.setCollisionValue(x2, y1, collisionType)
			InGameState.setCollisionValue(x2, y2, collisionType)
        }
    }

    fun setMapOffset(xOffset: Int, yOffset: Int) {
        xMapOffset = xOffset
        yMapOffset = yOffset
    }

    fun setDimensions(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    open fun updateDepth() {
        depth = y / 16.0
    }
}
