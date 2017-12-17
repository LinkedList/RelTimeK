package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.RelativeTime
import cz.linkedlist.reltimek.SimpleFnc

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
@RegisterFnc("da")
class DaHumanizeFnc: SimpleFnc {
    override val future = "om %s"
    override val past = "%s siden"
    override val map = mapOf(
            RelativeTime.SECONDS.single to "få sekunder",
            RelativeTime.SECONDS.double to "%d sekunder",
            RelativeTime.MINUTES.single to "et minut",
            RelativeTime.MINUTES.double to "%d minutter",
            RelativeTime.HOURS.single to "en time",
            RelativeTime.HOURS.double to "%d timer",
            RelativeTime.DAYS.single to "en dag",
            RelativeTime.DAYS.double to "%d dage",
            RelativeTime.MONTHS.single to "en måned",
            RelativeTime.MONTHS.double to "%d måneder",
            RelativeTime.YEARS.single to "et år",
            RelativeTime.YEARS.double to "%d år"
    )

}