package battle.status.nonVt

import entities.pokemon.Pokemon

/**
 * Created by dwbrite on 4/27/16.
 */
class nvtsParalyze(self: Pokemon) : NonVolatileStatus() {

    internal var spdModifier: Int = 0

    init {
        this.self = self
        this.status = STATUS_PARALYZED
    }

    override fun beginTurnUpdate() {
        //TODO: Paralyze
        if (Math.random().toInt() * 4 == 0) {
            //skip turn
            //animate
        } else {
            //was not paralyzed!
        }
    }

    override fun init() {
        /* TODO: ...
        if (self.getType1() === TYPE_ELECTRIC || self.getType2() === TYPE_ELECTRIC) {
            self.removeNonVolatileStatus()
        }
        */
        if (self!!.ability == "Quick Feet") {
            spdModifier = (self!!.getBattleStat(Pokemon.SpeciesStat.SPEED) * 0.5f).toInt()
        } else {
            spdModifier = (self!!.getBattleStat(Pokemon.SpeciesStat.SPEED) * -0.25f).toInt()
        }
        self!!.modifyBattleStat(Pokemon.SpeciesStat.SPEED, spdModifier)
    }

    override fun outOfBattleUpdate() {
        //nothing
    }

    override fun remove() {
        self!!.modifyBattleStat(Pokemon.SpeciesStat.SPEED, spdModifier)
        self!!.removeNonVolatileStatus()
    }
}
