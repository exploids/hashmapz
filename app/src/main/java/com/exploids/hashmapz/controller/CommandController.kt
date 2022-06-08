package com.exploids.hashmapz.controller

import androidx.compose.runtime.currentRecomposeScope
import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.model.CurrentState

class CommandController(val currentState: CurrentState) {

    fun add(key: String, value: Int) {
        currentState.usedKey = key
        currentState.usedValue = value
        currentState.usedHashcode = key.hashCode()

        //calculateSteps

        //Fill next steck


        nextCommand()
    }

    fun nextCommand() {
        executeNextCommand()
        updateNextCommand()
        if (checkIfLastCommandWasExecuted()) {
            sendActionHasFinished()
        }
    }

    fun checkIfLastCommandWasExecuted() : Boolean {
        var nextCommandDeck: ArrayDeque<Command> = currentState.nextCommands
        if (nextCommandDeck.isEmpty()) {
            return true
        }

        return false
    }

    fun sendActionHasFinished() {
        //ToDo
    }

    /**
     * Executes the next Command if it exist in the nextCommands Stack
     */
    fun executeNextCommand() {
        var nextCommand: Command
        var nextCommandDeck: ArrayDeque<Command> = currentState.nextCommands

        nextCommand = nextCommandDeck.first();
        nextCommand.doCommand()
    }

    /**
     * Updates the ViewModel`s prevCommands, nextCommands and currentCommand
     */
    fun updateNextCommand(){

        var currentCommand: Command = currentState.currentCommand
        var nextCommand: Command = currentState.nextCommands.first()
        currentState.prevCommands.add(currentCommand)
        currentState.currentCommand = nextCommand
        currentState.nextCommands.removeFirst()

    }

    fun prevCommand() {

    }
}