package battle

import entities.Animation
import entities.objectEntities.Trainer
import entities.pokemon.Pokemon
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import org.newdawn.slick.geom.Rectangle

/**
 * Created by dwbrite on 4/13/16.
 */
abstract class AbstractBattle {
    protected var arena: Image? = null

    protected var allyPokemon: Array<Pokemon>? = null
    protected var enemyPokemon: Array<Pokemon>? = null
    protected var animationTicks: Long = 0
    protected var pokemonSwitchTicks = 0
    protected var allySprites: Array<Animation>? = null
    protected var enemySprites: Array<Animation>? = null
    protected var allyTrainers: Array<Trainer>? = null
    protected var enemyTrainers: Array<Trainer>? = null

    fun render(gc: GameContainer, g: Graphics) {
        g.drawImage(arena, 0f, 0f)

        when (BattleManager.currentBattle) {
            is WildBattle -> {
                val ax1 = 24;
                val ay1 = 64
                val bx1 = 226;
                val by1 = -32

                g.draw(Rectangle(ax1.toFloat(), ay1.toFloat(), 128f, 128f))
                g.draw(Rectangle(bx1.toFloat(), by1.toFloat(), 128f, 128f))

                g.drawImage(allySprites!![0].currentFrame, ax1.toFloat(), ay1.toFloat())
                g.drawImage(enemySprites!![0].currentFrame, bx1.toFloat(), by1.toFloat())
            }
            else -> {
                //TODO: Fix these values
                val ax1 = 64;
                val ay1 = 48
                val bx1 = 186;
                val by1 = -16
                val ax2 = 64;
                val ay2 = 48
                val bx2 = 186;
                val by2 = -16
                g.drawImage(allySprites!![0].currentFrame, ax1.toFloat(), ay1.toFloat())
                g.drawImage(allySprites!![1].currentFrame, ax2.toFloat(), ay2.toFloat())
                g.drawImage(enemySprites!![0].currentFrame, bx1.toFloat(), by1.toFloat())
                g.drawImage(enemySprites!![1].currentFrame, bx2.toFloat(), by2.toFloat())
            }
        }
        // TODO: render UI?
    }

    fun update(gc: GameContainer) {
        animationTicks++
        if (animationTicks % 3 == 0L) {
            allySprites!![0].nextFrame()
            enemySprites!![0].nextFrame()
        }
    }
}

/*/
     *  // Okay! Now to do the real battle-y things
     *  // We need do go over *every* *single* *step*.
     *  // So first thing after the battle transition:
     *      {
     *          Wild battle:
     *              "A wild rattata has appeared"
     *          Normal battle:
     *              [Trainer] would like to battle!
     *              [Trainer] sent out [pokemon]
     *      }
     *      {Player:} Go! [First conscious pokemon]!
     *  // Now that the pokemon are out
     *  //Player's turn
     *      for each pokemon that's out
     *          actions (attack, use item, switch mon, or run)
     *  //Enemy's turn
     *      repeat action choice for opponent (attack, use item, switch mon, ((AI)?" ":"run"))
     *
     *  //Now we have to animate the actions
     *      //# Maybe assign a value for speed to each "battle action"
     *      check which pokemon should go first based on the action done.
     *          {
     *              If the action was a bag, switch, or run, attempt that first. Player first, then opponent
     *                  don't do beginTurnUpdate() for each effect, because you're forfeiting the attack turn
     *                  do the switch/run/item and checks that apply to them
     *              If the action was a move, whichever Pokemon or move is faster occurs first.
     *                  then do beginTurnUpdate() for each effect
     *                  do the attack
     *          }
     *          After all turns have finished, do endTurnUpdate() for each effect
     *
     *      Now check for K.Os and either force a switch or end the battle
     *          Give EXP for the K.O. (this should automatically check for level-ups and grant new moves if necessary)
     *      Player win:
     *          Enemy loses, bring up winning text and give money
     *      Player lose:
     *          Player sent
     *  Done this turn. Repeat.
    //*/
/**
 * GuiManager guiman1 = GameStateManager.getGameState(1).getGuiManager();
 * GuiBattleWildMon wildMonBox = new GuiBattleWildMon(battle.enemyPokemon[0], battle.allyTrainers[0]);
 */
}
        */
        */