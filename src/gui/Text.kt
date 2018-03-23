package gui

import util.Resources.FontColor
import util.Resources.FONT
import main.Main
import org.newdawn.slick.*

open class Text(text: String, private var x: Int, private var y: Int, var color: FontColor) : GuiElement {


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
        if(shadow) {
            drawText(x.toFloat()+1, y.toFloat(), color.background)
            drawText(x.toFloat(), y.toFloat()+1, color.background)
            drawText(x.toFloat()+1, y.toFloat()+1, color.background)
        }
        drawText(x.toFloat(), y.toFloat(), color.foreground)
    }

    private fun drawText(x: Float, y: Float, color: Color) {
        for ( i in 0 until textArray.size) {
            FONT.drawString(x, y - FONT.lineHeight + (i * height) - 3, textArray[i], color)
        }
    }
}


