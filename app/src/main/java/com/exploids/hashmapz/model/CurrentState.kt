package com.exploids.hashmapz.model

import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.model.Actions.NONE
import java.util.*
import kotlin.collections.ArrayDeque

data class CurrentState(
    var currentIndex: Int? = null,
    var currentAction: Actions = NONE,
    var currentCommand: Command? = null,
    var mapSize: Int,
    var loadFactor: Float = 0.75F,
    var usedValue: Int? = null,
    var usedKey: String? = null,
    var savedValue: Int? = null,
    var savedKey: String? = null,
    var savedHashcode: Int? = null,
    var usedHashcode: Int? = null,
    var usedIndex: Int? = null,
    var isSlotFree: Boolean? = null,
    var isKeyEqual: Boolean? = null,
    var steps: Int,
    var currentDescription: String? = null,
    var prevCommands: Stack<Command>? = null,
    var nextCommands: ArrayDeque<Command>? = null,
    var actionHasFinished: Boolean? = null,
    var keyList: LinkedList<String?>,
    var hashcodeList: LinkedList<Int?>,
    var valueList: LinkedList<Int?>,
    var savedKeyList: LinkedList<String?>? = null,
    var savedValueList: LinkedList<Int?>? = null,
    var savedHashcodeList: LinkedList<Int?>? = null,
    var foundValue: Int? = null,
    var keyNotFound: Boolean? = null

    )
