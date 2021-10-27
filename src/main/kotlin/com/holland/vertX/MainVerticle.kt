package com.holland.vertX

import com.holland.vertX.db.MySqlPool
import com.holland.vertX.pojo.Log
import com.holland.vertX.router.common.CORSRoute
import com.holland.vertX.router.common.IndexRoute
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Launcher
import io.vertx.core.Promise
import io.vertx.core.impl.logging.LoggerFactory
import io.vertx.core.json.Json
import io.vertx.ext.web.Router

@Suppress("SpellCheckingInspection", "SqlNoDataSourceInspection", "SqlResolve")
class MainVerticle : AbstractVerticle() {

  override fun start(startPromise: Promise<Void>) {
    val logger = LoggerFactory.getLogger(this.javaClass)

    println("server  start...")
    val port = 8080
    val mySqlPool = MySqlPool(vertx)

    vertx
      .apply {
        val router = Router.router(this)
          .apply { CORSRoute().start(this) }
          .apply { IndexRoute().start(this) }
          .apply {
            route("/test").respond { ctx ->
              val response = ctx.response()
              response.putHeader("Content-Type", "application/json")

              mySqlPool.client
                .query("select * from log")
                .mapping(Log.mapping)
                .execute()
                .onSuccess { list ->
                  Future.succeededFuture(list)
                }
                .onFailure {
                  logger.error(it)
                  response.statusCode = 500
                  response.end(Json.encodePrettily("server error"))
                }
            }
          }
        createHttpServer().requestHandler(router).listen(port)
        println("server is running... port = $port")
      }
  }
}

fun main() {
  Launcher().dispatch(arrayOf("run", MainVerticle::class.java.name))
}
