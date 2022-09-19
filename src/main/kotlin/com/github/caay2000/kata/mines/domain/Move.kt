package com.github.caay2000.kata.mines.domain

enum class Move {
    UP, DOWN, RIGHT, LEFT;

    fun moveFrom(position: Position): Position =
        when (this) {
            DOWN -> Position(position.x, position.y + 1)
            UP -> Position(position.x, position.y - 1)
            RIGHT -> Position(position.x + 1, position.y)
            LEFT -> Position(position.x - 1, position.y)
        }

    fun inverse(): Move =
        when (this) {
            UP -> DOWN
            DOWN -> UP
            RIGHT -> LEFT
            LEFT -> RIGHT
        }
}
