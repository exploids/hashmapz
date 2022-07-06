package com.exploids.hashmapz.model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.exploids.hashmapz.R
import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.createIntList
import com.exploids.hashmapz.createStringList
import java.util.*
import kotlin.collections.ArrayDeque

class CurrentStateViewModel() : ViewModel() {
    var state by mutableStateOf( CurrentState(
        mapSize = 8,
        steps = 1,
        keyList = createStringList(),
        valueList = createStringList(),
        hashcodeList = createIntList(),
        nextCommands = ArrayDeque<Command>(),
        currentDescription = R.string.explanation_getting_started
    ))

    var isPrevDisabled by mutableStateOf(state.prevCommands.isEmpty())

    var isNextDisabled by mutableStateOf(state.nextCommands.isEmpty())

    var listKey by mutableStateOf(state.keyList)

    var valueList by mutableStateOf(state.valueList)

    var hashList by mutableStateOf(state.hashcodeList)

    var currentIndex by mutableStateOf(state.currentIndex)

    var currentDescription by mutableStateOf(state.currentDescription)

    var mapSize by mutableStateOf(state.mapSize)

    init {
        val commandController = getCommandController()
        commandController.renewMap("Linear Probing", 0.75F)
        update()
    }

    fun update() {
        isPrevDisabled = state.prevCommands.isEmpty()
        isNextDisabled = state.nextCommands.isEmpty()
        listKey = LinkedList(state.keyList.toList())
        valueList = LinkedList(state.valueList.toList())
        hashList = LinkedList(state.hashcodeList.toList())
        currentIndex = state.currentIndex
        currentDescription = state.currentDescription
        mapSize = state.mapSize
    }








    fun getCommandController() : CommandController{
        return CommandController(state,this)
    }


}



