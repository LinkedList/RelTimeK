@file:JvmName("Humanize")
package cz.linkedlist.reltimek

import java.time.DateTimeException
import java.time.Duration
import java.time.Period
import java.util.*

enum class Thresholds(val default: Int) {
  ss(44), // a few seconds to seconds
  s(45),  // seconds to minute
  m(45),  // minutes to hour
  h(22),  // hours to day
  d(26),  // days to month
  M(11)   // months to year
}

enum class RelativeTime {
  s, ss, m, mm, h, hh, d, dd, M, MM, y, yy
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
    val seconds = dur.seconds
    val minutes = dur.toMinutes()
    val hours = dur.toHours()
    val days = dur.toDays()
    val months = period.months
    val years = period.years

    val a =
            if(years > 1) {
                listOf(RelativeTime.yy, years)
            } else if(years == 1) {
                listOf(RelativeTime.y)
            } else if (months > 0 && months < Thresholds.M.default) {
                listOf(RelativeTime.MM, months)
            } else if (months == 1) {
                listOf(RelativeTime.M)
            } else if (days > 0 && days < Thresholds.d.default) {
                listOf(RelativeTime.dd, days)
            } else if(days == 1L) {
                listOf(RelativeTime.d)
            } else if(hours > 0 && hours < Thresholds.h.default) {
                listOf(RelativeTime.hh, hours)
            } else if(hours == 1L) {
                listOf(RelativeTime.h)
            } else if(minutes > 0 && minutes <= Thresholds.m.default) {
                listOf(RelativeTime.mm, minutes)
            } else if(minutes == 1L) {
                listOf(RelativeTime.m)
            } else if(seconds > 0 && seconds < Thresholds.s.default) {
                listOf(RelativeTime.ss, seconds)
            } else {
                listOf(RelativeTime.s)
            }
    val humanizeFnc = registry.getForLocale(locale)

    return if(a.size > 1) {
        val str = humanizeFnc.map()[a[0]]!!
        str.replace("%d", a[1].toString(), true)
    } else {
        humanizeFnc.map()[a[0]]!!
    }
}