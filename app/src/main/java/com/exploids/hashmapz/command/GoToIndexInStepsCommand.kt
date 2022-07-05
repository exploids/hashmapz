package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState
import kotlin.math.pow

class GoToIndexInStepsCommand : Command {

    override fun doCommand(state: CurrentState) {
        if (state.probingMode.equals("Quadratic Probing")) {
            state.steps = state.collisionCounter.pow(2).toInt()
            state.collisionCounter += 1
        }
        state.currentIndex = (state.currentIndex?.plus(state.steps))?.mod(state.mapSize)
        state.prevDescription.add(state.currentDescription)
        state.currentDescription = R.string.go_to_index_in_steps

    }

    override fun undoCommand(state: CurrentState) {
        if (state.probingMode.equals("Quadratic Probing")) {
            state.collisionCounter -= 1
            state.steps = state.collisionCounter.pow(2).toInt()
        }
        state.currentIndex = (state.currentIndex?.minus(state.steps))?.mod(state.mapSize)
        state.currentDescription = state.prevDescription.pop()
    }

}