package region.area

import entities.AbstractEntity
import entities.EntityManager
import entities.characters.GameCharacter
import handlers.Camera
import handlers.Resources
import handlers.controls.Controls.InputDir.*
import main.Main
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.tiled.TiledMap
import region.RegionManager
import java.io.File
import java.nio.charset.Charset
import java.util.*
import region.area.Cardinality.*

enum class Cardinality {
    NORTH,
    SOUTH,
    EAST,
    WEST
}


enum class CollisionType {
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

class Area(var areaKey: Pair<String, String>,
           path: String,
           wildlifeCsv: String,
           var areaKeyPair: HashMap<Cardinality, Pair<String, String>>) {

    constructor (areaKey: Pair<String, String>,
                 path: String,
                 wildlifeCsv: String) : this(areaKey, path, wildlifeCsv, HashMap<Cardinality, Pair<String, String>>()) {
        areaKeyPair[NORTH] = Pair(areaKey.first, "Clear")
        areaKeyPair[SOUTH] = Pair(areaKey.first, "Clear")
        areaKeyPair[EAST] = Pair(areaKey.first, "Clear")
        areaKeyPair[WEST] = Pair(areaKey.first, "Clear")
    }

    constructor (areaKey: Pair<String, String>,
                 path: String,
                 wildlifeCsv: String,
                 northAreaKey: Pair<String, String>? = Pair(areaKey.first, "Clear"),
                 southAreaKey: Pair<String, String>? = Pair(areaKey.first, "Clear"),
                 westAreaKey: Pair<String, String>? = Pair(areaKey.first, "Clear"),
                 eastAreaKey: Pair<String, String>? = Pair(areaKey.first, "Clear")
                 ) : this(areaKey, path, wildlifeCsv, HashMap<Cardinality, Pair<String, String>>()) {
        areaKeyPair[NORTH] = northAreaKey!!
        areaKeyPair[SOUTH] = southAreaKey!!
        areaKeyPair[EAST] = eastAreaKey!!
        areaKeyPair[WEST] = westAreaKey!!
    }

    private lateinit var map: TiledMap

    internal val neighbor = HashMap<Cardinality, Area>()

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

    lateinit var region: String

    init {
        try {
            map = TiledMap(path)

            val csvData = File(wildlifeCsv)
            val parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL)
            wildlifeData = parser.records
        } catch (e: Exception) {
            e.printStackTrace()
        }

        width = map.width
        height = map.height
        name = areaKey.second
    }

    fun render(gc: GameContainer, g: Graphics) {
        neighbor[NORTH]!!.updateRenderedAnimations()
        neighbor[WEST]!!.updateRenderedAnimations()
        updateRenderedAnimations()
        neighbor[EAST]!!.updateRenderedAnimations()
        neighbor[SOUTH]!!.updateRenderedAnimations()

        neighbor[NORTH]!!.renderFloor(x, y - neighbor[NORTH]!!.height * 16)
        renderFloor(x, y)
        neighbor[SOUTH]!!.renderFloor(x, y + height * 16)

        EntityManager.render(gc, g)

        neighbor[NORTH]!!.renderSky(x, y - neighbor[NORTH]!!.height * 16)
        renderSky(x, y)
        neighbor[SOUTH]!!.renderSky(x, y + height * 16)
    }

    private fun renderFloor(x: Int, y: Int) {
        map.render(x, y, map.getLayerIndex("Floor"))
        map.render(x, y, map.getLayerIndex("Interactive"))
    }

    private fun renderSky(x: Int, y: Int) {
        map.render(x, y, map.getLayerIndex("Sky"))
    }

    fun getCollisionValue (x: Int, y: Int): Int {
        return collisionMap[x + neighbor[WEST]!!.width][y + neighbor[NORTH]!!.height]
    }

    fun update(gc: GameContainer) {
        updateAnimationFrames()
        updateCollisionMap()
        EntityManager.update(gc)
        checkAreaSwitch()
    }

    private fun updateCollisionMap() {
        neighbor[NORTH]!!.updateCollisionMap(neighbor[WEST]!!.width, 0)
        neighbor[WEST]!!.updateCollisionMap(0, neighbor[NORTH]!!.height)
        updateCollisionMap(neighbor[WEST]!!.width, neighbor[NORTH]!!.height)
        neighbor[EAST]!!.updateCollisionMap(neighbor[WEST]!!.width + width, neighbor[NORTH]!!.height)
        neighbor[SOUTH]!!.updateCollisionMap(neighbor[WEST]!!.width, neighbor[NORTH]!!.height + height)
    }

    private fun updateCollisionMap(xOffset: Int, yOffset: Int) {
        //TODO("Define collision and tile animations elsewhere")
        for (y in 0 until height) for (x in 0 until width) {
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

    private fun updateAnimationFrames() {
        //TODO("Rename function to reflect proper purpose")
        if (Main.ticks % 16 == 0L) {
            Resources.waterFrame = Resources.waterFrame + 1
            if (Resources.waterFrame > 7) {
                Resources.waterFrame = 0
            }
        }
        if (Main.ticks % 16 == 1L) {
            Resources.flowerFrame = Resources.flowerFrame + 1
            if (Resources.flowerFrame > 4) {
                Resources.flowerFrame = 0
            }
        }
    }

    private fun updateRenderedAnimations() {
        //TODO("Rename function to reflect proper purpose")
        for (y in 0 until height) for (x in 0 until width) {
            var floorTile = map.getTileId(x, y, map.getLayerIndex("Floor"))
            var interactiveTile = map.getTileId(x, y, map.getLayerIndex("Interactive"))
            val skyTile = map.getTileId(x, y, map.getLayerIndex("Sky"))

            if (floorTile in 52..57) {
                floorTile = 52
                map.setTileId(x, y, map.getLayerIndex("Floor"), floorTile + Resources.flowerFrame)
            }
            if (floorTile in 44..51) {
                floorTile = 44
                map.setTileId(x, y, map.getLayerIndex("Floor"), floorTile + Resources.waterFrame)
            }
            if (interactiveTile in 64..79) {
                interactiveTile = if (interactiveTile % 2 == 0) 64 else 65
                map.setTileId(x, y, map.getLayerIndex("Interactive"), interactiveTile + 2 * Resources.waterFrame)
            }
            if (interactiveTile in 84..99) {
                interactiveTile = if (interactiveTile % 2 == 0) 84 else 85
                map.setTileId(x, y, map.getLayerIndex("Interactive"), interactiveTile + 2 * Resources.waterFrame)
            }
        }
    }

    private fun checkAreaSwitch() {
        val entity = (Camera.followedEntity!! as? GameCharacter)!!
        when (entity.direction) {
            UP -> if (entity.y < 0) areaSwitch(neighbor[NORTH]!!)
            DOWN -> if (entity.y > (RegionManager.currentArea.height - 1) * 16) areaSwitch(neighbor[SOUTH]!!)
            LEFT -> if (entity.x < 0) areaSwitch(neighbor[WEST]!!)
            RIGHT -> if (entity.x > (RegionManager.currentArea.width - 1) * 16) areaSwitch(neighbor[EAST]!!)
        }
    }

    private fun areaSwitch(nextArea: Area) {
        RegionManager.currentArea = nextArea
        EntityManager.areaSwitch((Camera.followedEntity!! as? GameCharacter)!!.direction)
    }

    fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun getEntities(): Map<String, AbstractEntity> {
        return entities
    }

    fun addEntity(name: String, entity: AbstractEntity) {
        entities[name] = entity
    }

    fun removeEntity(name: String) {
        entities.remove(name)
    }

    fun setCollisionValue(x: Int, y: Int, type: Int) {
        collisionMap[x + neighbor[WEST]!!.width][y + neighbor[NORTH]!!.height] = type
    }

    fun init() {
        for (direction in Cardinality.values())
            neighbor[direction] = RegionManager.getArea(areaKeyPair[direction]!!)

        collisionMap = Array(width + neighbor[WEST]!!.width + neighbor[EAST]!!.width)
        {IntArray(height + neighbor[SOUTH]!!.height + neighbor[NORTH]!!.height)}
    }
}