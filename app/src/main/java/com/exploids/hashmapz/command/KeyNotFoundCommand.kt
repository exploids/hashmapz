package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState

class KeyNotFoundCommand : Command {
    var savedIndex : Int = 0
    override fun doCommand(state: CurrentState) {
        state.keyNotFound = true
        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.key_not_found
        savedIndex = state.currentIndex!!
        state.currentIndex = null
    }

    override fun undoCommand(state: CurrentState) {
        state.currentIndex = savedIndex
        state.keyNotFound = null
        state.currentDescription = state.prevDescription.pop()
    }
}