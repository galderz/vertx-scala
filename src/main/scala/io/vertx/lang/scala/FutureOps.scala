package io.vertx.lang.scala

import io.vertx.core.AsyncResult
import io.vertx.core.Handler

import scala.concurrent.{Future, Promise}

object FutureOps {

  def future[T](func: Promise[T] => Unit): Future[T] = {
    val promise = Promise[T]()
    func(promise)
    promise.future
  }

  implicit def promiseToHandlerAR[T](promise: => Promise[T]): Handler[AsyncResult[T]] =
    new Handler[AsyncResult[T]]() {
      override def handle(event: AsyncResult[T]) = {
        if (event.succeeded()) promise.success(event.result())
        else promise.failure(event.cause())
      }
    }

  private def castingPromiseToHandlerAR[S, J](promise: => Promise[S]): Handler[AsyncResult[J]] =
    new Handler[AsyncResult[J]]() {
      override def handle(event: AsyncResult[J]) = {
        if (event.succeeded()) promise.success(event.result().asInstanceOf[S])
        else promise.failure(event.cause())
      }
    }

  implicit def promiseToVoidHandlerAR[T](p: => Promise[Unit]): Handler[AsyncResult[Void]] =
    castingPromiseToHandlerAR[Unit, Void](p)

  implicit def promiseToBooleanHandlerAR[T](p: => Promise[Boolean]): Handler[AsyncResult[java.lang.Boolean]] =
    castingPromiseToHandlerAR[Boolean, java.lang.Boolean](p)

  implicit def promiseToLongHandlerAR[T](p: => Promise[Long]): Handler[AsyncResult[java.lang.Long]] =
    castingPromiseToHandlerAR[Long, java.lang.Long](p)

  implicit def promiseToByteHandlerAR[T](p: => Promise[Byte]): Handler[AsyncResult[java.lang.Byte]] =
    castingPromiseToHandlerAR[Byte, java.lang.Byte](p)

  implicit def promiseToShortHandlerAR[T](p: => Promise[Short]): Handler[AsyncResult[java.lang.Short]] =
    castingPromiseToHandlerAR[Short, java.lang.Short](p)

  implicit def promiseToIntegerHandlerAR[T](p: => Promise[Int]): Handler[AsyncResult[java.lang.Integer]] =
    castingPromiseToHandlerAR[Int, java.lang.Integer](p)

  implicit def promiseToFloatHandlerAR[T](p: => Promise[Float]): Handler[AsyncResult[java.lang.Float]] =
    castingPromiseToHandlerAR[Float, java.lang.Float](p)

  implicit def promiseToDoubleHandlerAR[T](p: => Promise[Double]): Handler[AsyncResult[java.lang.Double]] =
    castingPromiseToHandlerAR[Double, java.lang.Double](p)

  implicit def promiseToCharacterHandlerAR[T](p: => Promise[Char]): Handler[AsyncResult[java.lang.Character]] =
    castingPromiseToHandlerAR[Char, java.lang.Character](p)

}
