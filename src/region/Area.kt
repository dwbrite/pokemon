package region

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import handlers.Resources.findResources
import java.io.File
import org.newdawn.slick.tiled.TiledMap
import region.area.Cardinality
import java.io.FileNotFoundException


class Area(val metadata: File) {
    val map: TiledMap
    val pokemon: File?

    val region: String
        get() = jsonMetadata.get("region").asString

    val name: String
        get() = jsonMetadata.get("area").asString

    val description: String
        get() = jsonMetadata.get("description").asString

    val keyPair: Pair<String, String>
        get() = Pair(region, name)

    private val jsonMetadata: JsonObject

    init {
        try {
            val mapFile = findResources("map.tmx", metadata.parentFile).first()
            map = TiledMap(mapFile.path)
        } catch (e: NullPointerException) {
            throw FileNotFoundException("\"" + metadata.parent + "/map.tmx\" not found.")
        }

        pokemon = findResources("pokemon.csv", metadata.parentFile).firstOrNull()

        jsonMetadata = JsonParser().parse(metadata.toString()).asJsonObject!!
    }

    fun getNeighbor(dir: Cardinality): Pair<String, String> {
        val direction = dir.name.toLowerCase()
        var name: String? = jsonMetadata.getAsJsonObject("neighbors")
                .getAsJsonObject(direction)
                .get("area").asString

        //TODO("Hope this is how the API works")
        if (name == null) { name = "Clear" }

        return Pair(region, name)
    }

    fun getOffset(dir: Cardinality): Int {
        val direction = dir.name.toLowerCase()
        val neighbor = jsonMetadata.getAsJsonObject("neighbors").getAsJsonObject(direction)
        return if (neighbor.isJsonNull) {
            0
        } else {
            neighbor.get("offset").asInt
        }
    }
}