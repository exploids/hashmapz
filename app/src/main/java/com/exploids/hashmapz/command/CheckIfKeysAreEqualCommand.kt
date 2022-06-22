package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState
import java.util.*

class CheckIfKeysAreEqualCommand : Command {
    override fun doCommand(state: CurrentState) {
        val keyList: LinkedList<String?> = state.keyList
        if (state.currentIndex?.let { keyList.get(it).equals(state.usedKey) } == true) {
                state.isKeyEqual = true
            } else {
                state.isKeyEqual = false
        }
    }

    override fun undoCommand(state: CurrentState) {
        TODO("Not yet implemented")
    }
}