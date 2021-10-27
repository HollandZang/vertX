package com.holland.vertX.db

import io.vertx.core.Vertx
import io.vertx.mysqlclient.MySQLConnectOptions
import io.vertx.mysqlclient.MySQLPool
import io.vertx.sqlclient.PoolOptions


class MySqlPool(vertx: Vertx) {
  private val connectOptions = MySQLConnectOptions()
    .setPort(3306)
    .setHost("localhost")
    .setDatabase("spring-cloud_gateway")
    .setUser("root")
    .setPassword("root")

  // Pool options
  private val poolOptions = PoolOptions()
    .setMaxSize(5)

  // Create the pooled client
  val client = MySQLPool.pool(vertx, connectOptions, poolOptions)
}
