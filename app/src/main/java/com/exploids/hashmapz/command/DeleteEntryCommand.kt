package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState

class DeleteEntryCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.savedKey = state.keyList[state.currentIndex!!]
        state.savedValue = state.valueList[state.currentIndex!!]
        state.savedHashcode = state.hashcodeList[state.currentIndex!!]
        state.keyList[state.currentIndex!!] = null
        state.valueList[state.currentIndex!!] = null
        state.hashcodeList[state.currentIndex!!] = null
        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.delete_entry
    }

    override fun undoCommand(state: CurrentState) {
        state.keyList[state.currentIndex!!] = state.savedKey
        state.valueList[state.currentIndex!!] = state.savedValue
        state.hashcodeList[state.currentIndex!!] = state.savedHashcode
        state.currentDescription = state.prevDescription.pop()
    }
}