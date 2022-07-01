package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class GoToIndexInStepsCommand : Command {

    override fun doCommand(state: CurrentState) {
        state.currentIndex = (state.currentIndex?.plus(state.steps))?.mod(state.mapSize)
    }

    override fun undoCommand(state: CurrentState) {
        state.currentIndex = (state.currentIndex?.minus(state.steps))?.mod(state.mapSize)
    }

}