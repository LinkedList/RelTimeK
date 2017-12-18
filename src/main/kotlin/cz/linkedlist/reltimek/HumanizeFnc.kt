package cz.linkedlist.reltimek

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
interface HumanizeFnc {
    val future: String?
    val past: String?

    fun humanize(payload: Payload): String

    fun simplePrefixSuffix(isFuture: Boolean, humanized: String): String {
        if(future == null || past == null)
            throw IllegalStateException("[future] and [past] property cannot be null if you want to use simplePrefixSuffix")
        else {
            return when {
                isFuture -> this.future!!.replace("%s", humanized)
                else -> this.past!!.replace("%s", humanized)
            }
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

        return if(withSuffix) {
            simplePrefixSuffix(isFuture, simpleHumanized)
        } else {
            simpleHumanized
        }
    }

}

class SimpleFncImpl(override val future: String?, override val past: String?, override val map: Map<String, String>): SimpleFnc

interface ComplexFnc: HumanizeFnc {

    fun future(processed: String, payload: Payload):String {
        return simplePrefixSuffix(true, processed)
    }
    fun past(processed: String, payload: Payload): String {
        return simplePrefixSuffix(false, processed)
    }

    fun processPayload(payload: Payload): String

    override fun humanize(payload: Payload): String {
        val processed = processPayload(payload)

        val withSuffix = !payload.withoutSuffix
        val isFuture = payload.isFuture

        return if(withSuffix) {
            when {
                isFuture -> future(processed, payload)
                else -> past(processed, payload)
            }
        } else {
            processed
        }
    }
}


