package entities.pokemon

import battle.status.nonVt.NonVolatileStatus
import battle.status.volatileStatus.VolatileStatus
import battle.status.vtBattle.VolatileBattleStatus
import entities.characters.Trainer
import org.apache.commons.csv.CSVRecord
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.indices

class Pokemon(species: Int, IVs: IntArray, private var isShiny: Boolean, private var pokerusInfection: Boolean, level: Int, private var abilitySlot: Int, private var individualGender: Gender, private var individualNature: Nature) {
    // Species
    private var species: Int = 0

    // Contest stat
    private var contestValue = IntArray(5)
    private var currentHeldItem: Int = 0

    // Battle stats
    private var currentValue = IntArray(6)

    // EVs
    private var eVs = IntArray(6)

    // ?? Individual stats
    private var exp: Int = 0
    private var individualForm: Int = 0
    private var individualFriendship: Int = 0
    private var individualHeight: Int = 0
    private var individualWeight: Int = 0
    private var level: Int = 0
    private var individualMoves = IntArray(4)
    private var nickname: String? = null
    private var individualPokeball: Int = 0

    // IVs
    private var iVs = IntArray(6)
    private var isEgg: Boolean = false

    // Markers
    private var marker = HashMap<Marker, Boolean>()

    // First contact
    private var contactLevel: Int = 0
    private var contactLocation: String? = null
    private var contactType: String? = null

    // Origin
    private var originalTrainerId: Int = 0
    private var originalTrainerName: String? = null
    private var originalTrainerSecretId: Int = 0

    var countryOfOrigin: String? = null
        private set

    var regionOfOrigin: String? = null
        private set

    private var pokerusEndTime: Long = 0
    private var pokerusStartTime: Long = 0

    // Total stats
    private var totalValue = IntArray(6)

    private var evasion = 0
    private var statStage = IntArray(6)
    var isConscious = true

    private var statusCondition: NonVolatileStatus? = null
    private var volatileCondition: Array<VolatileStatus>? = null
    private var battleCondition: Array<VolatileBattleStatus>? = null

    lateinit var ability: String

    private var spriteUrl: String

    /**
     * Returns the amount of XP left until the next level up
     *
     * @return the amount of XP left until the next level up
     */
    private val levelUpExp: Int
        get() {
            val n = this.level + 1
            var exp = 0
            when (Species.getData(species, Species.Column.GROWTH_RATE)) {
                "ER" -> exp = when {
                    n <= 50 -> (n xor 3 * (100 - n)) / 50
                    n <= 68 -> (n xor 3 * (150 - n)) / 100
                    n <= 98 -> (n xor 3 * ((1911 - 10 * n) / 3)) / 500
                    else -> (n xor 3 * (160 - n)) / 100
                }

                "FA" -> exp = (4 * n xor 3) / 5
                "MF" -> exp = n xor 3
                "MS" -> exp = (1.2 * (n xor 3)).toInt() - (15 * n xor 2) + 100 * n - 140
                "SL" -> exp = (5 * n xor 3) / 4
                "FL" -> exp = when {
                    n <= 15 -> n xor 3 * ((n + 1) / 3 + 24 / 50)
                    n <= 36 -> n xor 3 * ((n + 14) / 50)
                    else -> n xor 3 * ((n / 2 + 32) / 50)
                }
            }

            return exp
        }

    val name: String
        get() = nickname ?: Species.getData(species, Species.Column.POKEMON_NAME)

    init {
        this.species = species
        this.iVs = IVs
        this.level = level

        val sb = StringBuilder()
        if (isShiny)
            sb.append("shiny/")
        if (individualGender == Gender.FEMALE)
            if (java.lang.Boolean.valueOf(Species.getData(species, Species.Column.FEMALE_SPRITE))!!)
                sb.append("female/")
        sb.append(species)
        spriteUrl = sb.toString()

        updateAbility()
        updateStats()
    }

    fun catch(player: Trainer) {
        //TODO("Catch pokemon, assign pokemon to player (or player's box)")
    }

    private fun updateAbility() {
        val tempc: Species.Column = when (abilitySlot) {
            0 -> Species.Column.ABILITY_1
            1 -> Species.Column.ABILITY_2
            2 -> Species.Column.ABILITY_HIDDEN
            else -> Species.Column.ABILITY_1
        }
        ability = Species.getData(species, tempc)

    }

    private fun checkEvolve() {
        //Stone, Trade, Level, Friendship
        val str = Species.getEvolutionCondition(species, 1)
        val type = str.substring(0, str.indexOf(':'))
        val `val` = str.substring(str.indexOf(':'))
        when (type) {
            "LEVEL:" -> if (this.level >= Integer.valueOf(`val`)) {
                this.evolveInto(Integer.valueOf(Species.getEvolutionSpecies(species, 1))!!)
            }
        }
    }

    private fun evolveInto(valueOf: Int) {
        this.species = valueOf
        updateStats()
    }

    fun rareCandyLevelUp() {
        this.exp = levelUpExp
        checkLevelUp()
    }

    private fun checkLevelUp() {
        if (this.exp >= this.levelUpExp) {
            this.grantLevelUp()
        }
    }

    fun grantExp(exp: Int) {
        this.exp += exp

        this.checkLevelUp()
    }

    private fun grantLevelUp() {
        this.level++
        this.updateStats()
        this.checkEvolve()
        //TODO("Update moves / ask *player* if they want new moves.")
        //If the Pokemon does not belong to a PLAYER don't do anything.
        //New comment: Does this ^ even need to be said? Can non-player 'mon even level up?
    }

    private fun updateStats() {
        val oldValues = this.totalValue
        val gainedValues = IntArray(6)

        this.totalValue[SpeciesStat.HP.ordinal] = (this.iVs[SpeciesStat.HP.ordinal] + Integer.valueOf(
                Species.getData(
                        species, Species.Column.STAT_HP
                )
        )!! + (Math.sqrt(this.eVs[SpeciesStat.HP.ordinal].toDouble()) / 8).toInt() + 50) * this.level / 50 + 10
        gainedValues[SpeciesStat.HP.ordinal] = this.totalValue[SpeciesStat.HP.ordinal] - oldValues[SpeciesStat.HP.ordinal]
        this.currentValue[SpeciesStat.HP.ordinal] += gainedValues[SpeciesStat.HP.ordinal]

        for (stat in SpeciesStat.values()) {
            this.totalValue[stat.ordinal] = (this.iVs[stat.ordinal] + Integer.valueOf(Species.getData(species, Species.Column.getStatCol(stat)))!! + (Math.sqrt(this.eVs[stat.ordinal].toDouble()) / 8).toInt()) * this.level / 50 + 10
            //this.totalValue[stat.ordinal()] = (int) (this.totalValue[stat.ordinal()] * this.natureModifier[stat.ordinal()]); // TODO("read modifiers from CSV")
            gainedValues[stat.ordinal] = this.totalValue[stat.ordinal] - oldValues[stat.ordinal]
            this.currentValue[stat.ordinal] += gainedValues[stat.ordinal]
        }

        updateAbility()
    }

    fun getBaseStat(speciesStat: SpeciesStat): Int {
        return totalValue[speciesStat.ordinal]
    }

    fun modifyBattleStatStage(stat: BattleStat, steps: Int) {
        statStage[stat.ordinal] += steps
        val statVal = statStage[stat.ordinal]
        val numerator: Float
        val denominator: Float
        var multiplier = 1f
        when {
            statVal > 6 -> statStage[stat.ordinal] = 6
            statVal < -6 -> statStage[stat.ordinal] = -6
            else -> //TODO("battle stats?")
                when (stat) {
                    Pokemon.BattleStat.ATK, Pokemon.BattleStat.DEF, Pokemon.BattleStat.SPATK, Pokemon.BattleStat.SPDEF, Pokemon.BattleStat.SPEED -> {
                        numerator = if (statVal > 0) 2f + statVal else 2f
                        denominator = if (statVal < 0) 2f + statVal else 2f
                        multiplier = numerator / denominator
                        currentValue[stat.ordinal] = (totalValue[stat.ordinal] * multiplier).toInt()
                    }
                    Pokemon.BattleStat.EVADE -> {
                        numerator = if (statVal < 0) 3f + statVal else 3f
                        denominator = if (statVal > 0) 3f + statVal else 3f
                        multiplier = numerator / denominator
                        evasion = (100 * multiplier).toInt()
                    }
                    else -> {
                    }
                }
        }
    }

    fun getBattleStat(stat: SpeciesStat): Int {
        return currentValue[stat.ordinal]
    }

    fun modifyBattleStat(stat: SpeciesStat, change: Int) {
        currentValue[stat.ordinal] = currentValue[stat.ordinal] + change
    }

    fun setHP(hp: Int) {
        if (hp <= 0) {
            isConscious = false
            this.currentValue[SpeciesStat.HP.ordinal] = 0
        } else
            currentValue[SpeciesStat.HP.ordinal] = hp
    }

    fun removeNonVolatileStatus() {
        statusCondition = null
    }

    fun setNonVolatileStatus(condition: NonVolatileStatus) {
        statusCondition = condition
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(Species.getData(species, Species.Column.POKEMON_NAME))
        sb.append((if (isShiny) "*" else "") + if (nickname != null) " \"$nickname\"" else "")

        sb.append(" lv $level | ")

        sb.append(iVs[0])
        for (i in 1..5)
            sb.append("/" + iVs[i])
        sb.append(" | $ability | ")
        when (individualGender) {
            Pokemon.Gender.FEMALE -> sb.append("Female")
            Pokemon.Gender.MALE -> sb.append("Male")
            Pokemon.Gender.GENDERLESS -> sb.append("Genderless")
        }

        return sb.toString()
    }

    enum class Gender {
        MALE,
        FEMALE,
        GENDERLESS
    }

    enum class Contest {
        COOL,
        BEAUTY,
        CUTE,
        CLEVER,
        TOUGH
    }

    enum class Nature {
        ADAMANT,
        BASHFUL,
        BOLD,
        BRAVE,
        CALM,
        CAREFUL,
        DOCILE,
        GENTLE,
        HARDY,
        HASTY,
        IMPISH,
        JOLLY,
        LAX,
        LONELY,
        MILD,
        MODEST,
        NAIVE,
        NAUGHTY,
        QUIET,
        QUIRKY,
        RASH,
        RELAXED,
        SASSY,
        SERIOUS,
        TIMID
    }

    enum class BattleStat {
        HP,
        ATK,
        DEF,
        SPATK,
        SPDEF,
        SPEED,
        EVADE
    }

    enum class SpeciesStat {
        HP,
        ATK,
        DEF,
        SPATK,
        SPDEF,
        SPEED
    }

    enum class Body {
        CEPHALOFORM, // Pokemon consisting of only a head.
        SERPENTINE, // Pokemon with a serpentine body.
        PINNIPED, // Pokemon with fins.
        BRACHIOCEPHALOFORM, // Pokemon consisting of a head and arms.
        SOMATOID, // Pokemon consisting of a head and base.
        ANDROCAUDAL, // Pokemon with a bipedal, tailed form.
        ASOMATICCEPHALOPOD, // Pokemon consisting of a head and legs.
        QUADRUPED, // Pokemon with a quadruped body.
        DIPLOPTERYGOTUS, // Pokemon with a single pair of wings
        CEPHALOPOD, // Pokemon with tentacles or a multiped body.
        ZOOID, // Pokemon consisting of multiple bodies.
        ANDROFORM, // Pokemon with a bipedal, tailless form.
        POLYPTERYGOTUS, // Pokemon with two or more pairs of wings.
        INSECTOID    // Pokemon with an insectoid body.
    }

    enum class Marker {
        HEART,
        TRIANGLE,
        SQUARE,
        STAR,
        CIRCLE,
        DIAMOND
    }

    companion object {

        fun generateWildPokemon(ecosystem: Int, wildlifeRecord: List<CSVRecord>): Pokemon {
            val speciesList = ArrayList<String>()
            val numWildlife = wildlifeRecord.size
            //TODO("check for time. For now we're going to assume it's daytime")
            val chances = DoubleArray(numWildlife - 3)

            //Species
            val species: Int
            for (i in 3 until numWildlife) {
                speciesList.add(wildlifeRecord[i].get(ecosystem * 7))
                chances[i - 3] = java.lang.Double.valueOf(wildlifeRecord[i].get(4 + ecosystem * 7))
            }
            var rng = Math.random()
            var lastTotal = 0.0
            var chosenMon = 0
            for (i in chances.indices) {
                if (rng >= lastTotal && rng < lastTotal + chances[i]) {
                    chosenMon = i
                    break
                } else {
                    lastTotal += chances[i]
                }
            }
            species = Species.getIdFromName(speciesList[chosenMon])

            //Level
            val level: Int
            val low = Integer.valueOf(wildlifeRecord[chosenMon + 3].get(1 + ecosystem * 7))!!
            val high = Integer.valueOf(wildlifeRecord[chosenMon + 3].get(2 + ecosystem * 7))!!
            val difference = high - low
            level = (Math.random() * difference + low).toInt()

            //IVs
            val ivs = IntArray(6)
            for (i in 0..5) {
                ivs[i] = (Math.random() * 32).toInt()
            }

            //Ability
            var abilitySlot = 0
            if (!Species.getData(species, Species.Column.ABILITY_2).isEmpty()) {
                abilitySlot = (Math.random() * 2).toInt()
            }

            //Shiny
            rng = Math.random()
            var rng2 = Math.random()
            val shiny = rng2 >= rng && rng2 < rng + 0.00012207031

            //Pokerus
            rng = Math.random()
            rng2 = Math.random()
            val pokerus = rng2 >= rng && rng2 < rng + 0.00004577706

            //Gender
            rng = Math.random()
            val gender: Gender
            gender = if (Species.getData(species, Species.Column.GENDER) != "-1") {
                if (rng <= java.lang.Double.valueOf(Species.getData(species, Species.Column.GENDER))) {
                    Gender.MALE
                } else {
                    Gender.FEMALE
                }
            } else {
                Gender.GENDERLESS
            }

            //Nature
            val nature = Nature.values()[(Math.random() * 25).toInt()]

            return Pokemon(species, ivs, shiny, pokerus, level, abilitySlot, gender, nature)
        }
    }
}


