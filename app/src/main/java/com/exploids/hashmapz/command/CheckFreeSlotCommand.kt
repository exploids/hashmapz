package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState
import java.util.*

class CheckFreeSlotCommand : Command {

    override fun doCommand(state: CurrentState) {
        val keyList: LinkedList<String?> = state.keyList
        state.prevDescription.add(state.currentDescription)
        if (state.currentIndex?.let { keyList.get(it) } == null) {
            state.isSlotFree = true
            state.currentDescription = R.string.check_free_slot_true
        } else {
            state.isSlotFree = false
            state.currentDescription = R.string.check_free_slot_false
        }

    }

    override fun undoCommand(state: CurrentState) {
        state.isSlotFree = null
        state.currentDescription = state.prevDescription.pop()
    }

}