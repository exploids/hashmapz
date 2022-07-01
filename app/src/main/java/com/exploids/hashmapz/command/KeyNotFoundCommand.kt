package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class KeyNotFoundCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.keyNotFound = true
    }

    override fun undoCommand(state: CurrentState) {
        state.keyNotFound = null
    }
}