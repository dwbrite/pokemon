package handlers

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.newdawn.slick.Image
import java.io.File
import java.nio.charset.Charset

object Resources {
    //TODO(" Remove @JvmStatic after conversion to Kotlin is complete.")
    @JvmStatic
    var isReady = false
    @JvmStatic
    val SPRITESHEET: HashMap<String, Image> = HashMap()
    @JvmStatic
    val PARTICLE: HashMap<String, Image> = HashMap()
    @JvmStatic
    val ARENA: HashMap<String, Image> = HashMap()
    @JvmStatic
    val CSV: HashMap<String, List<CSVRecord>> = HashMap()

    @JvmStatic lateinit var splashImage: Image

    @JvmStatic
    fun init() {
        splashImage = Image("Splash.png", false, Image.FILTER_NEAREST)
    }

    @JvmStatic
    fun initPool() {
        try {
            SPRITESHEET["Player Brendan"] = Image("TileSets/Sprites/Player/player.gif", false, Image.FILTER_NEAREST)
            PARTICLE["Grass"] = Image("TileSets/Sprites/grassRustle.gif", false, Image.FILTER_NEAREST)
            PARTICLE["Jump Shadow"] = Image("TileSets/Sprites/shadow.png", false, Image.FILTER_NEAREST)
            PARTICLE["Grass Jump"] = Image("TileSets/Sprites/grassjump.png", false, Image.FILTER_NEAREST)
            PARTICLE["Dirt Jump"] = Image("TileSets/Sprites/dirtjump.png", false, Image.FILTER_NEAREST)

            //*/
            ARENA["1"] = Image("TileSets/Arenas/arena1.png", false, Image.FILTER_NEAREST)
            ARENA["2"] = Image("TileSets/Arenas/arena2.png", false, Image.FILTER_NEAREST)
            ARENA["3"] = Image("TileSets/Arenas/arena3.png", false, Image.FILTER_NEAREST)
            ARENA["4"] = Image("TileSets/Arenas/arena4.png", false, Image.FILTER_NEAREST)
            ARENA["5"] = Image("TileSets/Arenas/arena5.png", false, Image.FILTER_NEAREST)
            ARENA["Cave"] = Image("TileSets/Arenas/cave.png", false, Image.FILTER_NEAREST)
            ARENA["Desert"] = Image("TileSets/Arenas/desert.png", false, Image.FILTER_NEAREST)
            ARENA["Grass 1"] = Image("TileSets/Arenas/grass1.png", false, Image.FILTER_NEAREST)
            ARENA["Grass 2"] = Image("TileSets/Arenas/bgtest1.png", false, Image.FILTER_NEAREST)
            ARENA["Grass 3"] = Image("TileSets/Arenas/grass3.png", false, Image.FILTER_NEAREST)
            ARENA["Land 1"] = Image("TileSets/Arenas/land1.png", false, Image.FILTER_NEAREST)
            ARENA["Land 2"] = Image("TileSets/Arenas/land2.png", false, Image.FILTER_NEAREST)
            ARENA["Swamp"] = Image("TileSets/Arenas/swamp.png", false, Image.FILTER_NEAREST)
            ARENA["Underwater"] = Image("TileSets/Arenas/underwater.png", false, Image.FILTER_NEAREST)
            ARENA["Water"] = Image("TileSets/Arenas/water.png", false, Image.FILTER_NEAREST)
            ARENA["Test 1"] = Image("TileSets/Arenas/bgtest1.png", false, Image.FILTER_NEAREST)
            ARENA["Test 2"] = Image("TileSets/Arenas/bgtest2.png", false, Image.FILTER_NEAREST)
            //*/

            //TODO(" Correct csv data")
            var csvData = File("resources/Pokemon - Species Data.csv")
            var parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL)
            CSV["Pokemon"] = parser.records
            parser.close()

            csvData = File("resources/Pokemon - Species Data.csv") //TODO(" "Evolution Data.csv"")
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL)
            CSV["Evolutions"] = parser.records
            parser.close()

            csvData = File("resources/Pokemon - Species Data.csv") //TODO(" "Type Data.csv"")
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL)
            CSV["Types"] = parser.records
            parser.close()

            csvData = File("resources/Pokemon - Move Data.csv") //TODO(" "Type Data.csv"")
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL)
            CSV["Moves"] = parser.records
            parser.close()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
