package gui

import handlers.Resources.FONT
import main.Main
import org.newdawn.slick.*
import org.newdawn.slick.font.effects.ColorEffect
import java.awt.Font
import java.io.FileInputStream

/*object Text {

}*/

open class Text(text: String, private var x: Int, private var y: Int) : GuiElement {
    private var shadow =  true
    private val width = Main.WIDTH / 8 //???
    private val height = 15

    private var textArray: ArrayList<String> = ArrayList()

    init {
        textArray = text.split("\n") as ArrayList<String>
        for ( i in 0 until textArray.size) {
            textArray[i] = " \n" + textArray[i]
        }
    }

    override fun update(gc: GameContainer) {}

    override fun render(gc: GameContainer, g: Graphics) {
        for ( i in 0 until textArray.size) {
            FONT.drawString(x.toFloat(),
                    y.toFloat() - FONT.lineHeight + (i * height) - 3, textArray[i], Color(0,0,0))
        }

        //FONT.drawString(x.toFloat(), y.toFloat(), text, Color(0,0,0))
    }
}


