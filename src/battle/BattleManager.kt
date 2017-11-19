package battle

import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics

/**
 * Created by dwbrite on 4/13/16.
 */
object BattleManager {
    var currentBattle: AbstractBattle? = null

    fun render(gc: GameContainer, g: Graphics) {
        currentBattle!!.render(gc, g)
    }

    fun update(gc: GameContainer) {
        currentBattle!!.update(gc)
    }
}

