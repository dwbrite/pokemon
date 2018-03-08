package entities

import entities.particles.Particle
import handlers.Camera
import handlers.controls.Controls
import handlers.controls.Controls.InputDir.*
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager
import region.area.Cardinality

object EntityManager {
    private val entityMap = EntityMap()

    fun add(particle: Particle) {
        entityMap.add(particle.toString(), particle)
    }

    fun add(name: String, entity: AbstractEntity) {
        entityMap.add(name, entity)
    }

    fun remove(name: String) {
        entityMap.remove(name)
    }

    fun clear() {
        entityMap.clear()
    }

    fun initAdd(name: String, entity: AbstractEntity) {
        add(name, entity)
        entityMap.update()
    }

    fun update(gc: GameContainer) {
        entityMap.entityList
                .filter { Camera.isEntityInBounds(entityMap[it]!!) }
                .forEach { entityMap[it]!!.setCollision() }

        entityMap.entityList
                .filter { entityMap[it] is Particle || Camera.isEntityInBounds(entityMap[it]!!) }
                .forEach { entityMap[it]!!.update(gc) }

        entityMap.update()
    }

    fun render(gc: GameContainer, g: Graphics) {
        for (key in entityMap.entityList) {
            if (Camera.isEntityInBounds(entityMap[key]!!)) {
                entityMap[key]!!.render(gc, g)
            }
        }
    }

    fun getEntity(name: String) = entityMap[name]

    fun areaSwitch(direction: Controls.InputDir) {
        val yOffset = when (direction) {
            UP -> RegionManager.currentArea.height * 16
            DOWN -> -RegionManager.getNeighbor(Cardinality.NORTH).height * 16
            else -> 0
        }

        val xOffset = when (direction) {
            LEFT -> RegionManager.getNeighbor(Cardinality.WEST).height * 16
            RIGHT -> -RegionManager.currentArea.height * 16
            else -> 0
        }

        for (key in entityMap.entityList) {
            val temp = entityMap[key]!!
            temp.setPosition(temp.getX() + xOffset, temp.getY() + yOffset)
            temp.updateDepth()
        }
        entityMap.update()
    }
}