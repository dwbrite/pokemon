package entities.pokemon.status.nonVt

import entities.pokemon.Pokemon

import entities.pokemon.Interaction.TYPE_POISON
import entities.pokemon.Interaction.TYPE_STEEL

/**
 * Created by dwbrite on 4/27/16.
 */
class nvtsPoison(self: Pokemon) : NonVolatileStatus() {

    internal var steps = 0

    init {
        this.self = self
        this.status = NonVolatileStatus.STATUS_POISONED
    }

    override fun endTurnUpdate() {
        if (self!!.ability == "Poison Heal") {
            self!!.setHP(self!!.getBattleStat(Pokemon.SpeciesStat.HP) + self!!.getBaseStat(Pokemon.SpeciesStat.HP) / 8)
        } else {
            self!!.setHP(self!!.getBattleStat(Pokemon.SpeciesStat.HP) - self!!.getBaseStat(Pokemon.SpeciesStat.HP) / 8)
        }
    }

    override fun init() {
        /* TODO: ...
        if (self.getType1() === TYPE_POISON || self.getType2() === TYPE_POISON ||
                self.getType1() === TYPE_STEEL || self.getType2() === TYPE_STEEL
                || self.ability == "Immunity") {
            self.removeNonVolatileStatus()
        }
        */
    }

    override fun outOfBattleUpdate() {
        steps++
        if (steps == 4) {
            if (self!!.getBattleStat(Pokemon.SpeciesStat.HP) > 1) {
                self!!.setHP(self!!.getBattleStat(Pokemon.SpeciesStat.HP) - 1)
            } else {
                self!!.removeNonVolatileStatus()
            }
        }
    }

    override fun remove() {
        self!!.removeNonVolatileStatus()
    }
}