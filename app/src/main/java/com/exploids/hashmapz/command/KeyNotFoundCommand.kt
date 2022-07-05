package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState

class KeyNotFoundCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.keyNotFound = true
        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.key_not_found
    }

    override fun undoCommand(state: CurrentState) {
        state.keyNotFound = null
        state.currentDescription = state.prevDescription.pop()
    }
}