package battle.status.nonVt

import entities.pokemon.Pokemon

class NvtsBurn(self: Pokemon) : NonVolatileStatus() {

    private var atkModifier: Int = 0

    init {
        this.self = self
        this.status = STATUS_BURNED
    }

    override fun endTurnUpdate() {
        //play animation
        /* ... */
        //deal damage
        if (self!!.ability == "Heatproof")
            self!!.setHP(self!!.getBattleStat(Pokemon.SpeciesStat.HP) - self!!.getBaseStat(Pokemon.SpeciesStat.HP) / 16)
        else
            self!!.setHP(self!!.getBattleStat(Pokemon.SpeciesStat.HP) - self!!.getBaseStat(Pokemon.SpeciesStat.HP) / 8)
    }

    override fun init() {
        /*TODO("Fix type getting")
		if ((self.getType1() == TYPE_FIRE || self.getType2() == TYPE_FIRE
				     || self.getAbility().equals("Water Veil"))) {
			self.removeNonVolatileStatus();
			return;
		}
		*/
        //Modify attack
        if (self!!.ability == "Guts") {
            atkModifier = (self!!.getBattleStat(Pokemon.SpeciesStat.ATK) * 0.5f).toInt()
            self!!.modifyBattleStat(Pokemon.SpeciesStat.ATK, atkModifier)
        } else {
            atkModifier = (self!!.getBattleStat(Pokemon.SpeciesStat.ATK) * -0.5f).toInt()
            self!!.modifyBattleStat(Pokemon.SpeciesStat.ATK, atkModifier)
        }
    }

    override fun remove() {
        //Modify attack
        if (self!!.ability == "Guts") {
            self!!.modifyBattleStat(Pokemon.SpeciesStat.ATK, -atkModifier)
        } else {
            self!!.modifyBattleStat(Pokemon.SpeciesStat.ATK, -atkModifier)
        }

        self!!.removeNonVolatileStatus()
    }

    override fun outOfBattleUpdate() {

    }
}
