package entities.objectEntities

import entities.AbstractEntity
import entities.Animation
import entities.particles.NormalGrassParticle
import entities.particles.Particle
import entities.particles.ParticleManager
import handlers.Resources
import main.Main
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import region.RegionManager
import region.area.Area.Collide

open class GameCharacter(x: Int, y: Int, spritesheet: Image) : AbstractEntity(x, y) {

    var currentAction = Action.IDLING
    var canControl = true
    var direction = Direction.DOWN
        protected set

    // Animations
    protected var walk = arrayOfNulls<Animation>(4)
    protected var run = arrayOfNulls<Animation>(4)
    protected var bike = arrayOfNulls<Animation>(4)
    protected var swim = arrayOfNulls<Animation>(4)
    protected var fish = arrayOfNulls<Animation>(4)
    protected var current = arrayOfNulls<Animation>(4)
    protected var currentAnimation: Animation?

    protected var frameNum = 0

    protected var transportMode = TransportMode.WALK

    // Controls
    protected var upActive: Boolean = false
    protected var downActive: Boolean = false
    protected var leftActive: Boolean = false
    protected var rightActive: Boolean = false
    protected var bActive: Boolean = false
    protected var aActive: Boolean = false
    protected var selectActive: Boolean = false
    protected var startActive: Boolean = false
    protected var lActive: Boolean = false
    protected var rActive: Boolean = false

    protected var aReleased = true
    protected var bReleased = true
    protected var selectReleased = true
    protected var startReleased = true
    protected var lReleased = true
    protected var rReleased = true

    protected var paused = false

    // Collisions
    protected var blocked = false

    protected var forwardCollisionType: Int = 0

    protected var belowCollisionType: Int = 0

    init {
        setMapOffset(0, 0)
        setSpritesheet(spritesheet)

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

        //final Image[][] fishing = standardAnimation(0, 32, 32);
        //for(int i = 0; i < 4; i++) {
        //	fish[i] = new Animation(standardFrames, fishing[i]);
        //}

        currentAnimation = walk[Direction.DOWN.ordinal]
        currentAnimation?.setFrame(0)
        currentImage = currentAnimation?.currentFrame
    }

    private fun standardAnimation(yOffset: Int, width: Int, height: Int): Array<Array<Image>> {
        //TODO: Is this code quality up-to-par?
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
                        this.spriteSheet.getSubImage(2 * width, yOffset + 0, width, height)
                                .getFlippedCopy(true, false)),

                arrayOf(this.spriteSheet.getSubImage(width, yOffset, width, height),
                        this.spriteSheet.getSubImage(0, yOffset, width, height),
                        this.spriteSheet.getSubImage(2 * width, yOffset + 0, width, height))
        )
    }

    protected fun checkCollisions() {
        when (direction) {
            Direction.UP -> forwardCollisionType = RegionManager.currentArea.getCollisionValue((x + xMapOffset) / 16, Math.ceil((y + yMapOffset) / 16.0).toInt() - 1)
            Direction.DOWN -> forwardCollisionType = RegionManager.currentArea.getCollisionValue((x + xMapOffset) / 16, Math.floor((y + yMapOffset) / 16.0).toInt() + 1)
            Direction.LEFT -> forwardCollisionType = RegionManager.currentArea.getCollisionValue(Math.ceil((x + xMapOffset) / 16.0).toInt() - 1, (y + yMapOffset) / 16)
            Direction.RIGHT -> forwardCollisionType = RegionManager.currentArea.getCollisionValue(Math.floor((x + xMapOffset) / 16.0).toInt() + 1, (y + yMapOffset) / 16)
        }
        blocked = !(forwardCollisionType == Collide.NONE.ordinal || forwardCollisionType == Collide.GRASS.ordinal || forwardCollisionType == Collide.DARK_GRASS.ordinal)
    }

    protected fun checkInput() {

        if (canControl) {
            //System.out.println("ok");
            if (upActive) {
                if (direction != Direction.UP && currentAction == Action.IDLING) {
                    direction = Direction.UP
                    currentAction = Action.TURNING
                } else {
                    direction = Direction.UP
                    checkCollisions()
                    currentAction = Action.MOVING
                }
            } else if (downActive) {
                if (direction != Direction.DOWN && currentAction == Action.IDLING) {
                    direction = Direction.DOWN
                    currentAction = Action.TURNING
                } else {
                    direction = Direction.DOWN
                    checkCollisions()
                    if (forwardCollisionType == 6) {
                        currentAction = Action.JUMPING
                    } else {
                        currentAction = Action.MOVING
                    }
                }

            } else if (leftActive) {
                if (direction != Direction.LEFT && currentAction == Action.IDLING) {
                    direction = Direction.LEFT
                    currentAction = Action.TURNING
                } else {
                    direction = Direction.LEFT
                    checkCollisions()
                    currentAction = Action.MOVING
                }
            } else if (rightActive) {
                if (direction != Direction.RIGHT && currentAction == Action.IDLING) {
                    direction = Direction.RIGHT
                    currentAction = Action.TURNING
                } else {
                    direction = Direction.RIGHT
                    checkCollisions()
                    currentAction = Action.MOVING
                }
            } else {
                currentAction = Action.IDLING
            }

            if (bActive && currentAction != Action.IDLING && currentAction != Action.TURNING) {
                if (transportMode == TransportMode.WALK) {
                    transportMode = TransportMode.RUN
                }
            } else if (transportMode == TransportMode.RUN) {
                transportMode = TransportMode.WALK
            }

            if (aReleased && aActive) {
                //TODO: check front block and pull up dialogue check
                aReleased = false
            }
            if (!aActive) {
                aReleased = true
            }

            if (selectReleased && selectActive) {
                //TODO: toggle registered item
                if (transportMode == TransportMode.WALK) {
                    transportMode = TransportMode.BIKE
                } else if (transportMode == TransportMode.BIKE) {
                    transportMode = TransportMode.WALK
                }
                selectReleased = false
            }
            if (!selectActive) {
                selectReleased = true
            }

            if (currentAction != Action.IDLING) {
                frameNum = 0
            }
        }
    }

    private fun updateCurrentAnimation() {
        when (transportMode) {
            TransportMode.WALK -> current = walk
            TransportMode.RUN -> current = run
            TransportMode.BIKE -> current = bike
            TransportMode.SWIM -> current = swim
        }
    }

    private fun checkGrassCollision() {
        if (currentAction == Action.MOVING) {
            //step
            if (forwardCollisionType == Collide.GRASS.ordinal) {
                var xOffset = 0
                var yOffset = 0
                if (currentAction == Action.MOVING) {
                    when (direction) {
                        Direction.UP -> yOffset = -16
                        Direction.DOWN -> yOffset = 16
                        Direction.LEFT -> xOffset = -16
                        Direction.RIGHT -> xOffset = 16
                    }

                    if (frameNum == 0) {
                        val temp = NormalGrassParticle(x + xOffset, y + yOffset, this)
                        ParticleManager.add(temp.toString(), temp)
                    }
                }
            }
        }
    }

    private fun finalizeMovement(moveSpeed: Int) {
        checkGrassCollision()

        if (frameNum == 0 && !blocked) {
            belowCollisionType = forwardCollisionType
        }

        when (direction) {
            Direction.UP -> {
                currentAnimation = current[Direction.UP.ordinal]
                y -= if (blocked) 0 else moveSpeed
            }
            Direction.DOWN -> {
                currentAnimation = current[Direction.DOWN.ordinal]
                y += if (blocked && currentAction != Action.JUMPING) 0 else moveSpeed
            }
            Direction.LEFT -> {
                currentAnimation = current[Direction.LEFT.ordinal]
                x -= if (blocked) 0 else moveSpeed
            }
            Direction.RIGHT -> {
                currentAnimation = current[Direction.RIGHT.ordinal]
                x += if (blocked) 0 else moveSpeed
            }
        }
    }

    private fun finalizeAnimations(animationFrames: Int) {
        if (currentAction == Action.JUMPING) {
            val n = frameNum

            if (n == 0) {
                val particleParent = this

                val temp = object : Particle(x, y, Resources.PARTICLE["Jump Shadow"], -1.0) {
                    override fun uniqueUpdates() {
                        this.x = particleParent.getX()
                        this.y = particleParent.getY()
                        checkDeathCondition()
                    }
                }
                temp.setTickLimit(16)
                ParticleManager.add(temp.toString(), temp)
            } else if (n == 15) {
                var tempForwardCollisionType = 0
                when (direction) {
                    Direction.DOWN -> tempForwardCollisionType = RegionManager.currentArea.getCollisionValue(x / 16, Math.floor(y / 16.0).toInt() + 2)
                    Direction.LEFT -> tempForwardCollisionType = RegionManager.currentArea.getCollisionValue(Math.ceil(x / 16.0).toInt() - 2, y / 16)
                    Direction.RIGHT -> tempForwardCollisionType = RegionManager.currentArea.getCollisionValue(Math.floor(x / 16.0).toInt() + 2, y / 16)
                }
                if (tempForwardCollisionType == Collide.GRASS.ordinal) {
                    var xOffset = 0
                    var yOffset = 0
                    when (direction) {
                        Direction.DOWN -> yOffset = 16
                        Direction.LEFT -> xOffset = -16
                        Direction.RIGHT -> xOffset = 16
                    }
                    val particleParent = this

                    val temp = object : Particle(x + xOffset, y + yOffset, Resources.PARTICLE["Grass Jump"], 0.1) {
                        private var isStepOffActivated = false

                        private var stepOffTick: Long = 0

                        override fun deathCondition(): Boolean {

                            if (Main.ticks - startingTick >= 56 && !isStepOffActivated && (particleParent.getX() != x || particleParent.getY() != y)) {
                                stepOffTick = Main.ticks + 11
                                isStepOffActivated = true
                            }

                            return Main.ticks >= stepOffTick && isStepOffActivated
                        }

                        override fun uniqueUpdates() {
                            val tickDifference = Main.ticks - startingTick
                            if (tickDifference <= 56 && tickDifference != 0L) {
                                if (tickDifference == 16L) {
                                    animation.nextFrame()
                                } else if (tickDifference > 16 && tickDifference % 8 == 0L && tickDifference < 56) {
                                    animation.nextFrame()
                                }
                            }
                            checkDeathCondition()
                        }
                    }
                    ParticleManager.add(temp.toString(), temp)
                } else {
                    val particleParent = this

                    val temp = object : Particle(x, y, Resources.PARTICLE["Dirt Jump"], -1.0) {
                        override fun uniqueUpdates() {
                            if (Main.ticks - startingTick < 16) {
                                this.x = particleParent.getX()
                                this.y = particleParent.getY()
                            } else {
                                if (Main.ticks - startingTick == 16L) {
                                    this.x = particleParent.getX()
                                    this.y = particleParent.getY()
                                }
                                this.depthOffset = 0.1
                                if ((Main.ticks - startingTick) % 8 == 0L) {
                                    this.animation.nextFrame()
                                }
                            }
                            checkDeathCondition()
                        }
                    }
                    temp.setTickLimit(32)
                    ParticleManager.add(temp.toString(), temp)
                }
            }

            if (transportMode == TransportMode.BIKE) {
                if (n == 0 || n == 5 || n == 10 || n == 16 || n == 21 || n == 26) {
                    currentAnimation?.nextFrame()
                }
            } else if (animationFrames > 0 && frameNum % animationFrames == 0) {
                currentAnimation?.nextFrame()
            }
            if (n % 2 == 0 && n <= 13) {
                yMapOffset -= 2
            } else if (n == 14) {
                yMapOffset -= 1
            } else if (n == 18) {
                yMapOffset += 1
            } else if (n % 2 == 0 && n < 32) {
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

    protected fun processCliffJump() {
        canControl = false

        val animationFrames = 8
        val animations = 4
        val moveSpeed = 1

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
            canControl = true
        }
    }

    protected fun processIdle() {
        updateCurrentAnimation()
        finalizeMovement(0)
        finalizeAnimations(0)
        //TODO: Swim idle animation things
        //frameNum++;
        //if(frameNum >= 16)
        //{
        //	frameNum = 0;
        //}
    }

    protected fun processMovement() {
        canControl = false
        var animationFrames = 8
        var animations = 2
        var moveSpeed = 1

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
                canControl = true
            } else if (direction == Direction.UP && (downActive || leftActive || rightActive)
                    || direction == Direction.DOWN && (upActive || leftActive || rightActive)
                    || direction == Direction.LEFT && (upActive || downActive || rightActive)
                    || direction == Direction.RIGHT && (upActive || downActive || leftActive)) {
                frameNum = 0
                canControl = true
                currentAnimation?.setFrame(0)
            }
        } else if (frameNum >= animationFrames * animations) {
            frameNum = 0
            canControl = true
        }
    }

    override fun render(gc: GameContainer, g: Graphics) {
        currentImage.draw((x - 8 + xMapOffset + RegionManager.currentArea.x).toFloat(), (y - 16 + yMapOffset + RegionManager.currentArea.y).toFloat())
    }

    protected fun processTurn() {
        canControl = false

        var animationFrames = 4
        var animations = 2
        val moveSpeed = 0

        if (transportMode == TransportMode.BIKE) {
            animationFrames = 0
            animations = 0
        }

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
            if (RegionManager.currentArea.getCollisionValue(x / 16, y / 16) == 2) {
                //TODO: wild battle, player only
            }
            frameNum = 0
            canControl = true
        }
    }

    override fun update(gc: GameContainer) {
        uniqueUpdates()
        if (!paused) {
            checkInput()
            identifyAction()
        }
        currentImage = currentAnimation?.currentFrame
        depth = y / 16.0
    }

    override fun uniqueUpdates() {}

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
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
