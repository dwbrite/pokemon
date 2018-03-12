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
        addArea(Area(Pair(kanto, "Clear"), "Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 01"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv",
                Pair(kanto, "Viridian City"), Pair(kanto, "Pallet Town"), Pair(kanto, "Clear"), Pair(kanto, "Clear")))
        addArea(Area(Pair(kanto, "Route 02"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 03"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 04"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 05"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 06"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 07"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 08"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 09"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 10"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 11"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 12"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 13"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 14"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 15"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 16"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 17"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 18"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 19"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 20"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 21"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 22"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 23"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Route 24"), "Maps/Kanto/Route1.tmx", "resources/Kanto Region - Route 1.csv"))

        addArea(Area(Pair(kanto, "Pallet Town"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv",
                Pair(kanto, "Route 01"), Pair(kanto, "Route 24"), Pair(kanto, "Clear"), Pair(kanto, "Clear"))) //Pallet

        entities.EntityManager.initAdd("Bob", NPC(256 + 64, 48, Resources.SPRITESHEET["Player Brendan"]!!))
        RegionManager.getArea(Pair("Kanto", "Pallet Town")).addEntity("Bob2", entities.EntityManager.getEntity("Bob")!!)

        addArea(Area(Pair(kanto, "Viridian City"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Viridian
        addArea(Area(Pair(kanto, "Pewter City"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Pewter
        addArea(Area(Pair(kanto, "Cerulean City"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Cerulean
        addArea(Area(Pair(kanto, "Lavender Town"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Lavender
        addArea(Area(Pair(kanto, "Celadon City"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Celadon
        addArea(Area(Pair(kanto, "Vermilion City"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Vermilion
        addArea(Area(Pair(kanto, "Saffron City"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Saffron
        addArea(Area(Pair(kanto, "Fuchsia City"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Fuchsia
        addArea(Area(Pair(kanto, "Cinnabar Island"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Cinnabar
        addArea(Area(Pair(kanto, "Indigo Plateau"), "Maps/Kanto/PalletTown.tmx", "resources/Kanto Region - Route 1.csv")) //Indigo

        addArea(Area(Pair(kanto, "Water"), "Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Trees"), "Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"))
        addArea(Area(Pair(kanto, "Mountains"), "Maps/Clear.tmx", "resources/Kanto Region - Route 1.csv"))


    }
}
