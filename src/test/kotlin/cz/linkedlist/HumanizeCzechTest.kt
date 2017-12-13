package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
class HumanizeCzechTest {

    val CZECH: Locale = Locale.forLanguageTag("cs_CZ")

    @Test
    fun testMinute() {
        val now = LocalDateTime.now()
        val minuteBefore = now.minusMinutes(1)
        Locale.setDefault(CZECH)

        assertThat(Duration.between(now, minuteBefore).humanize(), equalTo("minuta"))
    }

    @Test
    fun testAFewSeconds() {
        val now = LocalDateTime.now()
        val secondsBefore = now.minusSeconds(3)

        val duration = Duration.between(now, secondsBefore)
        assertThat(duration.humanize(CZECH), equalTo("pár sekund"))
    }


    @Test
    fun testAFewSecondsDefaultThreshold() {
        val now = LocalDateTime.now()
        val secondsBefore = now.minusSeconds(45)

        val duration = Duration.between(now, secondsBefore)
        assertThat(duration.humanize(CZECH), equalTo("45 sekund"))
    }


    @Test
    fun testHour() {
        val now = LocalDateTime.now()
        val secondsBefore = now.minusHours(1)

        val duration = Duration.between(now, secondsBefore)
        assertThat(duration.humanize(CZECH), equalTo("hodina"))
    }
}