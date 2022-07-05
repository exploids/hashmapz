package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState
import com.exploids.hashmapz.R

class UpdateValueCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.savedValue = state.valueList.get(state.currentIndex!!)
        state.valueList[state.currentIndex!!] = state.usedValue
        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.update_value
    }

    override fun undoCommand(state: CurrentState) {
        state.valueList[state.currentIndex!!] = state.savedValue
        state.currentDescription = state.prevDescription.pop()
    }

}