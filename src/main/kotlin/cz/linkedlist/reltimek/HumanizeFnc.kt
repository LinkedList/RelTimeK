package cz.linkedlist.reltimek

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
interface HumanizeFnc {
    val future: String
    val past: String

    fun humanize(payload: Payload): String

    fun simplePrefixSuffix(withSuffix: Boolean, isFuture: Boolean, humanized: String): String {
        return when {
            withSuffix && isFuture -> this.future.replace("%s", humanized)
            withSuffix && !isFuture -> this.past.replace("%s", humanized)
            else -> humanized
        }
    }
}

interface SimpleFnc: HumanizeFnc {
    val map: Map<String, String>

    override fun humanize(payload: Payload): String {
        val simpleHumanized =  if(payload.double) {
            val str = this.map[payload.relTime.double]!!
            str.replace("%d", payload.num.toString(), true)
        } else {
            this.map[payload.relTime.single]!!
        }

        val withSuffix = !payload.withoutSuffix
        val isFuture = payload.isFuture

        return simplePrefixSuffix(withSuffix, isFuture, simpleHumanized)
    }

}
interface ComplexFnc: HumanizeFnc {
    fun processPayload(payload: Payload): String

    override fun humanize(payload: Payload): String {
        val processed = processPayload(payload)

        val withSuffix = !payload.withoutSuffix
        val isFuture = payload.isFuture

        return simplePrefixSuffix(withSuffix, isFuture, processed)
    }
}


