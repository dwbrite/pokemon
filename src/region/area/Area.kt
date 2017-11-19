package region.area

import entities.AbstractEntity
import entities.EntityManager
import entities.characters.Player
import handlers.Misc
import main.Main
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.tiled.TiledMap
import region.RegionManager
import region.RegionManager.getArea

import java.io.File
import java.nio.charset.Charset
import java.util.ArrayList
import java.util.HashMap

import handlers.Controls.InputDir.*

class Area(var areaKey: Pair<String, String>,
           path: String,
           wildlifeCsv: String,
           var northArea: Pair<String, String>,
           var southArea: Pair<String, String>,
           var eastArea: Pair<String, String>,
           var westArea: Pair<String, String>) {

    constructor (areaKey: Pair<String, String>,
                 path: String,
                 wildlifeCsv: String) : this(areaKey, path, wildlifeCsv,
            Pair(areaKey.first, "Clear"),
            Pair(areaKey.first, "Clear"),
            Pair(areaKey.first, "Clear"),
            Pair(areaKey.first, "Clear"))

    protected lateinit var area: TiledMap

    var name = ""

    var northOffsetX: Int = 0
    var southOffsetX: Int = 0
    var eastOffsetY: Int = 0
    var westOffsetY: Int = 0

    var x: Int = 0
    var y: Int = 0
    var width: Int = 0
    var height: Int = 0

    lateinit var collisionMap: Array<IntArray>
    lateinit var wildlifeData: List<CSVRecord>
    var entities = HashMap<String, AbstractEntity>()
    var entityList = ArrayList<String>()

    lateinit var region: String

    enum class Collide {
        NONE,
        NORMAL,
        GRASS,
        DARK_GRASS,
        LEFT_CLIFF,
        RIGHT_CLIFF,
        DOWN_CLIFF,
        SIGN,
        PLAYER,
        NPC
    }

    init {
        try {
            area = TiledMap(path)

            val csvData = File(wildlifeCsv)
            val parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL)
            wildlifeData = parser.records
        } catch (e: Exception) {
            e.printStackTrace()
        }

        width = area.width
        height = area.height
        name = areaKey.second
    }

    fun render(gc: GameContainer, g: Graphics) {
        getArea(northArea).updateRenderedAnimations()
        getArea(westArea).updateRenderedAnimations()
        updateRenderedAnimations()
        getArea(eastArea).updateRenderedAnimations()
        getArea(southArea).updateRenderedAnimations()

        getArea(northArea).renderFloor(x, y - getArea(northArea).height * 16)
        renderFloor(x, y)
        getArea(southArea).renderFloor(x, y + height * 16)

        EntityManager.render(gc, g)

        getArea(northArea).renderSky(x, y - getArea(northArea).height * 16)
        renderSky(x, y)
        getArea(southArea).renderSky(x, y + height * 16)
    }

    fun renderFloor(x: Int, y: Int) {
        area.render(x, y, area.getLayerIndex("Floor"))
        area.render(x, y, area.getLayerIndex("Interactive"))
    }

    fun renderSky(x: Int, y: Int) {
        area.render(x, y, area.getLayerIndex("Sky"))
    }

    fun getCollisionValue(x: Int, y: Int): Int {
        return collisionMap[x + getArea(westArea).width][y + getArea(northArea).height]
    }

    fun update(gc: GameContainer) {
        updateAnimationFrames()
        updateCollisionMap()
        EntityManager.update(gc)
        checkAreaSwitch()
    }

    private fun updateCollisionMap() {
        collisionMap = Array(width + getArea(westArea).width + getArea(eastArea).width) { IntArray(height + getArea(southArea).height + getArea(northArea).height) }

        for (z in 0..4) {
            val tempArea = if (z == 0) getArea(northArea) else if (z == 1) getArea(westArea) else if (z == 2) this else if (z == 3) getArea(eastArea) else getArea(southArea)
            val xOffset = if (z == 0) getArea(westArea).width else if (z == 1) 0 else if (z == 2) getArea(westArea).width else if (z == 3) getArea(westArea).width + width else getArea(westArea).width
            val yOffset = if (z == 0) 0 else if (z == 1) getArea(northArea).height else if (z == 2) getArea(northArea).height else if (z == 3) getArea(northArea).height else getArea(northArea).height + height

            for (i in 0 until tempArea.height) {
                for (j in 0 until tempArea.width) {
                    when (tempArea.getTileId(j, i, tempArea.getLayerIndex("Interactive"))) {
                    // No collision
                        0 -> collisionMap[j + xOffset][i + yOffset] = Collide.NONE.ordinal
                    //Cliff left
                        7, 11 //4
                        -> collisionMap[j + xOffset][i + yOffset] = Collide.LEFT_CLIFF.ordinal
                    //Cliff right
                        10, 12 //5
                        -> collisionMap[j + xOffset][i + yOffset] = Collide.RIGHT_CLIFF.ordinal
                    //Cliff down // 6
                        27, 28, 29, 30 -> collisionMap[j + xOffset][i + yOffset] = Collide.DOWN_CLIFF.ordinal
                        else -> collisionMap[j + xOffset][i + yOffset] = Collide.NORMAL.ordinal
                    }
                    when (tempArea.getTileId(j, i, tempArea.getLayerIndex("Floor"))) {
                    //Grass
                        3 ->
                            //this.addEntity("Grass_"+j+"."+i+this, new Grass(j * 16, i * 16, "resources/TileSets/Sprites/grassRustle"));
                            collisionMap[j + xOffset][i + yOffset] = 2
                    //Dark grass
                        6 -> {
                        }
                    }//this.addEntity("Grass_"+j+"."+i+this, new Grass(j * 16, i * 16, "resources/TileSets/Sprites/grassRustle"));
                }
            }
        }
    }

    fun updateAnimationFrames() {
        if (Main.ticks % 16 == 1L) {
            Misc.flowerFrame = Misc.flowerFrame + 1
            if (Misc.flowerFrame > 4) {
                Misc.flowerFrame = 0
            }
        }
        if (Main.ticks % 16 == 0L) {
            Misc.waterFrame = Misc.waterFrame + 1
            if (Misc.waterFrame > 7) {
                Misc.waterFrame = 0
            }
        }
    }

    fun updateRenderedAnimations() {
        for (i in 0 until height) {
            for (j in 0 until width) {
                var floorTile = area.getTileId(j, i, area.getLayerIndex("Floor"))
                var interactiveTile = area.getTileId(j, i, area.getLayerIndex("Interactive"))
                val skyTile = area.getTileId(j, i, area.getLayerIndex("Sky"))

                if (floorTile in 52..57) {
                    floorTile = 52
                    area.setTileId(j, i, area.getLayerIndex("Floor"), floorTile + Misc.flowerFrame)
                }
                if (floorTile in 44..51) {
                    floorTile = 44
                    area.setTileId(j, i, area.getLayerIndex("Floor"), floorTile + Misc.waterFrame)
                }
                if (interactiveTile in 64..79) {
                    interactiveTile = if (interactiveTile % 2 == 0) 64 else 65
                    area.setTileId(j, i, area.getLayerIndex("Interactive"), interactiveTile + 2 * Misc.waterFrame)
                }
                if (interactiveTile in 84..99) {
                    interactiveTile = if (interactiveTile % 2 == 0) 84 else 85
                    area.setTileId(j, i, area.getLayerIndex("Interactive"), interactiveTile + 2 * Misc.waterFrame)
                }
            }
        }
    }

    private fun checkAreaSwitch() {
        val player = EntityManager.getEntity("player") as Player
        // Parameters: xdif, ydif, nextArea,
        if (player.direction === UP && player.y < 0) {
            areaSwitch(player.x + northOffsetX, player.y + RegionManager.getArea(northArea).height * 16, getArea(northArea))
        } else if (player.direction === DOWN && player.y > (RegionManager.currentArea.height - 1) * 16) {
            areaSwitch(player.x + southOffsetX, player.y - RegionManager.currentArea.height * 16, getArea(southArea))
        } else if (player.direction === LEFT && player.x < 0) {
            //TODO: Calculate xpos
            areaSwitch(5, player.y + westOffsetY, getArea(westArea))
        } else if (player.direction === RIGHT && player.x > (RegionManager.currentArea.width - 1) * 16) {
            areaSwitch(5, player.y + eastOffsetY, getArea(eastArea))
        }
    }

    private fun areaSwitch(xpos: Int, ypos: Int, nextArea: Area) {
        val player = EntityManager.getEntity("player") as Player
        //resetEntityRenderPositions()
        EntityManager.clear()
        RegionManager.currentArea = nextArea
        player.setPosition(xpos, ypos)
        EntityManager.areaSwitch(player)
        //EntityManager.areaSwitch(player)
        EntityManager.add("player", player)
    }

    fun getTileId(x: Int, y: Int, layer: Int): Int {
        return area.getTileId(x, y, layer)
    }

    fun getLayerIndex(str: String): Int {
        return area.getLayerIndex(str)
    }

    fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun getEntities(): Map<String, AbstractEntity> {
        return entities
    }

    fun addEntity(name: String, entity: AbstractEntity) {
        entities.put(name, entity)
        entityList.add(name)
    }

    fun removeEntity(name: String) {
        entities.remove(name)
        entityList.remove(name)
    }

    fun setActiveEntities() {
        for (i in entityList.indices) {
            val name = entityList[i]
            val entity = entities[name]!!
            entity.setMapOffset(0, 0)
            EntityManager.add(name, entity)
        }

        for (i in getArea(northArea).entityList.indices) {
            val name = getArea(northArea).entityList[i]
            val entity = getArea(northArea).entities[name]!!
            entity.setMapOffset(northOffsetX, -(getArea(northArea).height * 16))
            EntityManager.add(name, entity)
        }

        for (i in getArea(southArea).entityList.indices) {
            val name = getArea(southArea).entityList[i]
            val entity = getArea(southArea).entities[name]
            entity!!.setMapOffset(southOffsetX, height * 16)
            EntityManager.add(name, entity)
        }
    }

    fun setCollisionValue(x: Int, y: Int, type: Int) {
        collisionMap[x + getArea(westArea).width][y + getArea(northArea).height] = type
    }

    fun init(gc: GameContainer) {

    }
}
