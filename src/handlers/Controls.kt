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

    private val input = BooleanArray(10)

    private val controlPriority = ArrayList<Any>()

    // TODO: Is it __really__ dirty if it's this neat?
    fun update(gc: GameContainer) {
        input[InputDir.UP.ordinal] = gc.input.isKeyDown(KEY_UP)
        input[InputDir.DOWN.ordinal] = gc.input.isKeyDown(KEY_DOWN)
        input[InputDir.LEFT.ordinal] = gc.input.isKeyDown(KEY_LEFT)
        input[InputDir.RIGHT.ordinal] = gc.input.isKeyDown(KEY_RIGHT)
        input[InputKey.A.ordinal+4] = gc.input.isKeyDown(KEY_X)
        input[InputKey.B.ordinal+4] = gc.input.isKeyDown(KEY_Z)
        input[InputKey.SEL.ordinal+4] = gc.input.isKeyDown(KEY_BACK)
        input[InputKey.START.ordinal+4] = gc.input.isKeyDown(KEY_ENTER)
        input[InputKey.L.ordinal+4] = gc.input.isKeyDown(KEY_A)
        input[InputKey.R.ordinal+4] = gc.input.isKeyDown(KEY_S)
    }

    private fun checkControls() {
        //TODO: ... This is never used, hm?
    }

    fun getInput(button: InputKey, obj: Any): Boolean {
        return hasPriority(obj) && input[button.ordinal + 4] //TODO: for some reason I had a +6 in here?
    }

    fun getInput(button: InputDir, obj: Any): Boolean {
        return hasPriority(obj) && input[button.ordinal]
    }

    fun hasPriority(obj: Any): Boolean {
        return controlPriority[0] === obj
    }

    fun givePriority(obj: Any) {
        controlPriority.add(0, obj)
    }

    fun removePriority(obj: Any) {
        if (controlPriority.contains(obj)) {
            controlPriority.remove(obj)
            controlPriority.trimToSize()
        } //TODO: This silently ignores requests to delete objects that don't have priority. Should that be different?
    }
}
