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
        addArea(Area(Pair(kanto, "Clear"), "res/Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 01"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv",
                Pair(kanto, "Viridian City"), Pair(kanto, "Pallet Town"), Pair(kanto, "Clear"), Pair(kanto, "Clear")))
        addArea(Area(Pair(kanto, "Route 02"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 03"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 04"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 05"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 06"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 07"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 08"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 09"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 10"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 11"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 12"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 13"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 14"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 15"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 16"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 17"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 18"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 19"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 20"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 21"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 22"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 23"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Route 24"), "res/Maps/Kanto/Route 01/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))

        addArea(Area(Pair(kanto, "Pallet Town"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv",
                Pair(kanto, "Route 01"), Pair(kanto, "Route 24"), Pair(kanto, "Clear"), Pair(kanto, "Clear"))) //Pallet

        entities.EntityManager.initAdd("Bob", NPC(256 + 64, 48, Resources.SPRITESHEET["Player Brendan"]!!))
        RegionManager.getArea(Pair("Kanto", "Pallet Town")).addEntity("Bob2", entities.EntityManager.getEntity("Bob")!!)

        addArea(Area(Pair(kanto, "Viridian City"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Viridian
        addArea(Area(Pair(kanto, "Pewter City"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Pewter
        addArea(Area(Pair(kanto, "Cerulean City"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Cerulean
        addArea(Area(Pair(kanto, "Lavender Town"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Lavender
        addArea(Area(Pair(kanto, "Celadon City"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Celadon
        addArea(Area(Pair(kanto, "Vermilion City"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Vermilion
        addArea(Area(Pair(kanto, "Saffron City"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Saffron
        addArea(Area(Pair(kanto, "Fuchsia City"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Fuchsia
        addArea(Area(Pair(kanto, "Cinnabar Island"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Cinnabar
        addArea(Area(Pair(kanto, "Indigo Plateau"), "res/Maps/Kanto/Pallet Town/map.tmx", "res/Maps/Kanto/Route 01/pokemon.csv")) //Indigo

        addArea(Area(Pair(kanto, "Water"), "res/Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Trees"), "res/Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))
        addArea(Area(Pair(kanto, "Mountains"), "res/Maps/Clear/Clear.tmx", "res/Maps/Kanto/Route 01/pokemon.csv"))


    }
}
