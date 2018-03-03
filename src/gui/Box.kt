package gui

import handlers.Resources
import handlers.Resources.BORDERS
import handlers.Resources.userBorder
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
        for (i: Int in 0..w) for (j: Int in 0..h) {
            val X = x+i*8
            val Y = y+j*8
            when {
                i == 0 && j == 0 -> Tile.TOP_LEFT.draw(X, Y)
                i == 0 && j == h -> Tile.BOT_LEFT.draw(X, Y)
                i == w && j == 0 -> Tile.TOP_RIGHT.draw(X, Y)
                i == w && j == h -> Tile.BOT_RIGHT.draw(X, Y)

                i == 0 -> Tile.LEFT.draw(X, Y)
                i == w -> Tile.RIGHT.draw(X, Y)
                j == 0 -> Tile.TOP.draw(X, Y)
                j == h -> Tile.BOT.draw(X, Y)

                else -> Tile.CENTER.draw(X, Y)
            }
        }
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


