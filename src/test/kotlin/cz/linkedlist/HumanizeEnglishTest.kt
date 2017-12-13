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
class HumanizeEnglishTest {

    val US: Locale = Locale.US

    @Test
    fun testMinute() {
        val now = LocalDateTime.now()
        val minuteBefore = now.minusMinutes(1)
        Locale.setDefault(US)

        assertThat(Duration.between(now, minuteBefore).humanize(), equalTo("a minute"))
    }

    @Test
    fun testFewSeconds() {
        val now = LocalDateTime.now()
        val secondsBefore = now.minusSeconds(3)

        val duration = Duration.between(now, secondsBefore)
        assertThat(duration.humanize(US), equalTo("a few seconds"))
    }
}