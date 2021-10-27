package com.holland.vertX.pojo

import io.vertx.sqlclient.Row

class Log(val id: Int, val operate_user: String?) {

  companion object {
    val mapping: (Row) -> Log = {
      Log(it.getInteger("id"), it.getString("operate_user"))
    }
  }
}
