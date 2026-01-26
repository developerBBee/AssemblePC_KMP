package jp.developer.bbee.assemblepc.shared.common

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Clock
import kotlin.time.Instant

private const val TOKYO_TZ = "Asia/Tokyo"

val tokyoTz: TimeZone = TimeZone.of(TOKYO_TZ)

fun now(): Instant = Clock.System.now()

fun String.toTokyoInstant(): Instant {
    return runCatching {
        val ldt = LocalDateTime.parse(this)
        ldt.toInstant(tokyoTz)
    }.getOrElse {
        Instant.parse(this)
    }
}
