package com.exploids.hashmapz.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.exploids.hashmapz.R
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
    var usedValue: String? = null,
    var usedKey: String? = null,
    var savedValue: String? = null,
    var savedKey: String? = null,
    var savedHashcode: Int? = null,
    var usedHashcode: Int? = null,
    var usedIndex: Int? = null,
    var isSlotFree: Boolean? = null,
    var isKeyEqual: Boolean? = null,
    var steps: Int,
    var currentDescription: Int = R.string.app_name,
    var prevCommands: Stack<Command> = Stack(),
    var nextCommands: ArrayDeque<Command> = ArrayDeque(),
    var actionHasFinished: Boolean? = null,
    var keyList: LinkedList<String?>,
    var hashcodeList: LinkedList<Int?>,
    var valueList: LinkedList<String?>,
    var savedKeyList: LinkedList<String?>? = null,
    var savedValueList: LinkedList<String?>? = null,
    var savedHashcodeList: LinkedList<Int?>? = null,
    var foundValue: String? = null,
    var keyNotFound: Boolean? = null,
    var probingMode: String = "Linear Probing",
    var prevDescription: Stack<Int> = Stack()
    )
