package com.ik.tictactoe

object MiniMax {
    private val playerX = 'X'
    private val playerO = 'O'

    def findBestMove(board : Array[Option[Char]]) : Int = {
        var bestMove = -1
        var bestValue = Int.MinValue

        for (i <- board.indices if board(i).isEmpty){
            board(i) = Some(playerO)
            val moveValue = minimax(board,0,isMaximizing=false)
            board(i) = None

            if(moveValue > bestValue){
                bestMove = i
                bestValue = moveValue
            }
        }
        bestMove
    }

    private def minimax(board:Array[Option[Char]],depth:Int,isMaximizing:Boolean) : Int = {
        val score = evaluate(board)
        if (score != 0) return score
        if (board.forall(_.isDefined)) return 0

        if(isMaximizing){
            var maxEval = Int.MinValue
            for(i <- board.indices if board(i).isEmpty){
                board(i) = Some(playerO)
                maxEval = math.max(maxEval,minimax(board,depth+1,isMaximizing=false))
                board(i) = None
            }
            maxEval
        }
        else{
            var minEval = Int.MaxValue
            for(i <- board.indices if board(i).isEmpty){
                board(i) = Some(playerX)
                minEval = math.min(minEval,minimax(board,depth+1,isMaximizing=true))
                board(i) = None
            }
            minEval
        }
    }
    private def evaluate(board:Array[Option[Char]]) : Int = {
        val winningCombinations = Array(
            Array(0, 1, 2), Array(3, 4, 5), Array(6, 7, 8),
            Array(0, 3, 6), Array(1, 4, 7), Array(2, 5, 8),
            Array(0, 4, 8), Array(2, 4, 6)
        )
        winningCombinations.collectFirst {
            case combo if combo.map(board).distinct.toSeq == Seq(Some(playerO)) => 10
            case combo if combo.map(board).distinct.toSeq == Seq(Some(playerX)) => -10
        }.getOrElse(0)
    }
}