package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState
import java.util.*

class CheckIfKeysAreEqualCommand : Command {
    override fun doCommand(state: CurrentState) {
        val keyList: LinkedList<String?> = state.keyList
        state.prevDescription.add(state.currentDescription)
        if (state.currentIndex?.let { keyList.get(it).equals(state.usedKey) } == true) {
                state.isKeyEqual = true
                state.currentDescription = R.string.check_equal_case1
            } else {
                state.isKeyEqual = false
                state.currentDescription = R.string.check_equal_case2
        }
    }

    override fun undoCommand(state: CurrentState) {
        state.isKeyEqual = null
        state.currentDescription = state.prevDescription.pop()
    }
}