package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class ReturnValueCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.foundValue = state.valueList[state.currentIndex!!]
        state.prevDescription.add(state.currentDescription)
    }

    override fun undoCommand(state: CurrentState) {
        state.foundValue = null
        state.currentDescription = state.prevDescription.pop()
    }
}