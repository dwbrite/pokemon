package gui

import handlers.Resources
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.tiled.TiledMap

object GuiManager {


    val elements = ArrayList<GuiElement>()

    @JvmStatic
    fun init(gc: GameContainer) {
        var text = Text("RED used SURF (or something).\n" +
                "The quick brown fox jumps over the...", 12, 124, Resources.FontColor.LIGHT)
        var mb = MappedBox(TiledMap("Maps/battleTextArea.tmx"))

        elements.add(mb)
        elements.add(text)
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
