package entities.pokemon.status.nonVt

import entities.pokemon.Pokemon

import entities.pokemon.Interaction.TYPE_ICE

/**
 * Created by dwbrite on 4/27/16.
 */
class nvtsFreeze(self: Pokemon) : NonVolatileStatus() {

    init {
        this.self = self
        this.status = NonVolatileStatus.STATUS_FROZEN
    }

    override fun beginTurnUpdate() {
        if ((Math.random() * 4).toInt() == 0) {
            this.remove()
        }

        //play animation
        /* ... */

        //TODO: Skip turn
    }

    override fun init() {
        //TODO: sunny battle
        /* TODO: what even is this?
		if (self.getType1() == TYPE_ICE || self.getType2() == TYPE_ICE) {
			self.removeNonVolatileStatus();
		}*/
    }

    override fun remove() {
        self!!.removeNonVolatileStatus()
    }

    override fun outOfBattleUpdate() {

    }
}
