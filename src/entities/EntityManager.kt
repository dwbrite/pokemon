package entities

import entities.particles.Particle
import handlers.Camera
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager
import handlers.Controls.InputDir.*

object EntityManager {
    private val entityMap = EntityMap()

    fun add(particle: Particle) { entityMap.add(particle.toString(), particle) }
    fun add(name: String, entity: AbstractEntity) { entityMap.add(name, entity) }
    fun remove(name: String) { entityMap.remove(name) }
    fun clear() { entityMap.clear() }

    fun initAdd(name: String, entity: AbstractEntity) {
        add(name, entity)
        entityMap.update()
    }

    fun update(gc: GameContainer) {
        for (key in entityMap.entityList) {
            if (Camera.isEntityInBounds(entityMap[key]!!)) {
                entityMap[key]!!.setCollision()
            }
        }
        for (key in entityMap.entityList) {
            if (entityMap[key] is Particle || Camera.isEntityInBounds(entityMap[key]!!)) {
                entityMap[key]!!.update(gc)
            }
        }

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

    fun areaSwitch(direction: handlers.Controls.InputDir) {
        var yOffset = when (direction) {
            UP -> RegionManager.currentArea.height * 16
            DOWN -> -RegionManager.northArea.height * 16
            else -> 0
        }

        var xOffset = when (direction) {
            LEFT -> RegionManager.westArea.height * 16
            RIGHT -> -RegionManager.currentArea.height * 16
            else -> 0
        }

        for (key in entityMap.entityList) {
            var temp = entityMap[key]!!
            temp.setPosition(temp.getX() + xOffset, temp.getY() + yOffset)
        }

        entityMap.update()
    }
}
