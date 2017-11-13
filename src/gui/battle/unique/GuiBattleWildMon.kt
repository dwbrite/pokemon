package gui.battle.unique

import entities.objectEntities.GameCharacter
import entities.objectEntities.Player
import entities.pokemon.Pokemon
import entities.pokemon.Species
import gameState.GameStateManager
import gui.GuiTextBox
import gui.battle.GuiBattleText
import gui.childElements.GuiBox

/**
 * Created by dwbrite on 5/15/16.
 */
class GuiBattleWildMon(mon: Pokemon, private val parent: GameCharacter) : GuiTextBox("A wild " + Species.getData(mon.species, Species.Column.POKEMON_NAME) + " has appeared!", GuiBox.BORDER_BATTLE) {

    init {
        this.setGuiManager(GameStateManager.getGameState(1).guiManager)
    }

    override fun activate() {
        val firstMon = (parent as Player).firstConsciousMonster
        val btext = object : GuiBattleText("Go! " + firstMon!!.name + "!", 64) {
            override fun finishedAction() {
                remove()
            }
        }
        remove()
        btext.setGuiManager(GameStateManager.getGameState(1).guiManager)
    }
}
