package util

import entities.AbstractEntity
import main.Main

object Camera {
    var x: Int = 0
        private set
    var y: Int = 0
        private set

    var isFollowing = false
    var followedEntity: AbstractEntity? = null
        private set

    private fun setPosition(x: Int, y: Int) {
        Camera.x = x
        Camera.y = y
    }

    fun update() {
        if (isFollowing) {
            Camera.setPosition(followedEntity!!.x - (Main.WIDTH / 2 - 8), followedEntity!!.y - (Main.HEIGHT / 2 - 8))
        }
        //RegionManager.currentArea.setPosition(- x, -y)
    }

    fun isEntityInBounds(entity: AbstractEntity): Boolean {
        val x = entity.x + entity.xMapOffset
        val y = entity.y + entity.yMapOffset

        return x >= Camera.x - 16 && x < Camera.x + (Main.WIDTH + 16) + (Main.WIDTH / 2 - 8) && y >= Camera.y - 16 && y < Camera.y + (Main.HEIGHT + 16) + (Main.HEIGHT / 2 - 8)
    }

    fun followEntity(entity: AbstractEntity) {
        followedEntity = entity
        isFollowing = true
    }
}
