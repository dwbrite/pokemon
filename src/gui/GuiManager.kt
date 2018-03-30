package gui

import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics

object GuiManager {
    private val elements = ArrayList<GuiElement>()

    @JvmStatic
    fun init(gc: GameContainer) {
        /*var text = Text("RED used SURF (or something).\n" +
                "The quick brown fox jumps over the...", 12, 124, Resources.FontColor.LIGHT)
        var box = Box(29, 5, 0,112)
        elements.add(box)
        elements.add(text)
        */
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
