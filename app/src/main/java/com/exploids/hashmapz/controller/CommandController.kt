package com.exploids.hashmapz.controller

import androidx.compose.runtime.currentRecomposeScope
import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayDeque

class CommandController(val currentState: CurrentState) {

    fun add(key: String, value: Int) {
        currentState.usedKey = key
        currentState.usedValue = value
        currentState.usedHashcode = key.hashCode()
        currentState.actionHasFinished = false

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

    /**
     * Executes the next Command if it exist in the nextCommands Stack
     */
    fun executeNextCommand() {
        var nextCommand: Command
        var nextCommandsDeck: ArrayDeque<Command> = currentState.nextCommands

        nextCommand = nextCommandsDeck.first();
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

    fun checkIfLastCommandWasExecuted() : Boolean {
        var nextCommandDeck: ArrayDeque<Command> = currentState.nextCommands
        if (nextCommandDeck.isEmpty()) {
            return true
        }

        return false
    }

    fun sendActionHasFinished() {
        currentState.actionHasFinished = true
    }

    fun prevCommand() {
        if (!currentState.prevCommands.isEmpty()) {
            executePrevCommand()
            updatePrevCommand()
        }
    }

    fun executePrevCommand() {
        var prevCommand: Command
        var prevCommandsStack: Stack<Command> = currentState.prevCommands

        prevCommand = prevCommandsStack.peek()
        prevCommand.undoCommand()
    }

    fun updatePrevCommand() {
        var currentCommand: Command = currentState.currentCommand
        var prevCommand: Command = currentState.prevCommands.pop()
        currentState.prevCommands.add(currentCommand)
        currentState.currentCommand = prevCommand
    }


}