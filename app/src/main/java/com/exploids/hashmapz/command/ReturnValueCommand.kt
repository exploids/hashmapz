package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class ReturnValueCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.foundValue = state.valueList[state.currentIndex!!]
    }

    override fun undoCommand(state: CurrentState) {
        state.foundValue = null
    }
}