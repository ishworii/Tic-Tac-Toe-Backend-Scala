package com.ik.tictactoe

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.StatusCodes
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._



object Routes extends DefaultJsonProtocol {
    implicit val boardFormat: JsonFormat[Seq[Option[Char]]] = new JsonFormat[Seq[Option[Char]]] {
        def write(board: Seq[Option[Char]]): JsValue = {
            JsArray(board.map {
                case Some(char) => JsString(char.toString)
                case None => JsNull
            }.toVector)
        }

        def read(json: JsValue): Seq[Option[Char]] = {
            json match {
                case JsArray(elements) =>
                    elements.map {
                        case JsString(value) if value.nonEmpty => Some(value.charAt(0))
                        case JsNull => None
                        case _ => throw new DeserializationException("Invalid board element")
                    }
                case _ => throw new DeserializationException("Expected an array of board elements")
            }
        }
    }

    def routes: Route = cors() {
        path("findBestMove") {
            post {
                entity(as[JsValue]) { json =>
                    try {
//                        println(s"Raw JSON: ${json.prettyPrint}")
                        val boardJson = json.asJsObject.fields.getOrElse("board", throw new DeserializationException("Missing 'board' key"))

                        // Deserialize the `board` value
                        val board = boardJson.convertTo[Seq[Option[Char]]]
                        val boardArray = board.toArray
//                        println(boardArray.mkString("Array(", ", ", ")"))

                        // Compute the best move
                        val bestMove = MiniMax.findBestMove(boardArray)
                        complete(StatusCodes.OK, JsObject("bestMove" -> JsNumber(bestMove)))
                    } catch {
                        case ex: Exception =>
                            println(s"Error during deserialization: ${ex.getMessage}")
                            ex.printStackTrace()
                            complete(StatusCodes.InternalServerError, s"Failed to process request: ${ex.getMessage}")
                    }
                }
            }
        }

    }
}
