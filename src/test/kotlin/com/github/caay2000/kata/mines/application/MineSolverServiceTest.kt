package com.github.caay2000.kata.mines.application

import com.github.caay2000.kata.mines.domain.Mine
import com.github.caay2000.kata.mines.domain.Mine.Cell
import com.github.caay2000.kata.mines.domain.Miner
import com.github.caay2000.kata.mines.domain.Move
import com.github.caay2000.kata.mines.domain.Move.DOWN
import com.github.caay2000.kata.mines.domain.Move.LEFT
import com.github.caay2000.kata.mines.domain.Move.RIGHT
import com.github.caay2000.kata.mines.domain.Move.UP
import com.github.caay2000.kata.mines.domain.Path
import com.github.caay2000.kata.mines.domain.Position
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class MineSolverServiceTest {

    private val sut = MineSolverService()

    @ParameterizedTest
    @EnumSource(value = Move::class)
    fun `solves a simple mine with just one movement`(move: Move) {

        val start = Position(0, 0)
        val exit = move.moveFrom(start)

        val mine = Mine(
            cells = listOf(Cell(start, false), Cell(exit, false)),
            exit = exit
        )
        val miner = Miner(start)

        val result = sut.solve(mine, miner)

        assertThat(result).isEqualTo(Path(move))
    }

    @ParameterizedTest
    @EnumSource(value = Move::class)
    fun `solves a simple mine with just two movements`(move: Move) {

        val start = Position(0, 0)
        val next = move.moveFrom(start)
        val exit = move.moveFrom(next)

        val mine = Mine(
            cells = listOf(Cell(start, false), Cell(next, false), Cell(exit, false)),
            exit = exit
        )
        val miner = Miner(start)

        val result = sut.solve(mine, miner)

        assertThat(result).isEqualTo(Path(move, move))
    }

    @Test
    fun `solves a one path mine`() {

//        S.XE.
//        X.XX.
//        ..XX.
//        .XXX.
//        .....

        val mine = Mine(
            cells = listOf(
                Cell(0, 0, false), Cell(1, 0, false), Cell(2, 0, true), Cell(3, 0, false), Cell(4, 0, false),
                Cell(0, 1, true), Cell(1, 1, false), Cell(2, 1, true), Cell(3, 1, true), Cell(4, 1, false),
                Cell(0, 2, false), Cell(1, 2, false), Cell(2, 2, true), Cell(3, 2, true), Cell(4, 2, false),
                Cell(0, 3, false), Cell(1, 3, true), Cell(2, 3, true), Cell(3, 3, true), Cell(4, 3, false),
                Cell(0, 4, false), Cell(1, 4, false), Cell(2, 4, false), Cell(3, 4, false), Cell(4, 4, false)
            ),
            exit = Position(3, 0)
        )
        val miner = Miner(Position(0, 0))

        val result = sut.solve(mine, miner)

        assertThat(result)
            .isEqualTo(Path(RIGHT, DOWN, DOWN, LEFT, DOWN, DOWN, RIGHT, RIGHT, RIGHT, RIGHT, UP, UP, UP, UP, LEFT))
    }

    @Test
    fun `solves a complex mine`() {

//        S...E
//        .XX.X
//        XXX..
//        XXXXX
//        XXXXX

        val mine = Mine(
            cells = listOf(
                Cell(0, 0, false), Cell(1, 0, false), Cell(2, 0, false), Cell(3, 0, false), Cell(4, 0, false),
                Cell(0, 1, false), Cell(1, 1, true), Cell(2, 1, true), Cell(3, 1, false), Cell(4, 1, true),
                Cell(0, 2, true), Cell(1, 2, true), Cell(2, 2, true), Cell(3, 2, false), Cell(4, 2, false),
                Cell(0, 3, false), Cell(1, 3, false), Cell(2, 3, true), Cell(3, 3, true), Cell(4, 3, true),
                Cell(0, 4, false), Cell(1, 4, false), Cell(2, 4, true), Cell(3, 4, false), Cell(4, 4, false)
            ),
            exit = Position(4, 0)
        )
        val miner = Miner(Position(0, 0))

        val result = sut.solve(mine, miner)

        assertThat(result).isEqualTo(Path(listOf(RIGHT, RIGHT, RIGHT, RIGHT)))
    }

    @Test
    fun `impossible to solve`() {

        val mine = Mine(
            cells = listOf(
                Cell(0, 0, false), Cell(1, 0, true), Cell(2, 0, false),
                Cell(0, 1, false), Cell(1, 1, true), Cell(2, 1, true),
            ),
            exit = Position(2, 0)
        )
        val miner = Miner(Position(0, 0))

        val result = sut.solve(mine, miner)

        assertThat(result).isEqualTo(Path(emptyList()))
    }
}
