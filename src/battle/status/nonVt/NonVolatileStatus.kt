package battle.status.nonVt

import entities.pokemon.Pokemon

abstract class NonVolatileStatus {


    protected var self: Pokemon? = null

    protected var status: Int = 0

    open fun beginTurnUpdate() {

    }

    open fun endTurnUpdate() {

    }

    abstract fun init()

    abstract fun outOfBattleUpdate()

    abstract fun remove()

    // TODO("Convert status conditions to enums.")
    companion object {
        var STATUS_NONE = 0
        var STATUS_BURNED = 1
        var STATUS_FROZEN = 2
        var STATUS_PARALYZED = 3
        var STATUS_POISONED = 4
        var STATUS_BADLYPOISONED = 5 //??
        var STATUS_ASLEEP = 6
    }
}
