package entities

import entities.characters.GameCharacter
import entities.particles.Particle
import handlers.Camera
import handlers.Controls
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap

class EntityMap {
    private val entities = HashMap<String, AbstractEntity>()
    internal val entityList = ArrayList<String>()
    private val entitiesToRemove = ArrayList<String>()
    private val entitiesToAdd = HashMap<String, AbstractEntity>()

    operator fun get(name: String) = entities[name]

    fun add(name: String, entity: AbstractEntity) {
        entitiesToAdd.put(name, entity)
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
            entities.put(e.key, e.value)
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

    fun sort() {
        Collections.sort(entityList, { p1, p2 -> compareValues(entities[p1]!!.getDepth(), entities[p2]!!.getDepth()) })
    }
}
