package com.exploids.hashmapz.command

import com.exploids.hashmapz.model.CurrentState

interface Command {
    fun doCommand(state: CurrentState)
    fun undoCommand(state: CurrentState)
}