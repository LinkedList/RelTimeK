package cz.linkedlist.reltimek

import java.time.Duration

class CsHumanizeFnc : HumanizeFnc {

    private val relTime = mapOf(
            RelativeTime.s to "pár sekund",
            RelativeTime.ss to "%d sekund",
            RelativeTime.mm to "minuta",
            RelativeTime.hh to "hodina"
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