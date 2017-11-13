package entities.pokemon

import handlers.Resources

object Species {
    fun getData(pkmn_id: Int, `val`: Column): String {
        return Resources.CSV["Pokemon"]!![pkmn_id][`val`.ordinal]
    }

    fun getEvolutionCondition(pkmn_id: Int, condition_id: Int): String {
        return Resources.CSV["Evolutions"]!![pkmn_id][4 + condition_id * 2]
    }

    fun getEvolutionSpecies(pkmn_id: Int, condition_id: Int): String {
        return Resources.CSV["Evolutions"]!![pkmn_id][3 + condition_id * 2]
    }

    fun getIdFromName(name: String): Int {
        for (i in 1 until Resources.CSV["Pokemon"]!!.size) {
            if (Resources.CSV["Pokemon"]!![i][0] == name) {
                return i
            }
        }

        return 0
    }

    enum class Column {
        POKEMON_NAME, //0
        NATIONAL_ID, //1
        KANTO_ID, //2
        JOHTO_ID, //3
        HOENN_ID, //4
        TYPE_1, //5
        TYPE_2, //6
        SPECIES_NAME, //7
        HEIGHT, //8
        WEIGHT, //9
        ABILITY_1, //10
        ABILITY_2, //11
        ABILITY_HIDDEN, //12
        EV_HP, //13
        EV_ATK, //14
        EV_DEF, //15
        EV_SPATK, //16
        EV_SPDEF, //17
        EV_SPEED, //18
        CATCH_RATE, //19
        FRIENDLINESS, //20
        EXPERIENCE_DESIGNATED, //21
        GROWTH_RATE, //22
        EGG_GROUP_1, //23
        EGG_GROUP_2, //24
        GENDER, //25
        EGG_CYCLES, //26
        IS_BABY, //27
        STAT_HP, //28
        STAT_ATK, //29
        STAT_DEF, //30
        STAT_SPATK, //31
        STAT_SPDEF, //32
        STAT_SPEED, //33
        STAT_TOTAL, //34
        BODY_TYPE, //35
        COLOR, //36
        DESCRIPTION, //37
        FEMALE_SPRITE;


        companion object {
            //38

            fun getStatCol(speciesStat: Pokemon.SpeciesStat): Column {
                return values()[STAT_HP.ordinal + speciesStat.ordinal]
            }
        }
    }
}
