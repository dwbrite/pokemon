package entities.particles

import entities.characters.GameCharacter
import handlers.Resources
import main.Main

/**
 * Created by dwbrite on 5/15/16.
 */
class NormalGrassParticle(x: Int, y: Int, private val parent: GameCharacter) : Particle(x, y, Resources.PARTICLE["Grass"]!!, -1.0) {

    private var hasParentFinishedStep = false
    private var isStepOffActivated = false
    private var stepOffTick: Long = 0
    private var pokemonChecked = false

    override fun isDead(): Boolean {
        if (parent.x == x && parent.y == y) {
            hasParentFinishedStep = true
        }

        /* TODO: Remove this. This __should__ be elsewhere - probably in the gamestatemachine?
        if (parent is Player && hasParentFinishedStep && !pokemonChecked) {
            /* TODO: create transition effects
	         * Water: Wave Distort
             * 		Wave "Shadow"
             * Grass: Black horizontal lines
             *		White thick lines
             * Cave: Pixelate
             * 		Boxes closing ish
             */

            pokemonChecked = true

            val f = Math.random()

            if (f < 0.1) {
                //Enters a Pokemon Battle
                val mon = Pokemon.generateWildPokemon(0, RegionManager.currentArea.wildlifeData)
                BattleManager.currentBattle = WildBattle(Resources.ARENA["Grass 2"], mon, EntityManager.getEntity("player") as Trainer)
                println(mon)
                val transition = 0
                GameStateMachine.transitionToBattle(transition)
                parent.busy = true
                parent.currentAction = GameCharacter.Action.IDLING
            }
        }
        */

        if (Main.ticks - startingTick >= 52 && !isStepOffActivated && (parent.x != x || parent.y != y)) {
            stepOffTick = Main.ticks + 11
            isStepOffActivated = true
        }

        return Main.ticks >= stepOffTick && isStepOffActivated
    }

    override fun uniqueUpdates() {
        val tickDifference = Main.ticks - startingTick
        if (tickDifference <= 40 && tickDifference != 0L) {
            if (tickDifference % 10 == 0L) {
                animation.nextFrame()
            }

            if (tickDifference == 10L) {
                depthOffset = 0.1
            }
        }
    }
}