package entities.characters

import handlers.Controls
import handlers.Controls.InputDir.*

import org.newdawn.slick.Image

class Player(x: Int, y: Int, spriteSheet: Image) : Trainer(x, y, spriteSheet) {

    override fun uniqueUpdates() {
        /*TODO: I don't even know :/ Maybe fix this crud in the GameCharacter class? Fuck. Maybe keep it this way, because the AI needs inputs too...*/
        controls[UP] = Controls.getInput(UP, this)
        controls[DOWN] = Controls.getInput(DOWN, this)
        controls[LEFT] = Controls.getInput(LEFT, this)
        controls[RIGHT] = Controls.getInput(RIGHT, this)
        /*aActive = Controls.getInput(A, this)
        bActive = Controls.getInput(B, this)
        selectActive = Controls.getInput(SEL, this)
        startActive = Controls.getInput(START, this)
        lActive = Controls.getInput(L, this)
        rActive = Controls.getInput(R, this)*/
    }
}
