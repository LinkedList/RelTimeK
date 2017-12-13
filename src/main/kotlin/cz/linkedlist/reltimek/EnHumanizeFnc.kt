package cz.linkedlist.reltimek

import java.time.Duration


class EnHumanizeFnc: HumanizeFnc {

    private val relTime = mapOf(
            RelativeTime.s to "a few seconds",
            RelativeTime.mm to "a minute"
    )
    override fun map(): Map<RelativeTime, String> {
        return relTime
    }

    override fun humanize(d: Duration): String {
        val dur = d.abs()
        val seconds = dur.seconds
        val minutes = dur.toMinutes()

        val a = if(minutes > 0 && minutes <= Thresholds.m.default) {
            listOf(RelativeTime.mm, minutes)
        } else {
            listOf(RelativeTime.s, seconds)
        }

        return relTime[a[0]]!!
    }
}