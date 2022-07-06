package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState

class InsertEntriesCommand : Command {
    var savedIndex : Int = 0
    override fun doCommand(state: CurrentState) {
        state.insertOrderKeyList.add(state.usedKey)
        state.insertOrderValueList.add(state.usedValue)
        state.keyList[state.currentIndex!!] = state.usedKey
        state.valueList[state.currentIndex!!] = state.usedValue
        state.hashcodeList[state.currentIndex!!] = state.usedHashcode
        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.insert_entries
        savedIndex = state.currentIndex!!
        state.currentIndex = null
    }

    override fun undoCommand(state: CurrentState) {
        state.currentIndex = savedIndex
        state.insertOrderValueList.remove()
        state.insertOrderKeyList.remove()
        state.keyList[state.currentIndex!!] = null
        state.valueList[state.currentIndex!!] = null
        state.hashcodeList[state.currentIndex!!] = null
        state.currentDescription = state.prevDescription.pop()
    }

}