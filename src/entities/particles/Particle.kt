package entities.particles

import entities.AbstractEntity
import entities.Animation
import entities.EntityManager
import main.Main
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import region.RegionManager
import util.CollisionType

open class Particle(x: Int, y: Int, spriteSheet: Image, protected var depthOffset: Double) : AbstractEntity(x, y) {
    protected var animation: Animation

    protected var startingTick: Long = 0
    protected open var tickLimit: Long = 16
    protected var endingTick: Long = 0
    protected var tickSwitch: Long = 0

    init {
        this.spriteSheet = spriteSheet

        val images = arrayOfNulls<Image>(this.spriteSheet.width / width)
        for (i in images.indices) {
            images[i] = this.spriteSheet.getSubImage(i * width, 0, 16, 16)
        }
        val imgz: ArrayList<Image> = ArrayList()
        for(i in images.indices) {
            imgz.add(images[i]!!)
        }

        this.animation = Animation(*imgz.toTypedArray())
        this.currentImage = animation.currentFrame
        this.collisionType = CollisionType.NONE

        this.startingTick = Main.ticks
        this.tickSwitch = tickLimit
        this.endingTick = startingTick + tickLimit
    }

    open fun isDead(): Boolean = Main.ticks >= endingTick

    override fun update(gc: GameContainer) {
        uniqueUpdates()
        if (isDead()) {
            EntityManager.remove(this.toString())
        }
        updateDepth()
        currentImage = animation.currentFrame
    }

    override fun render(gc: GameContainer, g: Graphics) {
        currentImage!!.draw((x + RegionManager.currentArea.x).toFloat(), (y + RegionManager.currentArea.y).toFloat())
    }

    protected open fun uniqueUpdates() {
        if ((Main.ticks - startingTick) % tickSwitch == 0L && Main.ticks - startingTick != 0L) {
            animation.nextFrame()
        }
    }

    fun setTickLimit(frames: Int) {
        tickLimit = frames.toLong()
        endingTick = startingTick + tickLimit
    }

    fun setSwitchTicks(frames: Int) {
        tickSwitch = frames.toLong()
    }

    override fun updateDepth() {
        depth = y / 16.0 + depthOffset
    }
}