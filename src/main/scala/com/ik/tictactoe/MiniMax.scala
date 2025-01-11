package com.ik.tictactoe

import scala.collection.mutable

object MiniMax {

    private val cachedCombinations = mutable.Map[Int, Seq[Seq[Int]]]()

    private def generateWinningCombinations(cellSize: Int): Seq[Seq[Int]] = {
        cachedCombinations.getOrElseUpdate(cellSize, {
            val rows = (0 until cellSize).map(row => (0 until cellSize).map(col => row * cellSize + col))
            val cols = (0 until cellSize).map(col => (0 until cellSize).map(row => row * cellSize + col))
            val diagonal1 = (0 until cellSize).map(i => i * (cellSize + 1))
            val diagonal2 = (0 until cellSize).map(i => (i + 1) * (cellSize - 1))

            (rows ++ cols :+ diagonal1 :+ diagonal2).map(_.toSeq)
        })
    }

    def findBestMove(board: Array[Option[Char]], cellSize: Int): Int = {
        val emptyCells = board.zipWithIndex.collect { case (None, index) => index }
        var bestScore = Int.MinValue
        var bestMove = -1
        val winningCombinations = generateWinningCombinations(cellSize)

        for (cell <- emptyCells) {
            // Simulate AI's move
            board(cell) = Some('O')
            val score = minimax(board, cellSize, depth = 0, isMaximizing = false, alpha = Int.MinValue, beta = Int.MaxValue, winningCombinations)
            board(cell) = None // Reset the board

            // Update the best move if the score is better
            if (score > bestScore) {
                bestScore = score
                bestMove = cell
            }
        }

        bestMove
    }

    private def minimax(board: Array[Option[Char]], cellSize: Int, depth: Int, isMaximizing: Boolean, alpha: Int, beta: Int, winningCombinations: Seq[Seq[Int]]): Int = {
        val emptyCells = board.zipWithIndex.collect { case (None, index) => index }

        // Base case: Evaluate the board
        val score = evaluate(board, winningCombinations)
        if (score == 10 || score == -10 || emptyCells.isEmpty || depth >= 7) {
            return if (depth >= 7) heuristicEvaluate(board, cellSize) else score - depth // Use heuristic evaluation at depth limit
        }

        var localAlpha = alpha
        var localBeta = beta

        if (isMaximizing) {
            var bestScore = Int.MinValue
            for (cell <- emptyCells) {
                board(cell) = Some('O') // Simulate AI move
                bestScore = math.max(bestScore, minimax(board, cellSize, depth + 1, isMaximizing = false, localAlpha, localBeta, winningCombinations))
                board(cell) = None // Reset the board
                localAlpha = math.max(localAlpha, bestScore)
                if (localBeta <= localAlpha) {
                    // Prune the branch
                    return bestScore
                }
            }
            bestScore
        } else {
            var bestScore = Int.MaxValue
            for (cell <- emptyCells) {
                board(cell) = Some('X') // Simulate opponent's move
                bestScore = math.min(bestScore, minimax(board, cellSize, depth + 1, isMaximizing = true, localAlpha, localBeta, winningCombinations))
                board(cell) = None // Reset the board
                localBeta = math.min(localBeta, bestScore)
                if (localBeta <= localAlpha) {
                    // Prune the branch
                    return bestScore
                }
            }
            bestScore
        }
    }

    private def heuristicEvaluate(board: Array[Option[Char]], cellSize: Int): Int = {
        val center = cellSize / 2
        val winningCombinations = generateWinningCombinations(cellSize)
        var score = 0

        for (combo <- winningCombinations) {
            val values = combo.map(board)
            val oCount = values.count(_.contains('O'))
            val xCount = values.count(_.contains('X'))

            if (xCount == 0) score += oCount * oCount // Prioritize lines with only AI pieces
            if (oCount == 0) score -= xCount * xCount // Penalize lines with only opponent pieces
        }

        // Slightly favor central positions for better control
        for (i <- board.indices) {
            if (board(i).contains('O')) {
                val row = i / cellSize
                val col = i % cellSize
                val centerProximity = math.abs(center - row) + math.abs(center - col)
                score += cellSize - centerProximity
            }
        }

        score
    }

    // Evaluate the board for a win or loss
    private def evaluate(board: Array[Option[Char]], winningCombinations: Seq[Seq[Int]]): Int = {
        for (combo <- winningCombinations) {
            val values = combo.map(board)
            if (values.forall(_.contains('O'))) return 10 // AI wins
            if (values.forall(_.contains('X'))) return -10 // Opponent wins
        }
        0 // No winner
    }

}
