package region

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import util.Camera
import util.Resources.findResources
import java.io.File
import org.newdawn.slick.tiled.TiledMap
import java.io.FileNotFoundException
import util.CollisionType
import util.Direction
import util.Direction.*
import util.Resources


class Area(val metadata: File) {
    val map: TiledMap
    val wildlife: WildlifeParser

    val collisionMap: Array<IntArray>

    val region: String get() = jsonMetadata.get("region").asString
    val name: String get() = jsonMetadata.get("area").asString
    val description: String get() = jsonMetadata.get("description").asString

    val keyPair: Pair<String, String> get() = Pair(region, name)

    val x: Int get() = -Camera.x
    val y: Int get() = -Camera.y

    val width: Int get() = map.width
    val height: Int get() = map.height

    private val clear: Area get() = RegionManager.getArea(Pair("Clear", "Clear"))

    private val jsonMetadata: JsonObject

    init {
        try {
            val mapFile = findResources("map.tmx", metadata.parentFile).first()
            map = TiledMap(mapFile.path)
        } catch (e: NoSuchElementException) {
            throw FileNotFoundException("\"" + metadata.parent + "/map.tmx\" not found.")
        }

        wildlife = WildlifeParser(findResources("pokemon.csv", metadata.parentFile).firstOrNull())
        jsonMetadata = JsonParser().parse(metadata.reader()).asJsonObject

        collisionMap = Array(map.width) {IntArray(map.height)}
        initCollisionMap()
    }

    fun getNeighbor(dir: Direction): Pair<String, String> {
        val direction = dir.name.toLowerCase()
        return try {
            val name = jsonMetadata.getAsJsonObject("neighbors")
                    .getAsJsonObject(direction)
                    .get("area").asString!!
            val area = Pair(region, name)
            if (RegionManager.area.contains(area)) area
            else Pair("Clear", "Clear")
        } catch (e: Exception) {
            Pair("Clear", "Clear")
        }
    }

    fun getOffset(dir: Direction): Int {
        val direction = dir.name.toLowerCase()
        val neighbor = jsonMetadata.getAsJsonObject("neighbors").getAsJsonObject(direction)
        return try { neighbor.get("offset").asInt * 16 }
            catch (e: Exception) { 0 }
    }

    fun render(layerName: String) {
        val layer = map.getLayerIndex(layerName)

        val north = tryGetArea(UP).map
        val south = tryGetArea(DOWN).map
        val east = tryGetArea(RIGHT).map
        val west = tryGetArea(LEFT).map

        north.render(x + getOffset(UP), y - north.height*16, layer)
        west.render(x - west.width*16, y + getOffset(LEFT), layer)
        map.render(x, y, layer)
        east.render(x + map.width * 16, y + getOffset(RIGHT), layer)
        south.render(x + getOffset(DOWN),y + map.height * 16, layer)
    }

    private fun tryGetArea(dir: Direction): Area {
        return try {
            RegionManager.getArea(getNeighbor(dir))
        } catch(e: Exception) {
            System.err.println("Couldn't find " + getNeighbor(dir))
            clear
        }
    }

    private fun initCollisionMap() {
        //TODO("Define collision and tile animations elsewhere")
        for (y in 0 until map.height) for (x in 0 until map.width) {
            when (map.getTileId(x, y, map.getLayerIndex("Interactive"))) {
                0               -> collisionMap[x][y] = CollisionType.NONE.ordinal
                7, 11           -> collisionMap[x][y] = CollisionType.LEFT_CLIFF.ordinal
                10, 12          -> collisionMap[x][y] = CollisionType.RIGHT_CLIFF.ordinal
                27, 28, 29, 30  -> collisionMap[x][y] = CollisionType.DOWN_CLIFF.ordinal
                else -> collisionMap[x][y] = CollisionType.NORMAL.ordinal
            }
            when (map.getTileId(x, y, map.getLayerIndex("Floor"))) {
                3               -> collisionMap[x][y] = CollisionType.GRASS.ordinal
                6               -> collisionMap[x][y] = CollisionType.DARK_GRASS.ordinal
            }
        }
    }

    public fun updateRenderedAnimations() {
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
}