package entities.characters

import org.newdawn.slick.Image

class Player(x: Int, y: Int, spriteSheet: Image) : Trainer(x, y, spriteSheet) {
    //TODO("remove player class?")
    override fun uniqueUpdates() {
    }
}
