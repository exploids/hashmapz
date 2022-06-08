package com.exploids.hashmapz.model

import com.exploids.hashmapz.command.Command
import java.util.*
import kotlin.collections.ArrayDeque

data class CurrentState(var currentIndex: Int,
                        var currentCommand: Command,
                        var mapSize: Int,
                        var usedValue: Int,
                        var usedKey: String,
                        var usedHashcode: Int,
                        var currentDescription: String,
                        var prevCommands: Stack<Command>,
                        var nextCommands: ArrayDeque<Command>




    )
