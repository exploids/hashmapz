package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

class CalculateIndexCommand : Command{
    override fun doCommand(state: CurrentState) {
        val key: String? = state.usedKey
        state.usedHashcode = key.hashCode()
        state.usedIndex = key.hashCode().mod(state.mapSize)
    }

    override fun undoCommand(state: CurrentState) {
        state.usedHashcode = null
        state.usedIndex = null
    }
}