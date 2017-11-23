package handlers.controls

import org.newdawn.slick.GameContainer

public abstract class Controller {
    // TODO: find repeated functionality from child classes.
    //Okej, so
    // A controller will control *something*. It could be a menu, or a game character. Or... something else.
    public abstract fun update(gc: GameContainer)
}