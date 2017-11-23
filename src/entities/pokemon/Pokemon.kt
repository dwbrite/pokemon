package entities.pokemon

import entities.characters.Player
import battle.status.nonVt.NonVolatileStatus
import battle.status.volatileStatus.VolatileStatus
import battle.status.vtBattle.VolatileBattleStatus
import org.apache.commons.csv.CSVRecord

import java.util.ArrayList

class Pokemon(species: Int, IVs: IntArray, protected var isShiny: Boolean, protected var pokerusInfection: Boolean, level: Int, protected var abilitySlot: Int, protected var individualGender: Gender, protected var individualNature: Nature) {
    // Species
    var species: Int = 0
        protected set

    // Contest stat
    protected var contestValue = IntArray(5)
    protected var currentHeldItem: Int = 0

    // Battle stats
    protected var currentValue = IntArray(6)

    // EVs
    var eVs = IntArray(6)
        protected set

    // ?? Individual stats
    protected var exp: Int = 0
    protected var individualForm: Int = 0
    protected var individualFriendship: Int = 0
    protected var individualHeight: Int = 0
    protected var individualWeight: Int = 0
    protected var level: Int = 0
    protected var individualMoves = IntArray(4)
    var nickname: String? = null
    protected var individualPokeball: Int = 0

    // IVs
    var iVs = IntArray(6)
        protected set
    protected var isEgg: Boolean = false

    // Markers
    protected var marker = HashMap<Marker, Boolean>()

    // First contact
    protected var contactLevel: Int = 0
    protected var contactLocation: String? = null
    protected var contactType: String? = null

    // Origin
    protected var originalTrainerId: Int = 0
    protected var originalTrainerName: String? = null
    protected var originalTrainerSecretId: Int = 0

    var countryOfOrigin: String? = null
        protected set

    var regionOfOrigin: String? = null
        protected set

    protected var pokerusEndTime: Long = 0
    protected var pokerusStartTime: Long = 0

    // Total stats
    protected var totalValue = IntArray(6)

    var evasion = 0
    protected var statStage = IntArray(6)
    var isConscious = true

    protected var statusCondition: NonVolatileStatus? = null
    protected var volatileCondition: Array<VolatileStatus>? = null
    protected var battleCondition: Array<VolatileBattleStatus>? = null

    lateinit var ability: String

    var spriteUrl: String
        protected set

    /**
     * Returns the amount of XP left until the next level up
     *
     * @return the amount of XP left until the next level up
     */
    //TODO: fix the heck out of this crap. ??
    val levelUpExp: Int
        get() {
            val n = this.level + 1
            var exp = 0
            when (Species.getData(species, Species.Column.GROWTH_RATE)) {
                "ER" -> if (n <= 50) {
                    exp = (n xor 3 * (100 - n)) / 50
                } else if (n <= 68) {
                    exp = (n xor 3 * (150 - n)) / 100
                } else if (n <= 98) {
                    exp = (n xor 3 * ((1911 - 10 * n) / 3)) / 500
                } else {
                    exp = (n xor 3 * (160 - n)) / 100
                }

                "FA" -> exp = (4 * n xor 3) / 5
                "MF" -> exp = n xor 3
                "MS" -> exp = (1.2 * (n xor 3)).toInt() - (15 * n xor 2) + 100 * n - 140
                "SL" -> exp = (5 * n xor 3) / 4
                "FL" -> if (n <= 15) {
                    exp = n xor 3 * ((n + 1) / 3 + 24 / 50)
                } else if (n <= 36) {
                    exp = n xor 3 * ((n + 14) / 50)
                } else {
                    exp = n xor 3 * ((n / 2 + 32) / 50)
                }
            }

            return exp
        }

    val name: String
        get() = nickname?:Species.getData(species, Species.Column.POKEMON_NAME)

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

    fun catchPokemon(p: Player) {
        //TODO: Catch pokemon, assign pokemon to player (or player's box)
    }

    private fun updateAbility() {
        val tempc: Species.Column
        when (abilitySlot) {
            0 -> tempc = Species.Column.ABILITY_1
            1 -> tempc = Species.Column.ABILITY_2
            2 -> tempc = Species.Column.ABILITY_HIDDEN
            else -> tempc = Species.Column.ABILITY_1
        }
        ability = Species.getData(species, tempc)

    }

    fun checkEvolve() {
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

    fun checkLevelUp() {
        if (this.exp >= this.levelUpExp) {
            this.grantLevelUp()
        }
    }

    fun grantExp(exp: Int) {
        this.exp += exp

        this.checkLevelUp()
    }

    fun grantLevelUp() {
        this.level++
        this.updateStats()
        this.checkEvolve()
        //TODO: Update moves / ask *player* if they want new moves.
        //If the Pokemon does not belong to a PLAYER don't do anything.
        //New comment: Does this ^ even need to be said? Can non-player 'mon even level up?
    }

    fun updateStats() {
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
            //this.totalValue[stat.ordinal()] = (int) (this.totalValue[stat.ordinal()] * this.natureModifier[stat.ordinal()]); // TODO read modifiers from CSV
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
        if (statVal > 6)
            statStage[stat.ordinal] = 6
        else if (statVal < -6)
            statStage[stat.ordinal] = -6
        else { //TODO: battle stats?
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
        sb.append((if (isShiny) "*" else "") + if (nickname != null) " \"" + nickname + "\"" else "")

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
        BRACHIOCEPHALOFORM, // Pokemon consistong of a head and arms.
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
            //TODO: check for time. For now we're going to assume daytime
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
            if (Species.getData(species, Species.Column.GENDER) != "-1") {
                if (rng <= java.lang.Double.valueOf(Species.getData(species, Species.Column.GENDER))) {
                    gender = Gender.MALE
                } else {
                    gender = Gender.FEMALE
                }
            } else {
                gender = Gender.GENDERLESS
            }

            //Nature
            val nature = Nature.values()[(Math.random() * 25).toInt()]

            return Pokemon(species, ivs, shiny, pokerus, level, abilitySlot, gender, nature)
        }
    }
}


