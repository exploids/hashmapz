package com.exploids.hashmapz.command

import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState
import java.util.*

class CheckFreeSlotCommand : Command {

    override fun doCommand(state: CurrentState) {
        val keyList: LinkedList<String?> = state.keyList
        state.prevDescription = state.currentDescription
        if (state.currentIndex?.let { keyList.get(it) } == null) {
            state.isSlotFree = true
            state.currentDescription = R.string.check_free_slot_case1
        } else {
            state.isSlotFree = false
            state.currentDescription = R.string.check_free_slot_case2
        }

    }

    override fun undoCommand(state: CurrentState) {
        state.isSlotFree = null
        state.currentDescription = state.prevDescription!!
    }

}