package gui

import handlers.Resources.FONT
import main.Main
import org.newdawn.slick.*
import org.newdawn.slick.font.effects.ColorEffect
import java.awt.Font
import java.io.FileInputStream

/*object Text {

}*/

open class Text(private var text: String, private var x: Int, private var y: Int) : GuiElement {
    private var shadow =  true
    private val width = Main.WIDTH / 8
    private val height = 6


    override fun update(gc: GameContainer) {}

    override fun render(gc: GameContainer, g: Graphics) {
        FONT.drawString(x.toFloat(), y.toFloat(), text, Color(0,0,0))
    }
}


