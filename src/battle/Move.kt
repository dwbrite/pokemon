package battle

import entities.pokemon.EffectCategory
import util.Resources
import kotlin.reflect.full.functions


class Move(private var id: Int) {
    enum class Column {
        GAME_ID,
        INTERNAL_ID,
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

    fun execute() {
        EffectCategory::class.functions.find {
            it.name == Resources.CSV["Moves"]!![id][Column.PRIMARY_FUNCTION.ordinal].trim()
        }?.call(EffectCategory)
    }

    fun selectTarget() {
        val target: Target = Target.valueOf(Resources.CSV["Moves"]!![id][Column.TARGET.ordinal].trim())
        println(target)
    }
}