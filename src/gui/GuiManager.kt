package gui

import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.tiled.TiledMap

object GuiManager {

    val elements = ArrayList<GuiElement>()

    @JvmStatic
    fun init(gc: GameContainer) {
        var text = Text("RED used SURF or something.", 4, 48)
        var text2 = Text("The quick brown fox jumps over the lazy dog.", 4, 32)
        var mb = MappedBox(TiledMap("Maps/chooseapkmn.tmx"))

        elements.add(text)
        elements.add(text2)
        elements.add(mb)
    }

    @JvmStatic
    fun render(gc: GameContainer, g: Graphics) {
        elements.forEach({ it.render(gc, g) })
    }

    @JvmStatic
    fun update(gc: GameContainer) {
        elements.forEach({ it.update(gc) })
    }
}

interface GuiElement {
    fun update(gc: GameContainer)
    fun render(gc: GameContainer, g: Graphics)
}
