package gamestate

import entities.EntityManager
import entities.characters.GameCharacter
import entities.characters.Trainer
import gui.GuiManager
import main.Main.ticks
import util.Camera
import util.Resources
import util.Direction.*
import util.controls.Controls
import util.controls.PlayerController
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.tiled.TiledMap
import region.CollisionMap
import region.RegionManager
import util.CollisionType.*
import java.util.*

object InGameState : AbstractGameState() {

    lateinit var collisionMap: CollisionMap

    private var player = Trainer(256 + 32, 0, Resources.SPRITESHEET["Player Brendan"]!!)

    private lateinit var northMap: TiledMap
    private lateinit var southMap: TiledMap
    private lateinit var centerMap: TiledMap
    private lateinit var eastMap: TiledMap
    private lateinit var westMap: TiledMap

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
        if(ticks == 1L) { refreshMaps() }

        collisionMap = CollisionMap(northMap, westMap, centerMap, eastMap, southMap)
        EntityManager.update(gc)
        if(areaShouldSwitch()) {
            val dir = (Camera.followedEntity!! as? GameCharacter)!!.direction
            RegionManager.currentArea = RegionManager.getNeighbor(dir)
            EntityManager.areaSwitch(dir)
            refreshMaps()
        }

        Camera.update()

        fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start


        if (player.frameNum == 0 && player.currentAction != GameCharacter.Action.IDLING) {
            when (player.forwardCollisionType) {
                GRASS, DARK_GRASS -> {
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

    private fun areaShouldSwitch(): Boolean {
        val entity = (Camera.followedEntity!! as? GameCharacter)!!
        return when (entity.direction) {
            UP -> (entity.y < 0)
            DOWN -> (entity.y > (RegionManager.currentArea.map.height - 1) * 16)
            LEFT -> (entity.x < 0)
            RIGHT -> (entity.x > (RegionManager.currentArea.map.width - 1) * 16)
        }
    }

    private fun refreshMaps() {
        northMap = RegionManager.getNeighbor(UP).map
        southMap = RegionManager.getNeighbor(DOWN).map
        centerMap = RegionManager.currentArea.map
        eastMap = RegionManager.getNeighbor(RIGHT).map
        westMap = RegionManager.getNeighbor(LEFT).map
    }

}
