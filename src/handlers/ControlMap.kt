package handlers

class ControlMap() {
    var inputDir = HashMap<Enum<Controls.InputDir>, Boolean>()
    var inputKey = HashMap<Enum<Controls.InputKey>, Boolean>()

    init {
        for (key in handlers.Controls.InputDir.values()) { this[key] = false }
        for (key in handlers.Controls.InputKey.values()) { this[key] = false }
    }

    operator fun get(key: Controls.InputDir): Boolean = inputDir[key]!!
    operator fun get(key: Controls.InputKey): Boolean = inputKey[key]!!
    operator fun set(key: Controls.InputDir, value: Boolean) { inputDir[key] = value }
    operator fun set(key: Controls.InputKey, value: Boolean) { inputKey[key] = value }
}