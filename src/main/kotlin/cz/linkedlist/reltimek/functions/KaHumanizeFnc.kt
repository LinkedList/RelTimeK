package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.ComplexFnc
import cz.linkedlist.reltimek.Payload
import cz.linkedlist.reltimek.RelativeTime
import cz.linkedlist.reltimek.SimpleFncImpl

@RegisterFnc("ka")
class KaHumanizeFnc : ComplexFnc{

    private val future_pattern = Regex("(წამი|წუთი|საათი|წელი)")
    private val past_pattern = Regex("წელი")
    private val simple = SimpleFncImpl(null, null, mapOf(
            RelativeTime.SECONDS.single to "რამდენიმე წამი",
            RelativeTime.SECONDS.double to "%d წამი",
            RelativeTime.MINUTES.single to "წუთი",
            RelativeTime.MINUTES.double to "%d წუთი",
            RelativeTime.HOURS.single to "საათი",
            RelativeTime.HOURS.double to "%d საათი",
            RelativeTime.DAYS.single to "დღე",
            RelativeTime.DAYS.double to "%d დღე",
            RelativeTime.MONTHS.single to "თვე",
            RelativeTime.MONTHS.double to "%d თვე",
            RelativeTime.YEARS.single to "წელი",
            RelativeTime.YEARS.double to "%d წელი"
        )
    )
    override val future = null
    override val past = null

    override fun future(processed: String, payload: Payload): String {
        return if(future_pattern.containsMatchIn(processed)) {
            processed.replace(Regex("ი$"), "ში")
        } else {
            "${processed}ში"
        }
    }

    override fun past(processed: String, payload: Payload): String {
        return if(past_pattern.containsMatchIn(processed)) {
            processed.replace(Regex("წელი$"), "წლის უკან")
        } else {
            processed.replace(Regex("(ი|ე)$"), "ის უკან")
        }
    }

    override fun processPayload(payload: Payload): String {
        return simple.humanize(payload.copy(withoutSuffix = true))
    }
}
