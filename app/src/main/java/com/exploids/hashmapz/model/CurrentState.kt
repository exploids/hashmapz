package com.exploids.hashmapz.model

import com.exploids.hashmapz.command.Command
import java.util.*
import kotlin.collections.ArrayDeque

data class CurrentState(
    var currentIndex: Int? = null,
    var currentCommand: Command? = null,
    var mapSize: Int,
    var usedValue: Int? = null,
    var usedKey: String? = null,
    var usedHashcode: Int? = null,
    var usedIndex: Int? = null,
    var isSlotFree: Boolean? = null,
    var isKeyEqual: Boolean? = null,
    var steps: Int,
    var currentDescription: String? = null,
    var prevCommands: Stack<Command>,
    var nextCommands: ArrayDeque<Command>,
    var actionHasFinished: Boolean? = null,
    var keyList: LinkedList<String?>,
    var hashcodeList: LinkedList<Int?>,
    var valueList: LinkedList<Int?>
    )
