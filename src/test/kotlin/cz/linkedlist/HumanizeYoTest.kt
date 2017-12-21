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
class HumanizeYoTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("yo"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ìsẹjú aayá die",
        "SECONDS, 45, aayá 45",
        "SECONDS, 48, ìsẹjú kan",
        "MINUTES, 1, ìsẹjú kan",
        "MINUTES, 3, ìsẹjú 3",
        "MINUTES, 45, wákati kan",
        "HOURS, 1, wákati kan",
        "HOURS, 3, wákati 3",
        "HOURS, 22, ọjọ́ kan",
        "DAYS, 1, ọjọ́ kan",
        "DAYS, 3, ọjọ́ 3",
        "DAYS, 26, osù kan",
        "MONTHS, 1, osù kan",
        "DAYS, 93, osù 3",
        "DAYS, 341, ọdún kan",
        "YEARS, 1, ọdún kan",
        "YEARS, 4, ọdún 4"
    ])
    fun testYo(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("ní ìsẹjú 5"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("ìsẹjú 5 kọjá"))
    }
}
