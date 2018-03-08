package region

import handlers.Camera
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.area.Area
import region.area.Cardinality
import region.area.Cardinality.*

object RegionManager {
    val area = HashMap<Pair<String, String>, Area>()
    lateinit var currentArea: Area

    fun getNeighbor(dir: Cardinality): Area
    { return currentArea.neighbor[dir]!! }

    @JvmStatic
    fun init(gc: GameContainer) {
        Kanto.init()
        for (area in area.values) area.init()
        currentArea = getArea(Pair("Kanto", "Pallet Town"))
        Camera.update()
    }

    fun render(gc: GameContainer, g: Graphics) {
        currentArea.render(gc, g)
    }

    fun update(gc: GameContainer) {
        currentArea.update(gc)
    }

    fun getRegion(region: String): String {
        return currentArea.region
    }

    fun getArea(areaKey: Pair<String, String>): Area {
        return area[areaKey]!!
    }

    fun setCurrentArea(areaKey: Pair<String, String>) {
        currentArea = area[areaKey]!!
    }

    fun addArea(area: Area) {
        this.area[area.areaKey] = area
    }
}
