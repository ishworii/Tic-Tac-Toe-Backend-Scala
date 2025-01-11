package com.ik.tictactoe

import org.scalatest.funsuite.AnyFunSuite

class MiniMaxTest extends AnyFunSuite {

    test("AI finds the winning move in a 3x3 grid") {
        val board = Array(
            Some('O'), Some('O'), None,
            Some('X'), Some('X'), None,
            None, None, None
        )
        val bestMove = MiniMax.findBestMove(board,3)  // Assuming default 3x3
        assert(bestMove == 2) // AI wins by completing the first row
    }

    test("AI blocks opponent's winning move in a 3x3 grid") {
        val board = Array(
            Some('X'), Some('X'), None,
            None, Some('O'), None,
            None, None, None
        )
        val bestMove = MiniMax.findBestMove(board,3)
        assert(bestMove == 2) // AI blocks the opponent's win
    }


    test("AI plays optimally in a 4x4 grid") {
        val board = Array(
            Some('O'), Some('O'), None, None,
            Some('X'), None, None, Some('X'),
            None, Some('O'), None, None,
            None, None, None, None
        )
        val bestMove = MiniMax.findBestMove(board,4)
        assert(bestMove == 6) // AI plays to maximize its position
    }

    test("AI handles a full grid (3x3 grid, draw)") {
        val board: Array[Option[Char]] = Array(
            Some('O'), Some('X'), Some('O'),
            Some('X'), Some('X'), Some('O'),
            Some('O'), Some('O'), Some('X')
        )
        val bestMove = MiniMax.findBestMove(board,3)
        assert(bestMove == -1) // No moves left, return -1
    }

    test("AI handles depth limit in a 4x4 grid") {
        val board = Array(
            Some('O'), Some('O'), None, None,
            None, Some('X'), None, Some('X'),
            None, None, Some('O'), None,
            None, None, None, None
        )
        val bestMove = MiniMax.findBestMove(board,4)
        assert(bestMove >= 0 && bestMove < board.length) // Ensure a valid move is returned
    }

    test("AI evaluates an empty 4x4 grid optimally") {
        val board = Array.fill[Option[Char]](16)(None) // Empty 4x4 board
        val bestMove = MiniMax.findBestMove(board,4)
        // Center positions in 4x4 are indices 5,6,9,10
        assert(Set(5, 6, 9, 10).contains(bestMove))
    }

    test("AI detects a win in a diagonal for 4x4 grid") {
        val board = Array(
            Some('O'), Some('X'), Some('X'), None,
            Some('X'), Some('O'), Some('X'), None,
            Some('X'), Some('O'), Some('O'), None,
            Some('O'), None, None, None
        )
        val bestMove = MiniMax.findBestMove(board,4)
        assert(bestMove == 15) // AI wins by completing the diagonal
    }

}