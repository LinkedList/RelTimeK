package cz.linkedlist.reltimek

import java.time.Duration


class EnHumanizeFnc: HumanizeFnc {

    private val relTime = mapOf(
            RelativeTime.SECONDS.single to "a few seconds",
            RelativeTime.SECONDS.double to "%d seconds",
            RelativeTime.MINUTES.single to "a minute",
            RelativeTime.MINUTES.double to "%d minutes"
    )
    override fun map(): Map<String, String> {
        return relTime
    }

    override fun humanize(d: Duration): String {
        return ""
    }
}