package com.holland.vertX.router.common

import com.holland.vertX.utils.DateUtils.getString
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import java.time.LocalDateTime

class IndexRoute {
  @Throws(Exception::class)
  fun start(router: Router) {
    router.route("/")
      .order(-777)
      .respond {
        Future.succeededFuture(JsonObject()
          .apply {
            put("desc", "this is a vertX demo")
            put("nowDate", LocalDateTime.now().getString())
          })
      }
  }
}
