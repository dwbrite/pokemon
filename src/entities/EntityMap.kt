package entities

import java.util.*

class EntityMap {
    private val entities = HashMap<String, AbstractEntity>()
    internal val entityList = ArrayList<String>()
    private val entitiesToRemove = ArrayList<String>()
    private val entitiesToAdd = HashMap<String, AbstractEntity>()

    operator fun get(name: String) = entities[name]

    fun add(name: String, entity: AbstractEntity) {
        entitiesToAdd[name] = entity
    }

    fun remove(name: String) {
        entitiesToRemove.add(name)
    }

    fun clear() {
        entitiesToRemove.clear()
        entitiesToRemove.addAll(entityList)
    }

    private fun doAdd() {
        for (e in entitiesToAdd.entries) {
            entities[e.key] = e.value
            entityList.add(e.key)
        }
        entitiesToAdd.clear()
    }

    private fun doRemove() {
        for (e in entitiesToRemove) {
            entities.remove(e)
            entityList.remove(e)
        }
        entitiesToRemove.clear()
    }

    fun update() {
        doRemove()
        doAdd()
        sort()
    }

    private fun sort() {
        entityList.sortWith(Comparator { p1, p2 -> compareValues(entities[p1]!!.depth, entities[p2]!!.depth) })
    }
}
