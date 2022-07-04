package com.exploids.hashmapz.command



import android.provider.Settings.System.getString
import androidx.annotation.StringRes
import com.exploids.hashmapz.R
import com.exploids.hashmapz.model.CurrentState

class CalculateIndexCommand : Command{
    override fun doCommand(state: CurrentState) {
        val key: String? = state.usedKey
        state.usedHashcode = key.hashCode()
        state.usedIndex = key.hashCode().mod(state.mapSize)
        state.prevDescription = state.currentDescription
        state.currentDescription = R.string.calculate_index_description
    }

    override fun undoCommand(state: CurrentState) {
        state.usedHashcode = null
        state.usedIndex = null
        state.currentDescription = state.prevDescription!!
    }
}