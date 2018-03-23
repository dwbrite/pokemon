package gui

import util.Resources.BORDERS
import util.Resources.userBorder
import org.newdawn.slick.*

/*object Text {

}*/

open class Box(private var w: Int, private var h: Int, private var x: Int, private var y: Int) : GuiElement {
    private var currentBorder = userBorder

    private enum class Tile {
        TOP_LEFT,
        TOP,
        TOP_RIGHT,
        LEFT,
        CENTER,
        RIGHT,
        BOT_LEFT,
        BOT,
        BOT_RIGHT
        ;
        lateinit var image: Image

        fun setTile(image: Image) {
            this.image = image;
        }

        fun draw(x: Int, y: Int) {
            image.draw(x.toFloat(), y.toFloat())
        }

    }

    init {
        updateBorderResource()
    }

    override fun render(gc: GameContainer, g: Graphics) {
        Tile.TOP_LEFT.draw(x, y)
        Tile.TOP_RIGHT.draw(x + (8 * w), y)
        Tile.BOT_LEFT.draw(x, y + (8 * h))
        Tile.BOT_RIGHT.draw(x + (8 * w), y + (8 * h))

        for (i: Int in 1 until w) {
            Tile.TOP.draw(x + (8 * i), y)
            Tile.BOT.draw(x + (8 * i), y + (8 * h))
        }

        for (i: Int in 1 until h) {
            Tile.LEFT.draw(x, y + (8 * i))
            Tile.RIGHT.draw(x + (8 * w), y + (8 * i))
        }

        Tile.CENTER.image.draw(x+8f,y+8f,w*8-8f, h*8-8f)
    }

    override fun update(gc: GameContainer) {
        if(currentBorder != userBorder) {
            currentBorder = userBorder
            updateBorderResource()
        }
    }

    private fun updateBorderResource() {
        val border = BORDERS[userBorder]
        for(i: Int in 0 until border.width/8)
            Tile.values()[i].setTile(border.getSubImage(i*8,0,8,8))
    }
}


