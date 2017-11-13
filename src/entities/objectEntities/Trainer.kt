package entities.objectEntities

import entities.pokemon.Pokemon
import org.newdawn.slick.Image

/**
 * Created by dwbrite on 4/28/16.
 */
open class Trainer(x: Int, y: Int, spritesheet: Image) : GameCharacter(x, y, spritesheet) {

    internal var party = arrayOfNulls<Pokemon?>(6)

    open val firstConsciousMonster: Pokemon?
        get() { //TODO: check if this works.
            return party.indices
                    .firstOrNull { party[it] != null && party[it]!!.isConscious }
                    ?.let { party[it]!! }
        }

    fun setPartyMember(position: Int, member: Pokemon?) {
        party[position] = member
    }

    fun getPartyMember(position: Int): Pokemon? {
        //TODO: Error checking
        return party[position]
    }

    fun setParty(party: Array<Pokemon>) {
        if (party.size > 6)
            println("FUCK YOU.") //TODO: this
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
