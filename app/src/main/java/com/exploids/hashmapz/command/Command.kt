package com.exploids.hashmapz.command

interface Command {
    fun doCommand()
    fun undoCommand()
}