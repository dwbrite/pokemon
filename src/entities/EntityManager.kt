package entities

import entities.particles.Particle
import util.Camera
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import region.RegionManager
import util.Direction
import util.Direction.*

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

    fun areaSwitch(dir: Direction) {
        val yOffset = when (dir) {
            UP -> RegionManager.currentArea.map.height * 16
            DOWN -> -RegionManager.getNeighbor(UP).map.height * 16
            LEFT -> -RegionManager.getNeighbor(RIGHT).getOffset(dir)
            RIGHT -> -RegionManager.getNeighbor(LEFT).getOffset(dir)
        }

        val xOffset = when (dir) {
            LEFT -> RegionManager.getNeighbor(LEFT).map.width * 16
            RIGHT -> -RegionManager.currentArea.map.width * 16
            UP -> -RegionManager.getNeighbor(DOWN).getOffset(dir)
            DOWN -> -RegionManager.getNeighbor(UP).getOffset(dir)
        }

        for (key in entityMap.entityList) {
            val temp = entityMap[key]!!
            temp.setPosition(temp.x + xOffset, temp.y + yOffset)
            temp.updateDepth()
        }
        entityMap.update()
    }
}