package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.RelativeTime
import cz.linkedlist.reltimek.SimpleFnc

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
@RegisterFnc("sv")
class SvHumanizeFnc : SimpleFnc {
    override val future = "om %s"
    override val past = "för %s sedan"
    override val map = mapOf(
            RelativeTime.SECONDS.single to "några sekunder",
            RelativeTime.SECONDS.double to "%d sekunder",
            RelativeTime.MINUTES.single to "en minut",
            RelativeTime.MINUTES.double to "%d minuter",
            RelativeTime.HOURS.single to "en timme",
            RelativeTime.HOURS.double to "%d timmar",
            RelativeTime.DAYS.single to "en dag",
            RelativeTime.DAYS.double to "%d dagar",
            RelativeTime.MONTHS.single to "en månad",
            RelativeTime.MONTHS.double to "%d månader",
            RelativeTime.YEARS.single to "ett år",
            RelativeTime.YEARS.double to "%d år"
    )
}