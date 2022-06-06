package com.exploids.hashmapz.model

import com.exploids.hashmapz.command.Command

data class CurrentState(var currentIndex: Int,
                        var currentCommand: Command,
                        var mapSize: Int,
                        var usedValue: Int,
                        var usedKey: String,
                        var usedHashcode: Int,


    )
