package com.github.caay2000.kata.mines.domain

data class Path(val path: List<Move>) {

    constructor(vararg move: Move) : this(listOf(*move))

    val size = path.size
    val isEmpty = path.isEmpty()
}
