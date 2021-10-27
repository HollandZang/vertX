package com.holland.vertX.router.common

import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.handler.StaticHandler


class CORSRoute {
  @Throws(Exception::class)
  fun start(router: Router) {
    val allowedHeaders: MutableSet<String> = HashSet()
    allowedHeaders.add("x-requested-with")
    allowedHeaders.add("Access-Control-Allow-Origin")
    allowedHeaders.add("origin")
    allowedHeaders.add("Content-Type")
    allowedHeaders.add("accept")
    allowedHeaders.add("X-PINGARUNER")
    val allowedMethods: MutableSet<HttpMethod> = HashSet()
    allowedMethods.add(HttpMethod.GET)
    allowedMethods.add(HttpMethod.POST)
    allowedMethods.add(HttpMethod.OPTIONS)
    /*
     * these methods aren't necessary for this sample,
     * but you may need them for your projects
     */allowedMethods.add(HttpMethod.DELETE)
    allowedMethods.add(HttpMethod.PATCH)
    allowedMethods.add(HttpMethod.PUT)
    router.route().order(-888).handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods))
    router["/access-control-with-get"].handler { handle(it) }
    router.post("/access-control-with-post-preflight").handler { handle(it) }

    // Serve the static resources
    router.route().order(-888).handler(StaticHandler.create())
  }

  private fun handle(ctx: RoutingContext) {
    val httpServerResponse = ctx.response()
    httpServerResponse.isChunked = true
    val headers = ctx.request().headers()
    for (key in headers.names()) {
      httpServerResponse.write("$key: ")
      httpServerResponse.write(headers[key])
      httpServerResponse.write("<br>")
    }
    httpServerResponse.putHeader("Content-Type", "application/text").end("Success")
  }
}
