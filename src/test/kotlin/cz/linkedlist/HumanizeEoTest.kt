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
class HumanizeEoTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("eo"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, sekundoj",
        "SECONDS, 45, 45 sekundoj",
        "SECONDS, 48, minuto",
        "MINUTES, 1, minuto",
        "MINUTES, 3, 3 minutoj",
        "MINUTES, 45, horo",
        "HOURS, 1, horo",
        "HOURS, 3, 3 horoj",
        "HOURS, 22, tago",
        "DAYS, 1, tago",
        "DAYS, 3, 3 tagoj",
        "DAYS, 26, monato",
        "MONTHS, 1, monato",
        "DAYS, 93, 3 monatoj",
        "DAYS, 341, jaro",
        "YEARS, 1, jaro",
        "YEARS, 4, 4 jaroj"
    ])
    fun testEo(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("post 5 minutoj"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("antaŭ 5 minutoj"))
    }
}
