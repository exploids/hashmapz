package com.exploids.hashmapz.controller

import com.exploids.hashmapz.R
import com.exploids.hashmapz.R.string
import com.exploids.hashmapz.command.*
import com.exploids.hashmapz.createIntList
import com.exploids.hashmapz.createStringList
import com.exploids.hashmapz.model.CurrentStateViewModel
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.pow

class CommandController(val currentState: CurrentState, val currentStateViewModel: CurrentStateViewModel) {
    private var usedIndex : Int = 0
    private var usedHashcode : Int = 1

    fun commandHasFinished() : Boolean {
        return currentState.actionHasFinished!!
    }

    fun renewMap (probingMode: String,loadFactor: Float) {
        currentState.keyList = createStringList()
        currentState.valueList = createStringList()
        currentState.hashcodeList = createIntList()
        currentState.probingMode = probingMode
        currentState.loadFactor = loadFactor
        currentState.nextCommands = ArrayDeque<Command>()
        currentState.prevCommands = Stack<Command>()
        currentState.currentDescription = R.string.app_name
        currentState.currentIndex = null
    }
    fun add(key: String, value: String) {
        currentState.currentIndex = null
        currentState.steps = 1
        currentState.collisionCounter = 1.0
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
        var steps : Int = 1
        var counter: Double = 1.0
        if ( index?.let { keyList.get(it) } == null ) {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            commandDeck.addLast(CheckFreeSlotCommand())
            commandDeck.addLast(InsertEntriesCommand())
        } else {
            commandDeck.addLast(CalculateIndexCommand())
            commandDeck.addLast(GoToIndexCommand())
            while(index?.let { keyList.get(it) } != null || index?.let { keyList.get(it).equals(key) } == true) {
                if (keyList[index!!] == null) {
                    break
                }
                commandDeck.addLast(CheckFreeSlotCommand())
                commandDeck.addLast(CheckIfKeysAreEqualCommand())
                if (index?.let { keyList.get(it).equals(key) } == true) {
                    commandDeck.addLast(UpdateValueCommand())
                    break
                }
                commandDeck.addLast(GoToIndexInStepsCommand())
                if(currentState.probingMode.equals("Linear Probing")) {
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                } else if (currentState.probingMode.equals("Quadratic Probing")) {
                    steps = counter.pow(2).toInt()
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                    counter++
                } else {
                    index = key.hashCode().hashCode()
                }
            }
            if (index?.let { keyList.get(it).equals(null) } == true) {
                commandDeck.addLast(CheckFreeSlotCommand())
                commandDeck.addLast(InsertEntriesCommand())
            }

        }
        if (checkIfLoadFactorIsExeeded()) {
            commandDeck.addLast(ExtendAndRestructureCommand())
        }
        currentState.nextCommands = commandDeck
    }

    private fun checkIfLoadFactorIsExeeded(): Boolean {
        val entryCounter = currentState.keyList.count(predicate = {it != null})
        print(entryCounter > currentState.loadFactor * currentState.mapSize)
        if (entryCounter >= currentState.loadFactor * currentState.mapSize) {
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
        currentStateViewModel.update()
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
        currentStateViewModel.update()
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
        currentState.currentIndex = null
        currentState.steps = 1
        currentState.collisionCounter = 1.0
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
        var steps : Int = 1
        var counter: Double = 1.0

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
                if(currentState.probingMode.equals("Linear Probing")) {
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                } else if (currentState.probingMode.equals("Quadratic Probing")) {
                    steps = counter.pow(2).toInt()
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                    counter++
                } else {
                    index = key.hashCode().hashCode()
                }
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
        currentState.currentIndex = null
        currentState.steps = 1
        currentState.collisionCounter = 1.0
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
        var steps : Int = 1
        var counter: Double = 1.0

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
                if(currentState.probingMode.equals("Linear Probing")) {
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                } else if (currentState.probingMode.equals("Quadratic Probing")) {
                    steps = counter.pow(2).toInt()
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                    counter++
                } else {
                    index = key.hashCode().hashCode()
                }
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