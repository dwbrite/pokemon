package region

import entities.characters.NPC
import handlers.Resources
import region.RegionManager.addArea
import region.area.Area

object Kanto {
    //TODO(" Hmmm... Can this be done better?")
    @JvmStatic
    fun init() {
        val kanto = "Kanto"
        addArea(Area(Pair(kanto, "Clear"), "Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 01"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv",
                Pair(kanto, "Viridian City"), Pair(kanto, "Pallet Town"), Pair(kanto, "Clear"), Pair(kanto, "Clear")))
        addArea(Area(Pair(kanto, "Route 02"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 03"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 04"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 05"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 06"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 07"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 08"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 09"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 10"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 11"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 12"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 13"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 14"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 15"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 16"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 17"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 18"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 19"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 20"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 21"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 22"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 23"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 24"), "Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))

        addArea(Area(Pair(kanto, "Pallet Town"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv",
                Pair(kanto, "Route 01"), Pair(kanto, "Route 24"), Pair(kanto, "Clear"), Pair(kanto, "Clear"))) //Pallet

        entities.EntityManager.initAdd("Bob", NPC(256 + 64, 48, Resources.SPRITESHEET["Player Brendan"]!!))
        RegionManager.getArea(Pair("Kanto", "Pallet Town")).addEntity("Bob2", entities.EntityManager.getEntity("Bob")!!)

        addArea(Area(Pair(kanto, "Viridian City"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Viridian
        addArea(Area(Pair(kanto, "Pewter City"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Pewter
        addArea(Area(Pair(kanto, "Cerulean City"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Cerulean
        addArea(Area(Pair(kanto, "Lavender Town"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Lavender
        addArea(Area(Pair(kanto, "Celadon City"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Celadon
        addArea(Area(Pair(kanto, "Vermilion City"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Vermilion
        addArea(Area(Pair(kanto, "Saffron City"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Saffron
        addArea(Area(Pair(kanto, "Fuchsia City"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Fuchsia
        addArea(Area(Pair(kanto, "Cinnabar Island"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Cinnabar
        addArea(Area(Pair(kanto, "Indigo Plateau"), "Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Indigo

        addArea(Area(Pair(kanto, "Water"), "Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Trees"), "Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Mountains"), "Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))


    }
}
