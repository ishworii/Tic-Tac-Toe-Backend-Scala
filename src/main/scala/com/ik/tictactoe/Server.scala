package com.ik.tictactoe

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Server extends App{
    implicit val system : ActorSystem = ActorSystem("TicTacToeSystem")
    implicit val materializer : Materializer = Materializer(system)
    implicit val executionContext : ExecutionContextExecutor = system.dispatcher
    private val bindingFuture = Http().newServerAt("localhost",9000).bind(Routes.routes)

    println("Server online at http://localhost:9000/\nPress RETURN to stop...")

    StdIn.readLine()
    bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
}