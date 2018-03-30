package entities

import org.newdawn.slick.Image

class Animation {
    private var frames: ArrayList<Image> = ArrayList()

    private var frameOrder: IntArray? = null

    private var currentFrameNum: Int = 0

    val currentFrame: Image
        get() = frames[frameOrder!![currentFrameNum]]

    constructor(frameOrder: IntArray, vararg frames: Image) {
        this.frameOrder = frameOrder
        this.frames = ArrayList(frames.toMutableList())
    }

    constructor(vararg frames: Image) {
        //TODO("Fix this awful black magic fuckery right here.")
        this.frames = ArrayList(frames.toMutableList())
        this.frameOrder = IntArray(frames.size)
        for (i in frames.indices) {
            frameOrder!![i] = i
        }
    }

    fun nextFrame() {
        currentFrameNum++
        if (currentFrameNum >= frameOrder!!.size) {
            currentFrameNum = 0
        }
    }

    fun setFrame(frameNum: Int) {
        currentFrameNum = frameNum
    }
}
