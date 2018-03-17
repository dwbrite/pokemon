package region

import handlers.Resources.findResources
import java.io.File
import org.newdawn.slick.tiled.TiledMap
import java.io.FileNotFoundException


class Area(val metadata: File) {
    val map: TiledMap
    val pokemon: File?

    val region: String
        get() = TODO("Create getter with JSON parser")

    init {
        try {
            val mapFile = findResources("map.tmx", metadata.parentFile).firstOrNull()!!
            map = TiledMap(mapFile.path)
        } catch (e: NullPointerException) {
            throw FileNotFoundException("\"" + metadata.parent + "/map.tmx\" not found.")
        }

        pokemon = findResources("pokemon.csv", metadata.parentFile).firstOrNull()
    }
}