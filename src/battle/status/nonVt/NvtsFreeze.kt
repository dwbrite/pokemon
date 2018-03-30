package battle.status.nonVt

import entities.pokemon.Pokemon


class NvtsFreeze(self: Pokemon) : NonVolatileStatus() {

    init {
        this.self = self
        this.status = STATUS_FROZEN
    }

    override fun beginTurnUpdate() {
        if ((Math.random() * 4).toInt() == 0) {
            this.remove()
        }

        //play animation
        /* ... */

        //TODO("Skip turn")
    }

    override fun init() {
        //TODO("sunny battle")
        /*
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
