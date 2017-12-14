package cz.linkedlist.reltimek

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
interface HumanizeFnc {
    val future: String
    val past: String
}

interface SimpleFnc: HumanizeFnc {
    val map: Map<String, String>

    fun humanize(payload: Payload): String {
        val simpleHumanized =  if(payload.double) {
            val str = this.map[payload.relTime.double]!!
            str.replace("%d", payload.num.toString(), true)
        } else {
            this.map[payload.relTime.single]!!
        }

        val withSuffix = !payload.withoutSuffix
        val isFuture = payload.isFuture

        return when {
            withSuffix && isFuture -> this.future.replace("%s", simpleHumanized)
            withSuffix && !isFuture -> this.past.replace("%s", simpleHumanized)
            else -> simpleHumanized
        }
    }
}
interface ComplexFnc: HumanizeFnc {
    fun humanize(): String
}

