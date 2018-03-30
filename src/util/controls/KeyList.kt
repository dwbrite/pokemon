package util.controls

import java.util.ArrayList

/**
 * Created by dwbrite on 5/6/16.
 */
class KeyList<T> {
    internal var values: ArrayList<T>

    internal var keys: ArrayList<String>

    private val t: T? = null

    init {
        this.values = ArrayList()
        this.keys = ArrayList()
    }

    operator fun get(key: String): T {
        return values[keys.indexOf(key)]
    }

    operator fun get(key: Int): T {
        return values[key]
    }

    operator fun set(key: String, value: T) {
        val index = keys.indexOf(key)
        if (index == -1) {
            values.add(value)
            keys.add(key)
        } else {
            values[index] = value
        }
    }

    fun remove(key: String) {
        values.removeAt(keys.indexOf(key))
    }

    fun remove(obj: T) {
        values.remove(obj)
    }

    fun indexOf(obj: T): Int {
        return values.indexOf(obj)
    }

    fun indexOf(key: String): Int {
        return keys.indexOf(key)
    }

    fun size(): Int {
        return keys.size
    }
}
