package cz.linkedlist.reltimek

import java.time.Duration

class CsHumanizeFnc : HumanizeFnc {

    private val relTime = mapOf(
            RelativeTime.SECONDS.single to "pár sekund",
            RelativeTime.SECONDS.double to "%d sekund",
            RelativeTime.MINUTES.single to "minuta",
            RelativeTime.MINUTES.double to "%d minut",
            RelativeTime.HOURS.single to "hodina"
    )

    override fun map(): Map<String, String> {
        return relTime
    }

    override fun humanize(d: Duration): String {
        return ""
    }
}