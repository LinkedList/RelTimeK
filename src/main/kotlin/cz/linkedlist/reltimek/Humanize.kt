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

    val a = if(relTime == RelativeTime.SECONDS)
        determineSeconds(num)
    else
        determineOther(num, relTime)

    val humanizeFnc = registry.getForLocale(locale)

    return if(a.size > 1) {
        val str = humanizeFnc.map()[a[0]]!!
        str.replace("%d", a[1].toString(), true)
    } else {
        humanizeFnc.map()[a[0]]!!
    }
}

fun determineSeconds(num: Long): List<Any> {
    return when {
        num < Thresholds.ss.default -> listOf(RelativeTime.SECONDS.single)
        num < Thresholds.s.default -> listOf(RelativeTime.SECONDS.double, num)
        else -> listOf(RelativeTime.MINUTES.single)
    }
}

fun determineOther(num: Long, relTime: RelativeTime): List<Any> {
    return when {
        num == 1L -> listOf(relTime.single)
        num < Thresholds.valueOf(relTime.single).default -> listOf(relTime.double, num)
        else -> listOf(RelativeTime.values()[relTime.ordinal + 1].single)
    }
}