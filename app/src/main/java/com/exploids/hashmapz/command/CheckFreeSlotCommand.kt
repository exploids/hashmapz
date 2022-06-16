package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState
import java.util.*

class CheckFreeSlotCommand : Command {

    override fun doCommand(state: CurrentState) {
        val keyList: LinkedList<String> = state.keyList
        if (keyList.get(state.currentIndex) == null) {
            state.isSlotFree = true
        } else {
            state.isSlotFree = false
        }
    }

    override fun undoCommand(state: CurrentState) {
        TODO("Not yet implemented")
    }

}