package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.ComplexFnc
import cz.linkedlist.reltimek.Payload
import cz.linkedlist.reltimek.RelativeTime
import cz.linkedlist.reltimek.SimpleFncImpl

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
@RegisterFnc("cv")
class CvHumanizeFnc: ComplexFnc {
    override val future = null
    override val past = null

    private val simple = SimpleFncImpl(null, null, mapOf(
            RelativeTime.SECONDS.single to "пӗр-ик ҫеккунт",
            RelativeTime.SECONDS.double to "%d ҫеккунт",
            RelativeTime.MINUTES.single to "пӗр минут",
            RelativeTime.MINUTES.double to "%d минут",
            RelativeTime.HOURS.single to "пӗр сехет",
            RelativeTime.HOURS.double to "%d сехет",
            RelativeTime.DAYS.single to "пӗр кун",
            RelativeTime.DAYS.double to "%d кун",
            RelativeTime.MONTHS.single to "пӗр уйӑх",
            RelativeTime.MONTHS.double to "%d уйӑх",
            RelativeTime.YEARS.single to "пӗр ҫул",
            RelativeTime.YEARS.double to "%d ҫул"
    ))

    override fun processPayload(payload: Payload): String {
        return simple.humanize(payload.copy(withoutSuffix = true))
    }

    override fun future(processed: String, payload: Payload): String {
        return when {
            processed.endsWith("сехет") -> "${processed}рен"
            processed.endsWith("ҫул") -> "${processed}тан"
            else -> "${processed}ран"
        }
    }

    override fun past(processed: String, payload: Payload): String {
        return "$processed секунд"
    }
}