package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayList

class ExtendAndRestructureCommand : Command {
    override fun doCommand(state: CurrentState) {
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


        //Add all Entries an calculate


        for (index in 0..(keyEntries.size-1)) {
            val indexInHashmap = hashCodeEntries[index].mod(state.mapSize)
            state.keyList[indexInHashmap] = keyEntries[index]
            state.valueList[indexInHashmap] = valueEntries[index]
            state.hashcodeList[indexInHashmap] = hashCodeEntries[index]
        }

        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.resize_map
    }

    override fun undoCommand(state: CurrentState) {
        state.mapSize -= state.mapSize/2
        state.keyList = LinkedList(state.savedKeyList)
        state.valueList = LinkedList(state.savedValueList)
        state.hashcodeList = LinkedList(state.savedHashcodeList)
        state.currentDescription = state.prevDescription.pop()
    }


}