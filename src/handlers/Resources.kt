package handlers

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import org.newdawn.slick.Image
import org.newdawn.slick.SpriteSheet
import org.newdawn.slick.UnicodeFont
import org.newdawn.slick.font.effects.ColorEffect
import java.awt.Font
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset

object Resources {
    //TODO(" Remove @JvmStatic after conversion to Kotlin is complete.")
    @JvmStatic var flowerFrame: Int = 0
    @JvmStatic var waterFrame: Int = 0
    @JvmStatic var userBorder = 26 //TODO(check that this is never over 28 at some point. Maybe.

    //@JvmStatic val FONT = generateFontFromName("Power Red and Green", 11)
    @JvmStatic val FONT = generateFontFromUrl("resources/Fonts/PocketPower.ttf", 10f)!!

    @JvmStatic var isReady = false
    @JvmStatic val SPRITESHEET: HashMap<String, Image> = HashMap()
    @JvmStatic val PARTICLE: HashMap<String, Image> = HashMap()
    @JvmStatic val ARENA: HashMap<String, Image> = HashMap()
    @JvmStatic val CSV: HashMap<String, List<CSVRecord>> = HashMap()
    @JvmStatic val BORDERS: ArrayList<Image> = ArrayList()

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

            //TODO("Fix csv data")
            generateCsvData("Pokemon",  "resources/Pokemon - Species Data.csv")
            //generateCsvData("Evolution","resources/Pokemon - Evolution Data.csv")
            //generateCsvData("Types",    "resources/Pokemon - Type Data.csv")
            generateCsvData("Moves",    "resources/Pokemon - Move Data.csv")

            for (i in 1..28) {
                BORDERS.add(Image("TileSets/Borders/" + i + ".png", false, Image.FILTER_NEAREST))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun generateFontFromUrl(url: String, defaultSize: Float): UnicodeFont? {
        return try {
            var f = Font.createFont(Font.TRUETYPE_FONT, FileInputStream(url))
            f = f.deriveFont(defaultSize)

            val uf = UnicodeFont(f)
            uf.addAsciiGlyphs()
            uf.addGlyphs("""ABCDEFGHIJKLMNOPQRSTUVWXYZ
                abcdefghijklmnopqrstuvwxyz
                0123456789
                .,“”‘’"'?!@_*#${'$'}%&()+-/:;<=>[\]^`{|}~¡¿
                ÀÁÂÄÇÈÉÊËÌÍÎÏÑÒÓÔÖ×ÙÚÛÜß
                àáâäçèéêëìíîïñòóôöùúûüŒœʟ…
                ₽℠℡℻←↑→↓►◄♂♀""")
            uf.effects.add(ColorEffect())
            uf.loadGlyphs()
            uf
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun generateFontFromName(name: String, defaultSize: Int): UnicodeFont {
        val uf = UnicodeFont(Font(name, Font.PLAIN, defaultSize))
        uf.addAsciiGlyphs()
        uf.addGlyphs("""ßé0123456789!-…‥«»<'"♂♀$,*/ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\u20BD:ÄÖÜ""")
        uf.effects.add(ColorEffect())
        try {
            uf.loadGlyphs()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return uf
    }

    fun generateCsvData(hashname: String, resource: String) {
        val csvData = File(resource)
        val parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.EXCEL)
        CSV[hashname] = parser.records
        parser.close()
    }
}
