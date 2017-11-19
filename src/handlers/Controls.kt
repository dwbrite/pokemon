package handlers

import org.newdawn.slick.GameContainer
import org.newdawn.slick.Input.*
import java.util.*

object Controls {

    enum class InputKey {
        A,
        B,
        SEL,
        START,
        L,
        R
    }

    enum class InputDir {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private val input = ControlMap()

    private val controlPriority = ArrayList<Any>()

    // TODO: Is it __really__ dirty if it's this neat?
    fun update(gc: GameContainer) {
        input[InputDir.UP] = gc.input.isKeyDown(KEY_UP)
        input[InputDir.DOWN] = gc.input.isKeyDown(KEY_DOWN)
        input[InputDir.LEFT] = gc.input.isKeyDown(KEY_LEFT)
        input[InputDir.RIGHT] = gc.input.isKeyDown(KEY_RIGHT)
        input[InputKey.A] = gc.input.isKeyDown(KEY_X)
        input[InputKey.B] = gc.input.isKeyDown(KEY_Z)
        input[InputKey.SEL] = gc.input.isKeyDown(KEY_BACK)
        input[InputKey.START] = gc.input.isKeyDown(KEY_ENTER)
        input[InputKey.L] = gc.input.isKeyDown(KEY_A)
        input[InputKey.R] = gc.input.isKeyDown(KEY_S)
    }

    fun getInput(button: InputKey, obj: Any): Boolean = hasPriority(obj) && input[button]
    fun getInput(button: InputDir, obj: Any): Boolean = hasPriority(obj) && input[button]

    fun hasPriority(obj: Any): Boolean = controlPriority[0] === obj
    fun givePriority(obj: Any) { controlPriority.add(0, obj) }

    fun removePriority(obj: Any) {

        if (controlPriority.contains(obj)) {
            controlPriority.remove(obj)
            controlPriority.trimToSize()
        } //TODO: This silently ignores requests to delete objects that don't have priority. Should that be different?
    }
}
