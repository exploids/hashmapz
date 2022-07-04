package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState

class GoToIndexCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.currentIndex = state.usedIndex
        state.prevDescription = state.currentDescription
        state.currentDescription = R.string.go_to_index
    }

    override fun undoCommand(state: CurrentState) {
        state.currentIndex = null
        state.currentDescription = state.prevDescription!!
    }
}