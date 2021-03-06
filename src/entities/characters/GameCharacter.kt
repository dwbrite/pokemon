package entities.characters

import entities.AbstractEntity
import entities.Animation
import entities.EntityManager
import entities.particles.DirtJump
import entities.particles.GrassJump
import entities.particles.JumpShadow
import entities.particles.NormalGrassParticle
import gamestate.InGameState
import util.controls.ControlMap
import util.Direction
import util.Direction.*
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import util.CollisionType.*
import region.RegionManager

open class GameCharacter(x: Int, y: Int, spritesheet: Image) : AbstractEntity(x, y) {

    var currentAction = Action.IDLING
    var busy = false
    var direction = DOWN

    // Controls
    var controls = ControlMap()

    // Animations
    private var walk = arrayOfNulls<Animation>(4)
    private var run = arrayOfNulls<Animation>(4)
    private var bike = arrayOfNulls<Animation>(4)
    private var swim = arrayOfNulls<Animation>(4)
    protected var fish = arrayOfNulls<Animation>(4)
    private var current = arrayOfNulls<Animation>(4)
    private var currentAnimation: Animation?

    var frameNum = 0
    private var animationFrames = 0
    private var animations = 0
    private var moveSpeed = 0
    var transportMode = TransportMode.WALK
        set(value) { if(!busy) field = value }

    // Collisions
    private var blocked = false
    var forwardCollisionType = NONE

    init {
        //setMapOffset(0, 0)
        this.spriteSheet = spritesheet

        val standardFrames = intArrayOf(0, 1, 0, 2)
        val walking = standardAnimation(0, 32, 32)
        for (i in 0..3) {
            walk[i] = Animation(standardFrames, *walking[i])
        }

        val running = standardAnimation(3 * 32, 32, 32)
        for (i in 0..3) {
            run[i] = Animation(standardFrames, *running[i])
        }

        val bikeFrames = intArrayOf(0, 1, 1, 0, 2, 2)
        val biking = standardAnimation(2 * 3 * 32, 32, 32)
        for (i in 0..3) {
            bike[i] = Animation(bikeFrames, *biking[i])
        }

        val swimFrames = intArrayOf(0, 1)
        val swimming = standardAnimation(3 * 3 * 32, 32, 32)
        for (i in 0..3) {
            swim[i] = Animation(swimFrames, *swimming[i])
        }

        //TODO("Fishing!")
        //final Image[][] fishing = standardAnimation(0, 32, 32);
        //for(int i = 0; i < 4; i++) {
        //	fish[i] = new Animation(standardFrames, fishing[i]);
        //}

        currentAnimation = walk[DOWN.ordinal]
        currentAnimation?.setFrame(0)
        currentImage = currentAnimation?.currentFrame
    }

    private fun standardAnimation(yOffset: Int, width: Int, height: Int): Array<Array<Image>> {
        //TODO("Is this code quality up-to-par?")
        return arrayOf(
                arrayOf(this.spriteSheet.getSubImage(width, yOffset + height, width, height),
                        this.spriteSheet.getSubImage(0, yOffset + height, width, height),
                        this.spriteSheet.getSubImage(0, yOffset + height, width, height)
                                .getFlippedCopy(true, false)),

                arrayOf(this.spriteSheet.getSubImage(width, yOffset + 2 * height, width, height),
                        this.spriteSheet.getSubImage(0, yOffset + 2 * height, width, height),
                        this.spriteSheet.getSubImage(0, yOffset + 2 * height, width, height)
                                .getFlippedCopy(true, false)),

                arrayOf(this.spriteSheet.getSubImage(width, yOffset, width, height)
                        .getFlippedCopy(true, false),
                        this.spriteSheet.getSubImage(0, yOffset, width, height)
                                .getFlippedCopy(true, false),
                        this.spriteSheet.getSubImage(2 * width, yOffset, width, height)
                                .getFlippedCopy(true, false)),

                arrayOf(this.spriteSheet.getSubImage(width, yOffset, width, height),
                        this.spriteSheet.getSubImage(0, yOffset, width, height),
                        this.spriteSheet.getSubImage(2 * width, yOffset, width, height))
        )
    }

    private fun checkCollisions() {
        forwardCollisionType = when (direction) {
            UP -> InGameState.collisionMap.getCollisionValue((x + xMapOffset) / 16, Math.ceil((y + yMapOffset) / 16.0).toInt() - 1)
            DOWN -> InGameState.collisionMap.getCollisionValue((x + xMapOffset) / 16, Math.floor((y + yMapOffset) / 16.0).toInt() + 1)
            LEFT -> InGameState.collisionMap.getCollisionValue(Math.ceil((x + xMapOffset) / 16.0).toInt() - 1, (y + yMapOffset) / 16)
            RIGHT -> InGameState.collisionMap.getCollisionValue(Math.floor((x + xMapOffset) / 16.0).toInt() + 1, (y + yMapOffset) / 16)
            else -> NONE
        }
        blocked = !(forwardCollisionType == NONE || forwardCollisionType == GRASS || forwardCollisionType == DARK_GRASS)
    }

    private fun checkInput() {
        if (!busy) {
            var controlled = false
            for (dir in Direction.values()) {
                if (controls[dir]) {
                    controlled = true
                    currentAction = if (direction != dir && currentAction == Action.IDLING) {
                        direction = dir
                        Action.TURNING
                    } else {
                        direction = dir
                        checkCollisions()
                        if (controls[DOWN] && forwardCollisionType == DOWN_CLIFF) {
                            Action.JUMPING
                        } else {
                            Action.MOVING
                        }
                    }
                }
            }

            if (controlled) {
                frameNum = 0
            } else {
                currentAction = Action.IDLING
            }
        }
    }

    private fun updateCurrentAnimation() {
        current = when (transportMode) {
            TransportMode.WALK -> walk
            TransportMode.RUN -> run
            TransportMode.BIKE -> bike
            TransportMode.SWIM -> swim
        }
    }

    private fun checkGrassCollision() {
        if (currentAction == Action.MOVING) {
            //step
            if (forwardCollisionType == GRASS) {
                var xOffset = 0
                var yOffset = 0
                if (currentAction == Action.MOVING) {
                    when (direction) {
                        UP -> yOffset = -16
                        DOWN -> yOffset = 16
                        LEFT -> xOffset = -16
                        RIGHT -> xOffset = 16
                    }

                    if (frameNum == 0) {
                        val temp = NormalGrassParticle(x + xOffset, y + yOffset, this)
                        EntityManager.add(temp.toString(), temp)
                    }
                }
            }
        }
    }

    private fun finalizeMovement(moveSpeed: Int) {
        checkGrassCollision()

        when (direction) {
            UP -> {
                currentAnimation = current[UP.ordinal]
                y -= if (blocked) 0 else moveSpeed
            }
            DOWN -> {
                currentAnimation = current[DOWN.ordinal]
                y += if (blocked && currentAction != Action.JUMPING) 0 else moveSpeed
            }
            LEFT -> {
                currentAnimation = current[LEFT.ordinal]
                x -= if (blocked) 0 else moveSpeed
            }
            RIGHT -> {
                currentAnimation = current[RIGHT.ordinal]
                x += if (blocked) 0 else moveSpeed
            }
        }
    }

    private fun finalizeAnimations(animationFrames: Int) {
        if (currentAction == Action.JUMPING) {
            if (frameNum == 0) {
                EntityManager.add(JumpShadow(this))
            } else if (frameNum == 15) {
                var tempForwardCollisionType = NONE
                when (direction) {
                    DOWN -> tempForwardCollisionType = InGameState.collisionMap.getCollisionValue(x / 16, Math.floor(y / 16.0).toInt() + 2)
                    LEFT -> tempForwardCollisionType = InGameState.collisionMap.getCollisionValue(Math.ceil(x / 16.0).toInt() - 2, y / 16)
                    RIGHT -> tempForwardCollisionType = InGameState.collisionMap.getCollisionValue(Math.floor(x / 16.0).toInt() + 2, y / 16)
                    else -> { System.err.println("Cliff jumping should only happen in down, left, or right directions. Collision definitions may be broken.") }
                }
                if (tempForwardCollisionType == GRASS) {
                    var xOffset = 0
                    var yOffset = 0
                    when (direction) {
                        DOWN -> yOffset = 16
                        LEFT -> xOffset = -16
                        RIGHT -> xOffset = 16
                        else -> {
                        }
                    }

                    EntityManager.add(GrassJump(x + xOffset, y + yOffset, this))
                } else {
                    EntityManager.add(DirtJump(x, y, this))
                }
            }

            if (transportMode == TransportMode.BIKE) { //This is effectively +32/6 per frame (rounded down)
                //((frameNum/6f)*(32f/6f)%(32f/6f))==0f
                //(n == 0 || n == 5 || n == 10 || n == 16 || n == 21 || n == 26)
                if ((frameNum / 6f) * (32f / 6f) % (32f / 6f) == 0f) {
                    currentAnimation?.nextFrame()
                }
            } else if (animationFrames > 0 && frameNum % animationFrames == 0) {
                currentAnimation?.nextFrame()
            }
            if (frameNum % 2 == 0 && frameNum <= 13) {
                yMapOffset -= 2
            } else if (frameNum == 14) {
                yMapOffset -= 1
            } else if (frameNum == 18) {
                yMapOffset += 1
            } else if (frameNum % 2 == 0 && frameNum < 32) {
                yMapOffset += 2
            }
        } else if (animationFrames > 0 && frameNum % animationFrames == 0) {
            currentAnimation?.nextFrame()
        }
    }

    private fun identifyAction() {
        when (currentAction) {
            Action.IDLING -> processIdle()
            Action.TURNING -> processTurn()
            Action.MOVING -> processMovement()
            Action.JUMPING -> processCliffJump()
        }
    }

    private fun processCliffJump() {
        busy = true

        animationFrames = 8
        animations = 4
        moveSpeed = 1

        if (transportMode == TransportMode.RUN) {
            transportMode = TransportMode.WALK
        }

        if (frameNum < animations * animationFrames) {
            updateCurrentAnimation()
            finalizeMovement(moveSpeed)
            finalizeAnimations(animationFrames)
            frameNum++
        }

        if (frameNum >= animationFrames * animations) {
            frameNum = 0
            busy = false
        }
    }

    private fun processIdle() {
        updateCurrentAnimation()
        finalizeMovement(0)
        finalizeAnimations(0)
        //TODO("Swim idle animation things")
        //frameNum++;
        //if(frameNum >= 16)
        //{
        //	frameNum = 0;
        //}
    }

    private fun processMovement() {
        busy = true
        animationFrames = 8
        animations = 2
        moveSpeed = 1

        if (transportMode == TransportMode.BIKE) {
            animationFrames = 2
            animations = 3
            moveSpeed = Math.round(2 + (frameNum + 1) * 0.33 % 1).toInt()
        }

        if (transportMode == TransportMode.RUN) {
            animationFrames = 4
            animations = 2
            moveSpeed = 2
            if (blocked) {
                transportMode = TransportMode.WALK
            }
        }

        if (blocked) {
            animationFrames *= 2
            moveSpeed = 0
        }

        if (frameNum < animations * animationFrames) {
            updateCurrentAnimation()
            finalizeMovement(moveSpeed)
            finalizeAnimations(animationFrames)
            frameNum++
        }

        if (blocked) {
            if (frameNum >= animationFrames * animations) {
                frameNum = 0
                busy = false
            } else if (direction == UP && (controls[DOWN] || controls[LEFT] || controls[RIGHT])
                    || direction == DOWN && (controls[UP] || controls[LEFT] || controls[RIGHT])
                    || direction == LEFT && (controls[UP] || controls[DOWN] || controls[RIGHT])
                    || direction == RIGHT && (controls[UP] || controls[DOWN] || controls[LEFT])) {
                frameNum = 0
                busy = false
                currentAnimation?.setFrame(0)
            }
        } else if (frameNum >= animationFrames * animations) {
            frameNum = 0
            busy = false
        }
    }

    override fun render(gc: GameContainer, g: Graphics) {
        currentImage!!.draw((x - 8 + xMapOffset + RegionManager.currentArea.x).toFloat(), (y - 16 + yMapOffset + RegionManager.currentArea.y).toFloat())
    }

    private fun processTurn() {
        busy = true

        animationFrames = 4
        animations = 2
        moveSpeed = 0

        when (transportMode) {
            TransportMode.BIKE -> {
                animationFrames = 0
                animations = 0
            }
            TransportMode.RUN -> transportMode = TransportMode.WALK
            GameCharacter.TransportMode.WALK -> {/*TODO("WALK")*/}
            GameCharacter.TransportMode.SWIM -> {/*TODO("SWIM")*/}
        }

        if (frameNum < animations * animationFrames) {
            updateCurrentAnimation()
            finalizeMovement(moveSpeed)
            finalizeAnimations(animationFrames)
            frameNum++
        }

        if (frameNum >= animationFrames * animations) {
            frameNum = 0
            busy = false
        }
    }

    override fun update(gc: GameContainer) {
        checkInput()
        identifyAction()
        currentImage = currentAnimation?.currentFrame
        updateDepth()
    }

    enum class Action {
        IDLING,
        TURNING,
        MOVING,
        JUMPING
    }

    enum class TransportMode {
        WALK,
        RUN,
        BIKE,
        SWIM
    }
}
