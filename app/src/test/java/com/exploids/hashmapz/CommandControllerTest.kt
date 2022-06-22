package com.exploids.hashmapz

import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    val keys = LinkedList<String?>()
    keys.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,"Hans",null))
    val valueList = LinkedList<Int?>()
    valueList.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,123,444))
    val hashList = LinkedList<Int?>()
    hashList.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null))
    val currentState: CurrentState = CurrentState(mapSize = 16, steps =  1, prevCommands = Stack<Command>(), nextCommands = ArrayDeque<Command>(), keyList = keys, hashcodeList = hashList, valueList = valueList)
    val commandController: CommandController = CommandController(currentState)
    commandController.add("Niklas",837837)
    println(Arrays.toString(currentState.nextCommands.toArray()))
    println(Arrays.toString(currentState.prevCommands.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands.toArray()))
    println(Arrays.toString(currentState.prevCommands.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands.toArray()))
    println(Arrays.toString(currentState.prevCommands.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands.toArray()))
    println(Arrays.toString(currentState.prevCommands.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))

    commandController.nextCommand()
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands.toArray()))
    println(Arrays.toString(currentState.prevCommands.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))





}