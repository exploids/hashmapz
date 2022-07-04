package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class GoToIndexInStepsCommand : Command {

    override fun doCommand(state: CurrentState) {
        state.currentIndex = (state.currentIndex?.plus(state.steps))?.mod(state.mapSize)
        state.prevDescription.add(state.currentDescription)
    }

    override fun undoCommand(state: CurrentState) {
        state.currentIndex = (state.currentIndex?.minus(state.steps))?.mod(state.mapSize)
        state.currentDescription = state.prevDescription.pop()
    }

}