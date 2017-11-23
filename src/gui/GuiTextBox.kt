package gui

import gui.childElements.GuiBox
import gui.childElements.GuiElement
import gui.childElements.GuiTextBuilder
import handlers.controls.Controls
import handlers.controls.Controls.InputKey.*
import main.Main
import org.newdawn.slick.SpriteSheet


/**
 * Created by dwbrite on 5/14/16.
 */
open class GuiTextBox(private val text: String, border: SpriteSheet) : GuiElement(true) {
    private val width = Main.WIDTH / 8
    private val height = 6

    private val x = 0
    private val y = Main.HEIGHT - 8 * height

    private val box: GuiBox

    private val guitext: GuiTextBuilder

    private var textHasFinished: Boolean = false

    private var stringPosition = 0

    private val u25bcOffset = 0

    init {
        box = GuiBox(x, y, width, height, border)
        guitext = GuiTextBuilder("") //TODO: fix
        guitext.setPosition(x + 12, y + 10)
    }

    override fun render() {
        /*
        box.draw()
        guitext.draw()
        val newx = x + (text.length - 1) * 6
        val newy = y + 8
        if (textHasFinished) {
            val ttb = GuiTextBuilder("")//TODO: wtf man... wtf... This file was broken in the recovery.
        } else if (Controls.getInput(this, B)) {
            guitext.setString(text)
            textHasFinished = true
        } else {
            if (stringPosition != text.length + 1) {
                guitext.setString(text.substring(0, stringPosition))
                stringPosition++
            } else {
                textHasFinished = true
            }
        }
        */
    }

    override fun uniqueUpdate() {

    }

    open fun activate() {
        println("""Devin says "wrong one". You might be looking for something else... Maybe. Darn it past Devin, you should document your code!""")
    }
}
