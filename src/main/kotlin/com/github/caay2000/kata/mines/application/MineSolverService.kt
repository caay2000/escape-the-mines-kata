package com.github.caay2000.kata.mines.application

import com.github.caay2000.kata.mines.domain.Mine
import com.github.caay2000.kata.mines.domain.Miner
import com.github.caay2000.kata.mines.domain.Path
import com.github.caay2000.kata.mines.domain.Position

class MineSolverService {

    fun solve(mine: Mine, miner: Miner): Path = recursiveSolve(mine, miner, invalidPosition).path

    private tailrec fun recursiveSolve(mine: Mine, miner: Miner, lastPosition: Position): Miner =
        when {
            miner.isNotLost -> miner
            miner.didntMove(lastPosition) -> recursiveSolve(mine, miner.moveBack(), miner.position)
            else -> recursiveSolve(mine, miner.move(mine), miner.position)
        }

    private val invalidPosition = Position(-1, -1)

    private fun Miner.didntMove(lastPosition: Position) = position == lastPosition
}
