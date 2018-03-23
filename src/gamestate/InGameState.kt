package gamestate

import entities.EntityManager
import entities.characters.GameCharacter
import entities.characters.Trainer
import gui.GuiManager
import util.Camera
import util.Resources
import util.Direction.*
import util.controls.Controls
import util.controls.PlayerController
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.tiled.TiledMap
import region.RegionManager
import util.CollisionType
import util.Direction
import java.util.*

object InGameState : AbstractGameState() {

    var collisionMap: Array<IntArray> = emptyArray()

    var player = Trainer(256 + 32, 0, Resources.SPRITESHEET["Player Brendan"]!!)

    init {
        //TODO(" (re)move this?")
        EntityManager.initAdd("player", player)
        EntityManager.initAdd("Bob", Trainer(256+64, 32, Resources.SPRITESHEET["Player Brendan"]!!))
        Controls.controllers["player"] = PlayerController(player as GameCharacter)
        Controls.givePriority(Controls.controllers["player"]!!)
        Camera.followEntity(player)
    }

    override fun render(gc: GameContainer, g: Graphics) {
        RegionManager.currentArea.render("Floor")
        RegionManager.currentArea.render("Interactive")
        EntityManager.render(gc, g)
        RegionManager.currentArea.render("Sky")

        GuiManager.render(gc, g)
    }

    override fun update(gc: GameContainer) {
        collisionMap = Array(RegionManager.fullWidth) { IntArray(RegionManager.fullHeight) }
        updateCollisionMap()
        EntityManager.update(gc)
        if(areaShouldSwitch()) {
            val dir = (Camera.followedEntity!! as? GameCharacter)!!.direction
            RegionManager.currentArea = RegionManager.getNeighbor(dir)
            EntityManager.areaSwitch(dir)
        }

        //collisionMap = Array(RegionManager.fullWidth) { IntArray(RegionManager.fullHeight) }

        Camera.update()

        fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start


        if (player.frameNum == 0 && player.currentAction != GameCharacter.Action.IDLING) {
            when (player.forwardCollisionType) {
                2 -> {
                    //TODO(" implement proper RNG")
                    if ((1..32).random() == 1) {
                        println("battle")
                        player.frameNum = 0
                        player.busy = true
                        player.currentAction = GameCharacter.Action.IDLING
                    }
                }
            }
        }
    }

    private fun updateCollisionMap() {
        val north: TiledMap = RegionManager.getNeighbor(UP).map
        val south: TiledMap = RegionManager.getNeighbor(DOWN).map
        val current: TiledMap = RegionManager.currentArea.map
        val east: TiledMap = RegionManager.getNeighbor(RIGHT).map
        val west: TiledMap = RegionManager.getNeighbor(LEFT).map

        updateCollisionFrom(north, west.width + RegionManager.currentArea.getOffset(UP)/16, 0)
        updateCollisionFrom(west, 0, north.height + RegionManager.currentArea.getOffset(LEFT)/16)
        updateCollisionFrom(current, west.width, north.height)
        updateCollisionFrom(east, west.width + current.width, north.height + RegionManager.currentArea.getOffset(RIGHT)/16)
        updateCollisionFrom(south, west.width + RegionManager.currentArea.getOffset(DOWN)/16, north.height + current.height)
    }

    private fun updateCollisionFrom(map: TiledMap, xOffset: Int, yOffset: Int) {
        for (y in 0 until map.height) for (x in 0 until map.width) {
            when (map.getTileId(x, y, map.getLayerIndex("Interactive"))) {
                0 -> collisionMap[x + xOffset][y + yOffset] = CollisionType.NONE.ordinal
                7, 11 -> collisionMap[x + xOffset][y + yOffset] = CollisionType.LEFT_CLIFF.ordinal
                10, 12 -> collisionMap[x + xOffset][y + yOffset] = CollisionType.RIGHT_CLIFF.ordinal
                27, 28, 29, 30 -> collisionMap[x + xOffset][y + yOffset] = CollisionType.DOWN_CLIFF.ordinal
                else -> collisionMap[x + xOffset][y + yOffset] = CollisionType.NORMAL.ordinal
            }
            when (map.getTileId(x, y, map.getLayerIndex("Floor"))) {
                3 -> collisionMap[x + xOffset][y + yOffset] = CollisionType.GRASS.ordinal
                6 -> collisionMap[x + xOffset][y + yOffset] = CollisionType.DARK_GRASS.ordinal
            }
        }
    }

    fun getCollisionValue (x: Int, y: Int): Int {
        return collisionMap[x + RegionManager.getNeighbor(LEFT).width][y + RegionManager.getNeighbor(UP).height]
    }

    fun setCollisionValue(x: Int, y: Int, type: Int) {
        collisionMap[x + RegionManager.getNeighbor(LEFT).width][y + RegionManager.getNeighbor(UP).height] = type
    }

    private fun areaShouldSwitch(): Boolean {
        val entity = (Camera.followedEntity!! as? GameCharacter)!!
        return when (entity.direction) {
            UP -> (entity.y < 0)
            DOWN -> (entity.y > (RegionManager.currentArea.map.height - 1) * 16)
            LEFT -> (entity.x < 0)
            RIGHT -> (entity.x > (RegionManager.currentArea.map.width - 1) * 16)
        }
    }

}
