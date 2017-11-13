package area.kanto.cities

import area.AbstractArea
import entities.objectEntities.NPC
import handlers.Resources
import org.newdawn.slick.GameContainer
import region.Kanto
import region.RegionManager

class PalletTown(resource: String, wildlifecsv: String) : AbstractArea(resource, wildlifecsv) {

    override fun init(gc: GameContainer) {
        northAreaValue = Kanto.ROUTE1
        southAreaValue = Kanto.ROUTE21

        northArea = RegionManager.getArea(RegionManager.KANTO, northAreaValue)
        southArea = RegionManager.getArea(RegionManager.KANTO, southAreaValue)
        westArea = RegionManager.getArea(RegionManager.KANTO, westAreaValue)
        eastArea = RegionManager.getArea(RegionManager.KANTO, eastAreaValue)

        initCollisionMap()

        addEntity("bob", NPC(128 + 11 * 16, 48, Resources.SPRITESHEET["Player Brendan"]!!))
    }
}
