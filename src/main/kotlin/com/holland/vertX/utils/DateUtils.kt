package com.holland.vertX.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {

  @JvmStatic
  @JvmName("LocalDateTime")
  fun LocalDateTime.getString(): String {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this)
  }
}
