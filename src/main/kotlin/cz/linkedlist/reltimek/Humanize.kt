@file:JvmName("Humanize")
package cz.linkedlist.reltimek

import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.util.*

enum class Thresholds(val default: Long) {
  ss(44L), // a few seconds to seconds
  s(48L),  // seconds to minute
  m(45L),  // minutes to hour
  h(22L),  // hours to day
  d(26L),  // days to month
  M(11L),   // months to year
  y(Long.MAX_VALUE)
}

enum class RelativeTime(val single: String, val double: String) {
    SECONDS("s", "ss"),
    MINUTES("m", "mm"),
    HOURS("h", "hh"),
    DAYS("d", "dd"),
    MONTHS("M", "MM"),
    YEARS("y", "yy");
}

val registry = LocaleRegistry()

fun Duration.humanize(): String {
  return this.humanize(Locale.getDefault())
}

fun Duration.humanize(withSuffix: Boolean): String {
    return this.humanize(Locale.getDefault(), withSuffix)
}

fun Duration.humanize(locale: Locale): String {
    return this.humanize(locale, false)
}

fun Duration.humanize(locale: Locale, withSuffix: Boolean): String {
    val dur = this.abs()
    val inFuture = !this.isNegative
    val seconds = Pair(dur.seconds, RelativeTime.SECONDS)
    val minutes = Pair(dur.toMinutes(), RelativeTime.MINUTES)
    val hours = Pair(dur.toHours(), RelativeTime.HOURS)
    val days = Pair(dur.toDays(), RelativeTime.DAYS)
    val period = if(days.first > 0) daysToPeriod(days.first) else Period.ZERO
    val months = Pair(period.months.toLong(), RelativeTime.MONTHS)
    val years = Pair(period.years.toLong(), RelativeTime.YEARS)

    val (num, relTime) = arrayOf(years, months, days, hours, minutes, seconds).first { it.first != 0L }

    val payload = if(relTime == RelativeTime.SECONDS)
        determineSeconds(num)
    else
        determineOther(num, relTime)

    val finalPayload = payload.copy(withoutSuffix = !withSuffix, isFuture = inFuture)

    val humanizeFnc = registry.getForLocale(locale)

    return humanizeFnc.humanize(finalPayload)
}


data class Payload(
        val relTime: RelativeTime,
        val num: Long?,
        val double: Boolean = false,
        val withoutSuffix: Boolean = true,
        val isFuture: Boolean = false
) {
    constructor(relTime: RelativeTime) : this(relTime, null)
}

fun determineSeconds(num: Long): Payload {
    return when {
        num < Thresholds.ss.default -> Payload(RelativeTime.SECONDS)
        num < Thresholds.s.default -> Payload(RelativeTime.SECONDS, num, true)
        else -> Payload(RelativeTime.MINUTES)
    }
}

fun determineOther(num: Long, relTime: RelativeTime): Payload {
    return when {
        num == 1L -> Payload(relTime)
        num < Thresholds.valueOf(relTime.single).default -> Payload(relTime, num, true)
        else -> Payload(RelativeTime.values()[relTime.ordinal + 1])
    }
}

fun daysToPeriod(days: Long): Period {
    val now = LocalDate.now()
    return Period.between(now, now.plusDays(days))
}