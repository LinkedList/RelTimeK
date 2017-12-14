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
        return if(payload.double) {
            val str = this.map[payload.relTime.double]!!
            str.replace("%d", payload.num.toString(), true)
        } else {
            this.map[payload.relTime.single]!!
        }
    }
}
interface ComplexFnc: HumanizeFnc {
    fun humanize(): String
}

