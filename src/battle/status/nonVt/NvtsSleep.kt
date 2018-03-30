package battle.status.nonVt

import entities.pokemon.Pokemon

class NvtsSleep : NonVolatileStatus {

    private var numSleepTurns: Int = 0

    private var turnsSlept: Int = 0

    constructor(self: Pokemon) {
        this.self = self
        this.status = STATUS_ASLEEP
    }

    constructor(self: Pokemon, numSleepTurns: Int) {
        this.self = self
        this.status = STATUS_ASLEEP
        this.numSleepTurns = numSleepTurns
    }

    override fun beginTurnUpdate() {
        //TODO("Wake up or sleep")
        if (turnsSlept == numSleepTurns) {
            //Wake up
            remove()
        } else {
            //still sleeping
        }
    }

    override fun init() {
        if (numSleepTurns == 0) {
            numSleepTurns = (Math.random() * 6 + 1).toInt()
        }
        turnsSlept = 0
    }

    override fun outOfBattleUpdate() {
        //nothing
    }

    override fun remove() {
        self!!.removeNonVolatileStatus()
    }
}
