package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class GoToIndexCommand : Command {
    override fun doCommand(state: CurrentState) {
        state.currentIndex = state.usedIndex
        println(state.currentIndex)
        println(state.usedIndex)
    }

    override fun undoCommand(state: CurrentState) {
        TODO("Not yet implemented")
    }
}