package gui

import org.newdawn.slick.*
import org.newdawn.slick.tiled.TiledMap

/*object Text {

}*/

open class MappedBox(var tm: TiledMap) : GuiElement {
    override fun update(gc: GameContainer) {}

    override fun render(gc: GameContainer, g: Graphics) {
        tm.render(0,0)
    }
}


