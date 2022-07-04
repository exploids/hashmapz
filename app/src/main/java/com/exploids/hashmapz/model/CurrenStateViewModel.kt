package com.exploids.hashmapz.model

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.command.GoToIndexCommand
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.createIntList
import com.exploids.hashmapz.createStringList
import java.util.*
import kotlin.collections.ArrayDeque

class CurrenStateViewModel() : ViewModel() {

    var state by mutableStateOf( CurrentState(
        mapSize = 16,
        steps = 1,
        keyList = createStringList(),
        valueList = createStringList(),
        hashcodeList = createIntList()
    ))

    var isPrevDisabled by mutableStateOf(state.prevCommands.isEmpty())

    var isNextDisabled by mutableStateOf(state.nextCommands.isEmpty())

    var listKey by mutableStateOf(state.keyList)

    var valueList by mutableStateOf(state.valueList)

    var hashList by mutableStateOf(state.hashcodeList)

    var currentIndex by mutableStateOf(state.currentIndex)

    var currentDescription by mutableStateOf(state.currentDescription)





    fun update() {
        isPrevDisabled = state.prevCommands.isEmpty()
        isNextDisabled = state.nextCommands.isEmpty()
        listKey = LinkedList(state.keyList)
        valueList = LinkedList(state.valueList)
        hashList = LinkedList(state.hashcodeList)
        currentIndex = state.currentIndex
        currentDescription = state.currentDescription
    }








    fun getCommandController() : CommandController{
        return CommandController(state)
    }


}



