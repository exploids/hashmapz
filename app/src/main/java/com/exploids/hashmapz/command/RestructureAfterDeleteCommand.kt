package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.math.pow

class RestructureAfterDeleteCommand : Command{
    var savedKeyList: LinkedList<String> = LinkedList()
    var savedValueList: LinkedList<String> = LinkedList()
    var savedHashcodeList: LinkedList<Int> = LinkedList()

    override fun doCommand(state: CurrentState) {
        state.prevDescription.add(state.currentDescription)
        savedKeyList = LinkedList(state.keyList)
        savedValueList = LinkedList(state.valueList)
        savedHashcodeList = LinkedList(state.hashcodeList)

        state.keyList.clear()
        state.valueList.clear()
        state.hashcodeList.clear()
        for (loop in 1..state.mapSize){
            state.keyList.add(null)
            state.valueList.add(null)
            state.hashcodeList.add(null)
        }

        for (index in 0..(state.insertOrderKeyList.size - 1)) {
            restructureHashmap(
                state,
                state.insertOrderKeyList[index],
                state.insertOrderValueList[index]
            )
        }
        state.currentDescription = R.string.restructure_after_delete
    }

    override fun undoCommand(state: CurrentState) {
        state.keyList = LinkedList(savedKeyList)
        state.valueList = LinkedList(savedValueList)
        state.hashcodeList = LinkedList(savedHashcodeList)
        state.currentDescription = state.prevDescription.pop()
    }

    private fun restructureHashmap (currentState: CurrentState, key: String?, value: String?) {
        val keyList : LinkedList<String?> = currentState.keyList
        var index : Int? = key.hashCode().mod(currentState.mapSize)
        var steps : Int = 1
        var counter: Double = 1.0
        if ( currentState.keyList[index!!].equals(null) ) {
            currentState.keyList[index] = key
            currentState.valueList[index] = value
            currentState.hashcodeList[index] = key.hashCode()
        } else {
            while(keyList[index!!] != null) {
                if(currentState.probingMode.equals("Linear Probing")) {
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                } else if (currentState.probingMode.equals("Quadratic Probing")) {
                    steps = counter.pow(2).toInt()
                    index = index?.plus(steps)
                    index = index?.mod(currentState.mapSize)
                    counter++
                } else {

                }
            }
            if (index?.let { keyList.get(it).equals(null) } == true) {
                currentState.keyList[index] = key
                currentState.valueList[index] = value
                currentState.hashcodeList[index] = key.hashCode()
            }

        }
    }

}