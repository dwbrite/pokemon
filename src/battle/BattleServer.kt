package battle

import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics

object BattleServer {
    /*
    Battle transition
        Wild battle:
            "A wild rattata has appeared"
        Normal battle:
            [Trainer] would like to battle!
            [Trainer] sent out [pokemon]
        {Player:} Go! [First conscious pokemon]!

    Player's turn
        for each pokemon that's out
            actions (attack, use item, switch mon, or run)
    Enemy's turn
        repeat action choice for opponent (attack, use item, switch mon, ((AI)?" ":"run"))


    Check which pokemon should go first based on the action done.
        If the action was a bag, switch, or run, attempt that first. Player first, then opponent
            don't do beginTurnUpdate() for each effect, because you're forfeiting the attack turn
            do the switch/run/item and checks that apply to them (...?)
        If the action was a move, whichever Pokemon or move is faster occurs first.
            then do beginTurnUpdate() for each effect
            do the attack
    After all turns have finished, do endTurnUpdate() for each effect
        Now check for K.Os and either force a switch or end the battle
            Give EXP for the K.O. (this should automatically check for level-ups and grant new moves if necessary)
        Player win:
              Enemy loses, bring up winning text and give money
        Player lose:
              Player sent
    Done this turn. Repeat.
    */

    val ActionQueue = ArrayList<Action>()

    fun render(gc: GameContainer, g: Graphics) {
    }

    fun update(gc: GameContainer) {
        //pokemon has appeared and stuff

        //the server giveth and the server taketh away (control)

        /*
        if queue is full (how do we define "full"?)
            state = executing
        when(state)
            executing ->
                order queue by priority (switch, bag/item, run, attack (move dependent) )
                for each action in queue
                    if action hasn't started and hasn't been completed
                        beginTurnUpdate
                    else if action hasn't been completed
                        action.update
                        if action gets completed this turn and a pokemon is KO'd
                            endTurnUpdate for KO'd mon.
                    else (actions are all complete)
                        endTurnUpdate { reset vars; remove action from queue }
                        if queue is empty
                            state = end
            end ->
                if KO'd
                    if no more pokemon
                        end battle
                    else
                        force switch
        */
    }


}