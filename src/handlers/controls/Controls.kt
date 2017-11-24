package handlers.controls

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
    private val controlPriority = ArrayList<Controller>()
    val controllers = HashMap<String, Controller>()

    fun update(gc: GameContainer) {
        input[InputDir.UP] = gc.input.isKeyDown(KEY_UP)
        input[InputDir.DOWN] = gc.input.isKeyDown(KEY_DOWN)
        input[InputDir.LEFT] = gc.input.isKeyDown(KEY_LEFT)
        input[InputDir.RIGHT] = gc.input.isKeyDown(KEY_RIGHT)

        input[InputKey.A] = gc.input.isKeyPressed(KEY_X)
        input[InputKey.B] = gc.input.isKeyPressed(KEY_Z)
        input[InputKey.SEL] = gc.input.isKeyPressed(KEY_BACK)
        input[InputKey.START] = gc.input.isKeyPressed(KEY_ENTER)
        input[InputKey.L] = gc.input.isKeyPressed(KEY_A)
        input[InputKey.R] = gc.input.isKeyPressed(KEY_S)

        for (controller in controllers.values) {
            controller.update(gc)
        }
    }

    fun getInput(obj: Controller, button: InputKey): Boolean = hasPriority(obj) && input[button]
    fun getInput(obj: Controller, button: InputDir): Boolean = hasPriority(obj) && input[button]

    fun hasPriority(obj: Controller): Boolean = controlPriority[0] === obj
    fun givePriority(obj: Controller) {
        controlPriority.add(0, obj)
    }

    fun removePriority(obj: Controller) {
        controlPriority.trimToSize()
        controlPriority.remove(obj)
    }
}
