package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * @author Martin Macko <https://github.com/LinkedList>.
 */
class HumanizeTlTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("tl"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ilang segundo",
        "SECONDS, 45, 45 segundo",
        "SECONDS, 48, isang minuto",
        "MINUTES, 1, isang minuto",
        "MINUTES, 3, 3 minuto",
        "MINUTES, 45, isang oras",
        "HOURS, 1, isang oras",
        "HOURS, 3, 3 oras",
        "HOURS, 22, isang araw",
        "DAYS, 1, isang araw",
        "DAYS, 3, 3 araw",
        "DAYS, 26, isang buwan",
        "MONTHS, 1, isang buwan",
        "DAYS, 93, 3 buwan",
        "DAYS, 341, isang taon",
        "YEARS, 1, isang taon",
        "YEARS, 4, 4 taon"
    ])
    fun testTl(unit: ChronoUnit, value: Long, result: String) {
        val time = LocalTime.of(12, 30, 30)
        val date = LocalDate.of(2017, 5, 13)
        val dateTime = LocalDateTime.of(date, time)
        val before = dateTime.minus(value, unit)

        assertThat(Duration.between(dateTime, before).humanize(), equalTo(result))
    }

    @Test
    fun testFuture() {
        val now = LocalDateTime.now()
        val before = now.plusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("sa loob ng 5 minuto"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minuto ang nakalipas"))
    }
}
