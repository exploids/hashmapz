package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class UpdateValueCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.savedValue = state.valueList.get(state.currentIndex!!)
        state.valueList[state.currentIndex!!] = state.usedValue
    }

    override fun undoCommand(state: CurrentState) {
        state.valueList[state.currentIndex!!] = state.savedValue
    }

}