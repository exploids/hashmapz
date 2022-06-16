package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class UpdateValueCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.keyList[state.currentIndex] = state.usedKey
        state.valueList[state.currentIndex] = state.usedValue
    }

    override fun undoCommand(state: CurrentState) {
        TODO("Not yet implemented")
    }

}