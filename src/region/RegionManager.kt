package region

import org.newdawn.slick.GameContainer
import util.Camera
import util.Direction.*
import util.Direction
import util.Resources
import java.io.File

object RegionManager {
    val area = HashMap<Pair<String, String>, Area>()
    lateinit var currentArea: Area

    val fullWidth: Int get() = {currentArea.width + getNeighbor(LEFT).width + getNeighbor(RIGHT).width}()
    val fullHeight: Int get() = {currentArea.height + getNeighbor(UP).height + getNeighbor(DOWN).height}()

    fun getNeighbor(dir: Direction): Area
    { return getArea(currentArea.getNeighbor(dir)) }

    @JvmStatic
    fun init(gc: GameContainer) {
        val areas = Resources.findResources("area.json", File("res/Maps"))
        for (a in areas) {
            addArea(Area(a))
        }

        currentArea = getArea(Pair("Kanto", "Pallet Town"))

        Camera.update()
    }

    fun getArea(areaKey: Pair<String, String>): Area {
        return area[areaKey]!!
    }

    private fun addArea(area: Area) {
        this.area[area.keyPair] = area
    }
}
