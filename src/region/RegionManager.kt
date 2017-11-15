package region

import region.area.Area
import handlers.Camera
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics

object RegionManager {
    val KANTO = 0

    val area = HashMap<Pair<String, String>, Area>()
    lateinit var currentArea: Area

    val northArea: Area get() = getArea(currentArea.northArea)
    val southArea: Area get() = getArea(currentArea.southArea)
    val westArea: Area get() = getArea(currentArea.westArea)
    val eastArea: Area get() = getArea(currentArea.eastArea)

    @JvmStatic
    fun init(gc: GameContainer) {
        Kanto.init()
        for (name in area.keys)
            area[name]!!.init(gc)
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
        /*TODO:*/
    }

    fun addArea(area: Area) {
        this.area[area.areaKey] = area
    }
}
