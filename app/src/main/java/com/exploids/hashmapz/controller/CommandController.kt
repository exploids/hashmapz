package com.exploids.hashmapz.controller

import androidx.compose.runtime.currentRecomposeScope
import com.exploids.hashmapz.command.*
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayDeque

class CommandController(val currentState: CurrentState) {
    private var usedIndex : Int = 0
    private var usedHashcode : Int = 0

    fun commandHasFinished() : Boolean {
        return currentState.actionHasFinished!!
    }

    fun isNextStackEmpty() : Boolean {
        return currentState.nextCommands.isEmpty()
    }

    fun isPrevStackEmpty() : Boolean {
        return currentState.prevCommands.isEmpty()
    }


    fun add(key: String, value: String) {
        println("Add wurde aufgerufen")
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

    private fun calculateStepsAdd(commandDeck: ArrayDeque<Command>)  {
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
                if (checkIfLoadFactorIsExeeded()) {
                    commandDeck.addLast(ExtendAndRestructureCommand())
                }
            }
        }
        currentState.nextCommands = commandDeck
    }

    private fun checkIfLoadFactorIsExeeded(): Boolean {
        val entryCounter = currentState.keyList.count(predicate = {it != null})
        if (entryCounter > currentState.loadFactor * currentState.mapSize) {
            return true
        }
        return false
    }

    fun nextCommand() {
        println(currentState.nextCommands)
        executeNextCommand()
        updateNextCommand()
        if (checkIfLastCommandWasExecuted()) {
            sendActionHasFinished()
        }
    }

    /**
     * Executes the next Command if it exist in the nextCommands Stack
     */
    private fun executeNextCommand() {
        var nextCommand: Command
        var nextCommandsDeck: ArrayDeque<Command> = currentState.nextCommands!!

        nextCommand = nextCommandsDeck.first();
        nextCommand.doCommand(currentState)
    }

    /**
     * Updates the ViewModel`s prevCommands, nextCommands and currentCommand
     */
    private fun updateNextCommand(){

        var currentCommand: Command? = currentState.currentCommand
        currentState.nextCommands!!.removeFirst()
        currentState.prevCommands!!.add(currentCommand)
        if (!currentState.nextCommands!!.isEmpty()) {
            var nextCommand: Command = currentState.nextCommands!!.first()
            currentState.currentCommand = nextCommand
        }

    }

    private fun checkIfLastCommandWasExecuted() : Boolean {
        var nextCommandDeck: ArrayDeque<Command> = currentState.nextCommands!!
        if (nextCommandDeck.isEmpty()) {
            return true
        }

        return false
    }

    private fun sendActionHasFinished() {
        currentState.actionHasFinished = true
    }

    fun prevCommand() {
        if (!currentState.prevCommands!!.isEmpty()) {
            executePrevCommand()
            updatePrevCommand()
        }
    }

    private fun executePrevCommand() {
        var prevCommand: Command
        var prevCommandsStack: Stack<Command> = currentState.prevCommands!!

        prevCommand = prevCommandsStack.peek()
        prevCommand.undoCommand(currentState)
    }

    private fun updatePrevCommand() {
        var currentCommand: Command? = currentState.currentCommand
        var prevCommand: Command? = currentState.prevCommands!!.pop()
        currentState.nextCommands!!.addFirst(prevCommand!!)
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

    private fun calculateStepsSearch(commandDeck: ArrayDeque<Command>) {
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

    fun delete(key: String) {
        currentState.nextCommands = ArrayDeque<Command>()
        currentState.prevCommands = Stack<Command>()
        currentState.usedKey = key
        usedIndex = key.hashCode().mod(currentState.mapSize)

        calculateStepsDelete(currentState.nextCommands!!)
        currentState.currentCommand = currentState.nextCommands!!.first()
        nextCommand()
    }

    private fun calculateStepsDelete(commandDeck: ArrayDeque<Command>) {
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
                    commandDeck.addLast(DeleteEntryCommand())
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