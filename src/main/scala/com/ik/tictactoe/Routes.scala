package com.ik.tictactoe

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._



object Routes extends DefaultJsonProtocol {
    implicit val boardFormat: RootJsonFormat[BoardRequest] = jsonFormat2(BoardRequest)

    def routes: Route = cors() {
        path("findBestMove") {
                post {
                    entity(as[BoardRequest]) { request =>
                        try {
                            val boardArray = request.board.toArray
                            val cellSize = request.cellSize

                            // Validate the board size
                            if (boardArray.length != cellSize * cellSize) {
                                complete(
                                    StatusCodes.BadRequest,
                                    s"Invalid board size. Expected ${cellSize * cellSize} cells but got ${boardArray.length}."
                                )
                            } else {
                                // Compute the best move
                                val bestMove = MiniMax.findBestMove(boardArray,cellSize)
                                complete(StatusCodes.OK, JsObject("bestMove" -> JsNumber(bestMove)))
                            }
                        } catch {
                            case ex: Exception =>
                                complete(StatusCodes.InternalServerError, s"Error: ${ex.getMessage}")
                        }
                    }
                }
        }

    }
}

case class BoardRequest(cellSize: Int, board: Seq[Option[Char]])
