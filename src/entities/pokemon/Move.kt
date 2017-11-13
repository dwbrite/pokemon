package entities.pokemon

import handlers.Resources
import kotlin.reflect.full.functions


class Move {
    enum class Column {
        ID,
        OID,
        INTERNAL_NAME,
        NAME,
        GENERATION,
        TYPE,
        CATEGORY,
        CONTEST,
        PP,
        POWER,
        ACCURACY,
        TARGET,
        PRIORITY,
        FLAGS,
        PRIMARY_FUNCTION,
        FLAVOR_TEXT,
        EFFECT_1,
        VAL_1,
        EFFECT_2,
        VAL_2,
        EFFECT_CHANCE,
        EFFECT_TARGET,
        EFFECT_DESCRIPTION,
        MOVE_DESCRIPTION
    }

    enum class Target {
        ANY_ADJ,
        USER,
        ALL_ADJ_FOE,
        ANY,
        ALL,
        ALL_ADJ,
        ANY_ALLY,
        ALL_FOE,
        ALL_ALLY,
        ANY_ADJ_ALLY,
        ANY_ADJ_FOE,
        ANY_FOE,
        PARALLEL
    }

    private var id: Int

    constructor(id: Int) {
        this.id = id
    }

    fun execute() {
        this::class.functions.find {
            it.name == Resources.CSV["Moves"]!![id][Column.PRIMARY_FUNCTION.ordinal]
        }?.call(this)
    }

    fun selectTarget() {
        var target: Target = Target.valueOf(Resources.CSV["Moves"]!![id][Column.TARGET.ordinal])
        println(target)
    }

    fun status() {
        println(":D")
    }

    fun attack() {
        println(":D!")
        //Battle.damage()
        //Battle.raise(Pokemon.STAT, n)
    }

    fun arena() {
        println("D:")
    }
}