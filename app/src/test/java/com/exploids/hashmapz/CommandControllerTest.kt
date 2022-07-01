package com.exploids.hashmapz

import com.exploids.hashmapz.command.Command
import com.exploids.hashmapz.controller.CommandController
import com.exploids.hashmapz.model.CurrentState
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    val keys = LinkedList<String?>()
    keys.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null))
    val valueList = LinkedList<String?>()
    valueList.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null))
    val hashList = LinkedList<Int?>()
    hashList.addAll(listOf(null, null, null, null, null,null,null,null,null,null,null,null,null,null,null,null))
    val currentState: CurrentState = CurrentState(mapSize = 16, steps =  1, keyList = keys, hashcodeList = hashList, valueList = valueList)
    val commandController: CommandController = CommandController(currentState)
    val names = listOf<String>("Peter","Anna","Bob","Hannah","James","Nina","Horst","Luisa","Dieter","Laura","Eliot","Ida","Norbert","Greta")
    val id = listOf<String>("313","244","543","544","312","265","238","612","256","2341","547","111","444","123")
    for (n in 0..(names.size-1)) {
        commandController.add(names[n], id[n])
        while (!commandController.commandHasFinished()){
            commandController.nextCommand()
        }
    }

    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    commandController.prevCommand()
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
   /*
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))

    commandController.nextCommand()
    commandController.nextCommand()
    commandController.nextCommand()
    commandController.nextCommand()
    commandController.nextCommand()
    commandController.nextCommand()
    commandController.nextCommand()
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))

    */
    /*
    commandController.search("Niklas")
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))

    commandController.delete("Hans")
    commandController.nextCommand()
    commandController.nextCommand()
    commandController.nextCommand()
    commandController.nextCommand()
    commandController.prevCommand()
    println(Arrays.toString(currentState.nextCommands!!.toArray()))
    println(Arrays.toString(currentState.prevCommands!!.toArray()))
    println(Arrays.toString(currentState.keyList.toArray()))
    println(Arrays.toString(currentState.valueList.toArray()))
    println(Arrays.toString(currentState.hashcodeList.toArray()))
    */

}