package cz.linkedlist.reltimek.functions

import cz.linkedlist.reltimek.ComplexFnc
import cz.linkedlist.reltimek.Payload
import cz.linkedlist.reltimek.RelativeTime

@RegisterFnc("cs")
class CsHumanizeFnc : ComplexFnc {

    override val future = "za %s"
    override val past = "před %s"

    override fun processPayload(payload: Payload): String {
        val result = if(payload.double) "${payload.num} " else " "
        val withoutSuffix = payload.withoutSuffix
        val isFuture = payload.isFuture
        return if(payload.double) {
            when(payload.relTime) {
                RelativeTime.SECONDS -> doubleResolution(payload, result, "sekundy", "sekund", "sekundami")
                RelativeTime.MINUTES -> doubleResolution(payload, result, "minuty", "minut", "minutami")
                RelativeTime.HOURS -> doubleResolution(payload, result, "hodiny", "hodin", "hodinami")
                RelativeTime.DAYS -> doubleResolution(payload, result, "dny", "dní", "dny")
                RelativeTime.MONTHS -> doubleResolution(payload, result, "měsíce", "měsíců", "měsíci")
                RelativeTime.YEARS -> doubleResolution(payload, result, "roky", "let", "lety")
            }
        } else {
            when(payload.relTime) {
                RelativeTime.SECONDS -> if(withoutSuffix || isFuture) "pár sekund" else "pár sekundami"
                RelativeTime.MINUTES -> if(withoutSuffix) "minuta" else if(isFuture) "minutu" else "minutou"
                RelativeTime.HOURS -> if(withoutSuffix) "hodina" else if(isFuture) "hodinu" else "hodinou"
                RelativeTime.DAYS -> if(withoutSuffix || isFuture) "den" else "dnem"
                RelativeTime.MONTHS -> if(withoutSuffix || isFuture) "měsíc" else "měsícem"
                RelativeTime.YEARS -> if(withoutSuffix || isFuture) "rok" else "rokem"
            }
        }
    }

    private fun doubleResolution(payload: Payload, result: String, s1: String, s2: String, s3: String) =
            if (payload.withoutSuffix || payload.isFuture) result + (if (plural(payload.num!!)) s1 else s2) else result + s3

    private fun plural(n: Long): Boolean {
        return (n > 1) && (n < 5) && (Math.round(n.toDouble()/10) != 1L)
    }
}