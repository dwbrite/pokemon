package entities.characters

import entities.characters.GameCharacter.TransportMode.*
import util.Direction

internal interface State {
    //var busy: Boolean
    //var animationFrames: Int
    fun update(ch: GameCharacter)
    
}

object Idle : State {
    override fun update(ch: GameCharacter) {
        ch.updateCurrentAnimation()
        ch.finalizeMovement(0)
        ch.finalizeAnimations(0)
        //TODO("Swim idle animation things")
        //frameNum++;
        //if(frameNum >= 16)
        //{
        //	frameNum = 0;
        //}
    }
}

object Turning : State {
    override fun update(ch: GameCharacter) {
        ch.busy = true

        ch.animationFrames = 4
        ch.moveSpeed = 0

        val animLength = 2 * ch.animationFrames

        when (ch.transportMode) {
            GameCharacter.TransportMode.BIKE -> { ch.frameNum = 0; ch.busy = false }
            GameCharacter.TransportMode.RUN -> ch.transportMode = GameCharacter.TransportMode.WALK
            else -> { /* I don't believe walking and running have special behaviours */ }
        }

        if (ch.frameNum < animLength) {
            ch.updateCurrentAnimation()
            ch.finalizeMovement(ch.moveSpeed)
            ch.finalizeAnimations(ch.animationFrames)
            ch.frameNum++
        }

        if (ch.frameNum >= animLength) {
            ch.frameNum = 0
            ch.busy = false
        }
    }
}

object Moving : State {
    override fun update(ch: GameCharacter) {
        ch.busy = true
        ch.animationFrames = 8
        var animations = 2
        ch.moveSpeed = 1

        when(ch.transportMode) {
            RUN -> {
                ch.animationFrames = 4
                animations = 2
                ch.moveSpeed = 2
                if (ch.blocked) { ch.transportMode = WALK }
            }

            BIKE -> {
                ch.animationFrames = 2
                animations = 3
                ch.moveSpeed = Math.round(2 + (ch.frameNum + 1) * 0.33 % 1).toInt()
            }
            else -> { /*TODO("Swimming")*/ }
        }

        if (ch.blocked) {
            ch.animationFrames *= 2
            ch.moveSpeed = 0
        }

        if (ch.frameNum < animations * ch.animationFrames) {
            ch.updateCurrentAnimation()
            ch.finalizeMovement(ch.moveSpeed)
            ch.finalizeAnimations(ch.animationFrames)
            ch.frameNum++
        }

        if (ch.blocked) {
            if (ch.frameNum >= ch.animationFrames * animations) {
                ch.frameNum = 0
                ch.busy = false
            } else if (ch.direction == Direction.UP && (ch.controls[Direction.DOWN] || ch.controls[Direction.LEFT] || ch.controls[Direction.RIGHT])
                    || ch.direction == Direction.DOWN && (ch.controls[Direction.UP] || ch.controls[Direction.LEFT] || ch.controls[Direction.RIGHT])
                    || ch.direction == Direction.LEFT && (ch.controls[Direction.UP] || ch.controls[Direction.DOWN] || ch.controls[Direction.RIGHT])
                    || ch.direction == Direction.RIGHT && (ch.controls[Direction.UP] || ch.controls[Direction.DOWN] || ch.controls[Direction.LEFT])) {
                ch.frameNum = 0
                ch.busy = false
                ch.currentAnimation?.setFrame(0)
            }
        } else if (ch.frameNum >= ch.animationFrames * animations) {
            ch.frameNum = 0
            ch.busy = false
        }
    }
}

object Jumping : State {
    override fun update(ch: GameCharacter) {
        ch.busy = true

        ch.animationFrames = 8
        ch.moveSpeed = 1

        val animLength = 4 * ch.animationFrames

        if (ch.transportMode == GameCharacter.TransportMode.RUN) {
            ch.transportMode = GameCharacter.TransportMode.WALK
        }

        if (ch.frameNum < animLength) {
            ch.updateCurrentAnimation()
            ch.finalizeMovement(ch.moveSpeed)
            ch.finalizeAnimations(ch.animationFrames)
            ch.frameNum++
        }

        if (ch.frameNum >= animLength) {
            ch.frameNum = 0
            ch.busy = false
        }

    }
}