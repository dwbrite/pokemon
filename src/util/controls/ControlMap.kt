package util.controls

import util.Direction
import util.InputKey

class ControlMap {
    private var inputDir = HashMap<Enum<Direction>, Boolean>()
    private var inputKey = HashMap<Enum<InputKey>, Boolean>()

    init {
        for (key in Direction.values()) this[key] = false
        for (key in InputKey.values()) this[key] = false
    }

    operator fun get(key: Direction): Boolean = inputDir[key]!!
    operator fun get(key: InputKey): Boolean = inputKey[key]!!
    operator fun set(key: Direction, value: Boolean) {
        if (value) for (dir in Direction.values()) this[dir] = false
        inputDir[key] = value
    }

    operator fun set(key: InputKey, value: Boolean) {
        inputKey[key] = value
    }
}