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

object EntityManager {
    private val entities = HashMap<String, AbstractEntity>()
    private val entityList = ArrayList<String>()
    private val entitiesToRemove = ArrayList<String>()

    fun add(particle: Particle) {
        entities.put(particle.toString(), particle)
        entityList.add(particle.toString())
    }

    fun add(name: String, entity: AbstractEntity) {
        entities.put(name, entity)
        entityList.add(name)
    }

    fun remove(name: String) {
        entitiesToRemove.add(name)
        /*entities.remove(name)
        entityList.remove(name)*/
    }

    fun clear() {
        entities.clear()
        entityList.clear()
        entitiesToRemove.clear()
    }

    private fun doRemove() {
        for (e in entitiesToRemove) {
            entities.remove(e)
            entityList.remove(e)
        }
        entitiesToRemove.clear()
    }

    private fun updateList() {
        Collections.sort(entityList, { p1, p2 ->
            compareValues(entities[p1]!!.getDepth(), entities[p2]!!.getDepth())
        })
    }

    fun update(gc: GameContainer) {
        for (i in entities.keys) {
            if (Camera.isEntityInBounds(entities[i])) {
                entities[i]!!.setCollision()
            }
        }
        for (i in entities.keys) {
            if (entities[i] is Particle
                    || Camera.isEntityInBounds(
                    entities[i])) {
                //TODO: update i?
                entities[i]!!.update(gc)
            }
        }

        doRemove()
        updateList()
    }

    fun render(gc: GameContainer, g: Graphics) {
        for (i in entityList.indices) {
            if (Camera.isEntityInBounds(entities[entityList[i]])) {
                entities[entityList[i]]!!.render(gc, g)
            }
        }
    }

    fun getEntity(name: String): AbstractEntity? {
        return if (entities.containsKey(name)) {
            entities[name]
        } else {
            null
        }
    }

    fun areaSwitch(entity: GameCharacter) {
        /*
        var xOffset = 0
        var yOffset = 0

        when (entity.direction) {
            Controls.InputDir.UP -> yOffset = RegionManager.currentArea.height * 16
            Controls.InputDir.DOWN -> yOffset = -RegionManager.northArea.height * 16
            Controls.InputDir.LEFT -> xOffset = RegionManager.westArea.height * 16
            Controls.InputDir.RIGHT -> xOffset = -RegionManager.currentArea.height * 16
        }

        /*TODO: clear all
         * move entity to next region
         *
         */
        for (i in entityList.indices) {
            val temp = entities[entityList[i]]!!
            temp.setPosition(temp.getX() + xOffset, temp.getY() + yOffset)
            //EntityManager.add(entityList[i], entities[entityList[i]]!!)
        }
        */
    }

    /*
    fun areaSwitch(direction: handlers.Controls.InputDir) {
        var xOffset = 0
        var yOffset = 0

        when (direction) {
            UP -> yOffset = RegionManager.currentArea.height * 16
            DOWN -> yOffset = -RegionManager.northArea.height * 16
            LEFT -> xOffset = RegionManager.westArea.height * 16
            RIGHT -> xOffset = -RegionManager.currentArea.height * 16
        }

        for (i in particleList.indices) {
            val temp = particles[particleList[i]]!!
            temp.setPosition(temp.getX() + xOffset, temp.getY() + yOffset)
            EntityManager.add(particleList[i], particles[particleList[i]]!!)
        }
    }*/
}
