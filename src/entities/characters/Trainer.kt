package entities.characters

import entities.pokemon.Pokemon
import org.newdawn.slick.Image

open class Trainer(x: Int, y: Int, spritesheet: Image) : GameCharacter(x, y, spritesheet) {

    private var party = arrayOfNulls<Pokemon?>(6)

    open val firstConsciousMonster: Pokemon?
        get() {
            return party.indices
                    .firstOrNull { party[it] != null && party[it]!!.isConscious }
                    ?.let { party[it]!! }
        }

    private fun setPartyMember(position: Int, member: Pokemon?) {
        party[position] = member
    }

    fun getPartyMember(position: Int): Pokemon? {
        //TODO("Error checking")
        return party[position]
    }

    fun setParty(party: Array<Pokemon>) {
        if (party.size > 6)
        //TODO("don't let the player cheat?")
        else {
            for (i in party.indices) {
                setPartyMember(i, party[i])
            }
            for (i in party.size - 1..5) {
                setPartyMember(i, null)
            }
        }

    }
}
