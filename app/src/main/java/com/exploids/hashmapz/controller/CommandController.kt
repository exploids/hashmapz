package com.exploids.hashmapz.controller

import androidx.compose.runtime.currentRecomposeScope
import com.exploids.hashmapz.command.*
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayDeque

class CommandController(val currentState: CurrentState) {
    var usedIndex : Int = 0
    var usedHashcode : Int = 0


    fun add(key: String, value: Int) {
        currentState.nextCommands = ArrayDeque<Command>()
        currentState.prevCommands = Stack<Command>()
        currentState.usedKey = key
        currentState.usedValue = value
        usedHashcode = key.hashCode()
        usedIndex = key.hashCode().mod(currentState.mapSize)
        currentState.actionHasFinished = false
        calculateStepsAdd(currentState.nextCommands!!)
        currentState.currentCommand = currentState.nextCommands!!.first()

        nextCommand()
    }

    fun calculateStepsAdd(commandDeck: ArrayDeque<Command>)  {
        val keyList : LinkedList<String?> = currentState.keyList
        val key : String? = currentState.usedKey
        var index : Int? = usedIndex
        if ( index?.let { keyList.get(it) } == null ) {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            commandDeck.addLast(CheckFreeSlotCommand())
            commandDeck.addLast(InsertEntriesCommand())
        } else {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            while(index?.let { keyList.get(it) } != null || index?.let { keyList.get(it).equals(key) } == true) {
                commandDeck.addLast(CheckFreeSlotCommand())
                commandDeck.addLast(CheckIfKeysAreEqualCommand())
                if (index?.let { keyList.get(it).equals(key) } == true) {
                    commandDeck.addLast(UpdateValueCommand())
                    break
                }
                commandDeck.addLast(GoToIndexInStepsCommand())
                index = index?.plus(currentState.steps)
                index = index?.mod(currentState.mapSize)
            }
            if (index?.let { keyList.get(it).equals(null) } == true) {
                commandDeck.addLast(CheckFreeSlotCommand())
                commandDeck.addLast(CheckIfKeysAreEqualCommand())
                commandDeck.addLast(InsertEntriesCommand())
            }
        }
        currentState.nextCommands = commandDeck
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
        var nextCommandsDeck: ArrayDeque<Command> = currentState.nextCommands!!

        nextCommand = nextCommandsDeck.first();
        nextCommand.doCommand(currentState)
    }

    /**
     * Updates the ViewModel`s prevCommands, nextCommands and currentCommand
     */
    fun updateNextCommand(){

        var currentCommand: Command? = currentState.currentCommand
        currentState.nextCommands!!.removeFirst()
        currentState.prevCommands!!.add(currentCommand)
        if (!currentState.nextCommands!!.isEmpty()) {
            var nextCommand: Command = currentState.nextCommands!!.first()
            currentState.currentCommand = nextCommand
        }

    }

    fun checkIfLastCommandWasExecuted() : Boolean {
        var nextCommandDeck: ArrayDeque<Command> = currentState.nextCommands!!
        if (nextCommandDeck.isEmpty()) {
            return true
        }

        return false
    }

    fun sendActionHasFinished() {
        currentState.actionHasFinished = true
    }

    fun prevCommand() {
        if (!currentState.prevCommands!!.isEmpty()) {
            executePrevCommand()
            updatePrevCommand()
        }
    }

    fun executePrevCommand() {
        var prevCommand: Command
        var prevCommandsStack: Stack<Command> = currentState.prevCommands!!

        prevCommand = prevCommandsStack.peek()
        prevCommand.undoCommand(currentState)
    }

    fun updatePrevCommand() {
        var currentCommand: Command? = currentState.currentCommand
        var prevCommand: Command? = currentState.prevCommands!!.pop()
        currentState.nextCommands!!.addLast(currentCommand!!)
        currentState.currentCommand = prevCommand
    }

    fun search(key: String) {
        currentState.nextCommands = ArrayDeque<Command>()
        currentState.prevCommands = Stack<Command>()
        currentState.usedKey = key
        usedIndex = key.hashCode().mod(currentState.mapSize)

        calculateStepsSearch(currentState.nextCommands!!)
        currentState.currentCommand = currentState.nextCommands!!.first()
        nextCommand()
    }

    fun calculateStepsSearch(commandDeck: ArrayDeque<Command>) {
        val keyList : LinkedList<String?> = currentState.keyList
        val key : String? = currentState.usedKey
        var index : Int? = usedIndex

        if (keyList[index!!] == null) {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            commandDeck.addLast(CheckFreeSlotCommand())
            commandDeck.addLast(KeyNotFoundCommand())
        } else {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())

            while (keyList[index!!] != null) {
                commandDeck.addLast(CheckFreeSlotCommand())
                commandDeck.addLast(CheckIfKeysAreEqualCommand())
                if (keyList[index!!].equals(key)) {
                    commandDeck.addLast(ReturnValueCommand())
                    break
                }
                commandDeck.addLast(GoToIndexInStepsCommand())
                index = index?.plus(currentState.steps)
                index = index?.mod(currentState.mapSize)
            }
            if (keyList[index!!] == null) {
                commandDeck.addLast(CheckFreeSlotCommand())
                commandDeck.addLast(CheckIfKeysAreEqualCommand())
                commandDeck.addLast(KeyNotFoundCommand())
            }
        }
        currentState.nextCommands = commandDeck
    }

}