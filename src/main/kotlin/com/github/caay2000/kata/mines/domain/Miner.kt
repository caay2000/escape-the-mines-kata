package com.github.caay2000.kata.mines.domain

data class Miner(
    val position: Position,
    val status: Status = Status.LOST,
    val visitedCells: List<Position> = listOf(position),
    val path: Path = Path(emptyList())
) {

    val isNotLost = status != Status.LOST

    fun move(mine: Mine): Miner {
        Move.values().forEach {
            val newPosition = it.moveFrom(position)
            if (newPosition.isValid(mine)) {
                println("moving to $newPosition[$it]")
                return Miner(
                    position = newPosition,
                    status = calculateNewStatus(newPosition, mine),
                    visitedCells = visitedCells + newPosition,
                    path = Path(path.path + it)
                )
            }
        }
        return this
    }

    fun moveBack(): Miner =
        if (path.isEmpty) trapped()
        else {
            println("moving back to ${goBackOnePosition()}")
            copy(position = goBackOnePosition(), path = removeLastPathMovement())
        }

    private fun calculateNewStatus(newPosition: Position, mine: Mine): Status =
        if (newPosition == mine.exit) Status.ESCAPED else Status.LOST

    private fun trapped() = copy(status = Status.TRAPPED)
    private fun removeLastPathMovement() = Path(path.path.take(path.path.size - 1))
    private fun goBackOnePosition() = path.path.last().inverse().moveFrom(position)

    private fun Position.isValid(mine: Mine): Boolean =
        notVisitedPosition() && navigablePosition(mine)

    private fun Position.navigablePosition(mine: Mine) =
        mine.navigableCells.any { it.position == this }

    private fun Position.notVisitedPosition() = visitedCells.none { it == this }

    enum class Status {
        LOST,
        TRAPPED,
        ESCAPED
    }
}
