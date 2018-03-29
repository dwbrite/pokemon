package region

import gamestate.InGameState
import org.newdawn.slick.tiled.TiledMap
import util.CollisionType
import util.CollisionType.*
import util.Direction

class CollisionMap(width: Int, height: Int) {
    private val matrix: Array<Array<CollisionType>> = Array(width) { Array(height, { _ -> NONE }) }

    constructor(map: TiledMap) : this(map.width, map.height) {
        for (y in 0 until map.height) for (x in 0 until map.width) {
            when (map.getTileId(x, y, map.getLayerIndex("Interactive"))) {
                0               -> matrix[x][y] = NONE
                7, 11           -> matrix[x][y] = LEFT_CLIFF
                10, 12          -> matrix[x][y] = RIGHT_CLIFF
                27, 28, 29, 30  -> matrix[x][y] = DOWN_CLIFF
                else -> matrix[x][y] = NORMAL
            }
            when (map.getTileId(x, y, map.getLayerIndex("Floor"))) {
                3               -> matrix[x][y] = GRASS
                6               -> matrix[x][y] = DARK_GRASS
            }
        }
    }

    constructor(north: TiledMap, west: TiledMap, center: TiledMap, east: TiledMap, south: TiledMap) : this(RegionManager.fullWidth, RegionManager.fullHeight) {
        // TODO("This may break in circumstances where an edge area is longer than the perpendicular middle 3 areas.")
        // ^ A possible solution may be to just break up the areas? If it's ever a problem. It might not be. @gif idk
        updateCollisionFrom(north, west.width + RegionManager.currentArea.getOffset(Direction.UP) / 16, 0)
        updateCollisionFrom(west, 0, north.height + RegionManager.currentArea.getOffset(Direction.LEFT) / 16)
        updateCollisionFrom(center, west.width, north.height)
        updateCollisionFrom(east, west.width + center.width, north.height + RegionManager.currentArea.getOffset(Direction.RIGHT) / 16)
        updateCollisionFrom(south, west.width + RegionManager.currentArea.getOffset(Direction.DOWN) / 16, north.height + center.height)
    }

    private fun updateCollisionFrom(map: TiledMap, xOffset: Int, yOffset: Int) {
        for (y in 0 until map.height) for (x in 0 until map.width) {
            when (map.getTileId(x, y, map.getLayerIndex("Interactive"))) {
                0 -> matrix[x + xOffset][y + yOffset] = CollisionType.NONE
                7, 11 -> matrix[x + xOffset][y + yOffset] = CollisionType.LEFT_CLIFF
                10, 12 -> matrix[x + xOffset][y + yOffset] = CollisionType.RIGHT_CLIFF
                27, 28, 29, 30 -> matrix[x + xOffset][y + yOffset] = CollisionType.DOWN_CLIFF
                else -> matrix[x + xOffset][y + yOffset] = CollisionType.NORMAL
            }
            when (map.getTileId(x, y, map.getLayerIndex("Floor"))) {
                3 -> matrix[x + xOffset][y + yOffset] = CollisionType.GRASS
                6 -> matrix[x + xOffset][y + yOffset] = CollisionType.DARK_GRASS
            }
        }
    }

    fun getCollisionValue (x: Int, y: Int): CollisionType {
        return matrix[x + RegionManager.getNeighbor(Direction.LEFT).width][y + RegionManager.getNeighbor(Direction.UP).height]
    }

    fun setCollisionValue(x: Int, y: Int, type: CollisionType) {
        matrix[x + RegionManager.getNeighbor(Direction.LEFT).width][y + RegionManager.getNeighbor(Direction.UP).height] = type
    }
}