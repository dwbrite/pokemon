package entities.pokemon

import util.Resources

/**
 * Created by dwbrite on 4/13/16.
 */
object Type {

    internal enum class Types {
        NORMAL,
        FIRE,
        FIGHTING,
        WATER,
        FLYING,
        GRASS,
        POISON,
        ELECTRIC,
        GROUND,
        PSYCHIC,
        ROCK,
        ICE,
        BUG,
        DRAGON,
        GHOST,
        DARK,
        STEEL,
        FAIRY
    }

    fun init() {}

    fun getTypeDamageModifier(offense: Int, defense: Int): Float {
        return Resources.CSV["Types"]!![1 + offense][1 + defense].toFloat()
    }

    fun getTypeDamageModifier(offense: Int, defense: Int, defense2: Int): Float {
        return (Resources.CSV["Types"]!![1 + offense][1 + defense].toFloat()
                * Resources.CSV["Types"]!![1 + offense][1 + defense2].toFloat())
    }
}
