package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState

class ReturnValueCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.foundValue = state.valueList[state.currentIndex!!]
        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.return_value
    }

    override fun undoCommand(state: CurrentState) {
        state.foundValue = null
        state.currentDescription = state.prevDescription.pop()
    }
}