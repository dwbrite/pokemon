package battle.status.nonVt

import entities.pokemon.Pokemon

class NvtsBadPoison(self: Pokemon) : NonVolatileStatus() {

    //TODO("Decide if BadPoison belongs in Poison or not.")
    private var turn = 0

    init {
        this.self = self
        this.status = STATUS_BADLYPOISONED
    }

    override fun endTurnUpdate() {
        turn++
        //TODO("Update some text for the effect")
        //pkmn is badly poisoned!
        if (self!!.ability == "Poison Heal") {
            self!!.setHP(self!!.getBattleStat(Pokemon.SpeciesStat.HP) + self!!.getBaseStat(Pokemon.SpeciesStat.HP) / 8)
            //pkmn healed!
        } else {
            self!!.setHP((self!!.getBattleStat(Pokemon.SpeciesStat.HP) - self!!.getBaseStat(Pokemon.SpeciesStat.HP) * (turn / 16f)).toInt())
            //pkmn took damage!
        }
    }

    override fun init() {
        /* TODO("fix type identification. I believe the fix should be "self".species.getType or getTypes()? Maybe Types returns a tuple.")
		if ((self.getType1() == TYPE_POISON || self.getType2() == TYPE_POISON ||
				     self.getType1() == TYPE_STEEL || self.getType2() == TYPE_STEEL
				     || self.getAbility().equals("Immunity"))) {
			//pkmn is immune to poison because of (reason)
			self.removeNonVolatileStatus();
		} else {
			//pkmn was poisoned!
		}
		*/
    }

    override fun outOfBattleUpdate() {}

    override fun remove() {
        //TODO("Refactor things like this to be part of the (not-yet-existent) battle server.")
        self!!.removeNonVolatileStatus()
    }
}
