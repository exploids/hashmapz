package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

class ExtendAndRestructureCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.prevDescription.add(state.currentDescription)
        state.savedKeyList = LinkedList(state.keyList)
        state.savedValueList = LinkedList(state.valueList)
        state.savedHashcodeList = LinkedList(state.hashcodeList)
        //Save Entries not null
        var keyEntries: ArrayList<String> = ArrayList<String>()
        var valueEntries: ArrayList<String> = ArrayList<String>()
        var hashCodeEntries: ArrayList<Int> = ArrayList<Int>()
        for (index in 0..(state.mapSize-1)) {
            if (state.keyList[index] != null) {
                keyEntries.add(state.keyList[index]!!)
                valueEntries.add(state.valueList[index]!!)
                hashCodeEntries.add(state.hashcodeList[index]!!)
            }
        }
        //Extend Hashmap by creating a new one with double Entry
        state.mapSize += state.mapSize

        state.keyList.clear()
        state.valueList.clear()
        state.hashcodeList.clear()
        for (loop in 1..state.mapSize){
            state.keyList.add(null)
            state.valueList.add(null)
            state.hashcodeList.add(null)
        }

        for (index in 0..(keyEntries.size-1)) {
            restructureHashmap(
                state,
                keyEntries[index],
                valueEntries[index]
            )
        }
        state.currentDescription = R.string.resize_map

    }

    override fun undoCommand(state: CurrentState) {
        state.mapSize -= state.mapSize/2
        state.keyList = LinkedList(state.savedKeyList)
        state.valueList = LinkedList(state.savedValueList)
        state.hashcodeList = LinkedList(state.savedHashcodeList)
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