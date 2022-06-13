package com.exploids.hashmapz.controller

import androidx.compose.runtime.currentRecomposeScope
import com.exploids.hashmapz.command.*
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayDeque

class CommandController(val currentState: CurrentState) {

    fun add(key: String, value: Int) {
        currentState.usedKey = key
        currentState.usedValue = value
        currentState.usedHashcode = key.hashCode()
        currentState.usedIndex = key.hashCode() % currentState.mapSize
        currentState.actionHasFinished = false

        calculateStepsAdd(currentState.nextCommands)

        nextCommand()
    }

    fun calculateStepsAdd(commandDeck: ArrayDeque<Command>)  {
        val keyList : LinkedList<String> = currentState.keyList
        val key : String = currentState.usedKey
        var index : Int = currentState.usedIndex
        if ( keyList.isEmpty() || keyList.get(index) == null ) {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            commandDeck.addLast(CheckFreeSlotCommand())
            commandDeck.addLast(InsertEntriesCommand())
        } else if (keyList.get(index) == key) {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            commandDeck.addLast(CheckFreeSlotCommand())
            commandDeck.addLast(UpdateValueCommand())
        } else {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            while(keyList.get(index) != null) {
                commandDeck.addLast(CheckFreeSlotCommand())
                commandDeck.addLast(GoToIndexInStepsCommand())
                index += currentState.steps
                index = index % currentState.mapSize
            }
            if (keyList.get(index) == key) {
                commandDeck.addLast(UpdateValueCommand())
            } else {
                commandDeck.addLast(InsertEntriesCommand())
            }
        }
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