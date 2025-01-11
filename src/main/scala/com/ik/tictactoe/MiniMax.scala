package com.ik.tictactoe
object MiniMax {

    // Entry point for finding the best move
    def findBestMove(board: Array[Option[Char]], cellSize: Int): Int = {
        val emptyCells = board.zipWithIndex.collect { case (None, index) => index }
        var bestScore = Int.MinValue
        var bestMove = -1

        for (cell <- emptyCells) {
            // Simulate AI's move
            board(cell) = Some('O')
            val score = minimax(board, cellSize, depth = 0, isMaximizing = false)
            board(cell) = None // Reset the board

            // Pick the move with the highest score
            if (score > bestScore) {
                bestScore = score
                bestMove = cell
            }
        }

        bestMove
    }

    private def minimax(board: Array[Option[Char]], cellSize: Int, depth: Int, isMaximizing: Boolean): Int = {
        val emptyCells = board.zipWithIndex.collect { case (None, index) => index }
        val winningCombinations = generateWinningCombinations(cellSize)

        // Base case: Evaluate the board
        val score = evaluate(board, winningCombinations)
        if (score == 10 || score == -10 || emptyCells.isEmpty) {
            return score - depth // Subtract depth to prefer quicker wins
        }

        if (isMaximizing) {
            var bestScore = Int.MinValue
            for (cell <- emptyCells) {
                board(cell) = Some('O') // Simulate AI's move
                bestScore = math.max(bestScore, minimax(board, cellSize, depth + 1, isMaximizing = false))
                board(cell) = None // Reset the board
            }
            bestScore
        } else {
            var bestScore = Int.MaxValue
            for (cell <- emptyCells) {
                board(cell) = Some('X') // Simulate opponent's move
                bestScore = math.min(bestScore, minimax(board, cellSize, depth + 1, isMaximizing = true))
                board(cell) = None // Reset the board
            }
            bestScore
        }
    }

    // Evaluate the board for a score
    private def evaluate(board: Array[Option[Char]], winningCombinations: Seq[Seq[Int]]): Int = {
        for (combo <- winningCombinations) {
            val values = combo.map(board)
            if (values.forall(_ == Some('O'))) return 10 // AI wins
            if (values.forall(_ == Some('X'))) return -10 // Opponent wins
        }
        0 // No winner
    }

    // Generate winning combinations dynamically
    private def generateWinningCombinations(cellSize: Int): Seq[Seq[Int]] = {
        val rows = (0 until cellSize).map(row => (0 until cellSize).map(col => row * cellSize + col))
        val cols = (0 until cellSize).map(col => (0 until cellSize).map(row => row * cellSize + col))
        val diagonal1 = (0 until cellSize).map(i => i * (cellSize + 1))
        val diagonal2 = (0 until cellSize).map(i => (i + 1) * (cellSize - 1))

        (rows ++ cols :+ diagonal1 :+ diagonal2).map(_.toSeq)
    }
}
