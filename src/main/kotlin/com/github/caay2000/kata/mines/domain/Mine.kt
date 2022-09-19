package com.github.caay2000.kata.mines.domain

data class Mine(val cells: List<Cell>, val exit: Position) {

    val navigableCells = cells.filter { it.wall.not() }

    data class Cell(val position: Position, val wall: Boolean) {

        constructor(x: Int, y: Int, wall: Boolean) : this(Position(x, y), wall)
    }
}
