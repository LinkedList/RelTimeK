@file:JvmName("Humanize")
package cz.linkedlist.reltimek

import java.time.DateTimeException
import java.time.Duration
import java.time.Period
import java.util.*

enum class Thresholds(val default: Long) {
  ss(44L), // a few seconds to seconds
  s(48L),  // seconds to minute
  m(45L),  // minutes to hour
  h(22L),  // hours to day
  d(26L),  // days to month
  M(11L)   // months to year
}

enum class RelativeTime(val single: String, val double: String) {
    SECONDS("s", "ss"),
    MINUTES("m", "MM"),
    HOURS("h", "hh"),
    DAYS("d", "dd"),
    MONTHS("M", "MM"),
    YEARS("y", "yy");
}

val registry = LocaleRegistry()

fun Duration.humanize(): String {
  return this.humanize(Locale.getDefault())
}

fun Duration.humanize(locale: Locale): String {
    val dur = this.abs()
    val period = try {
        Period.from(dur)
    } catch (o_O: DateTimeException) {
        Period.ZERO
    }
    val seconds = Pair(dur.seconds, RelativeTime.SECONDS)
    val minutes = Pair(dur.toMinutes(), RelativeTime.MINUTES)
    val hours = Pair(dur.toHours(), RelativeTime.HOURS)
    val days = Pair(dur.toDays(), RelativeTime.DAYS)
    val months = Pair(period.months.toLong(), RelativeTime.MONTHS)
    val years = Pair(period.years.toLong(), RelativeTime.YEARS)

    val (num, relTime) = arrayOf(years, months, days, hours, minutes, seconds).first { it.first != 0L }

    val payload = if(relTime == RelativeTime.SECONDS)
        determineSeconds(num)
    else
        determineOther(num, relTime)

    val humanizeFnc = registry.getForLocale(locale)

    return when(humanizeFnc) {
        is SimpleFnc -> {
            humanizeFnc.humanize(payload)
        }
        else -> TODO()
    }
}

class SimplePayload(val relTime: RelativeTime, val num: Long?, val double: Boolean = false) {
    constructor(relTime: RelativeTime) : this(relTime, null)
}

fun determineSeconds(num: Long): SimplePayload{
    return when {
        num < Thresholds.ss.default -> SimplePayload(RelativeTime.SECONDS)
        num < Thresholds.s.default -> SimplePayload(RelativeTime.SECONDS, num, true)
        else -> SimplePayload(RelativeTime.MINUTES)
    }
}

fun determineOther(num: Long, relTime: RelativeTime): SimplePayload {
    return when {
        num == 1L -> SimplePayload(relTime)
        num < Thresholds.valueOf(relTime.single).default -> SimplePayload(relTime, num, true)
        else -> SimplePayload(RelativeTime.values()[relTime.ordinal + 1])
    }
}