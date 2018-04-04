package gamestate

import battle.BattleServer
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics

object BattleState : AbstractGameState() {
    //TODO("Should the BattleState be the server?")

    override fun render(gc: GameContainer, g: Graphics) {
        BattleServer.render(gc, g)
    }

    override fun update(gc: GameContainer) {
        BattleServer.update(gc)
    }

}
