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
class HumanizeSwTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("sw"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, hivi punde",
        "SECONDS, 45, sekunde 45",
        "SECONDS, 48, dakika moja",
        "MINUTES, 1, dakika moja",
        "MINUTES, 3, dakika 3",
        "MINUTES, 45, saa limoja",
        "HOURS, 1, saa limoja",
        "HOURS, 3, masaa 3",
        "HOURS, 22, siku moja",
        "DAYS, 1, siku moja",
        "DAYS, 3, masiku 3",
        "DAYS, 26, mwezi mmoja",
        "MONTHS, 1, mwezi mmoja",
        "DAYS, 93, miezi 3",
        "DAYS, 341, mwaka mmoja",
        "YEARS, 1, mwaka mmoja",
        "YEARS, 4, miaka 4"
    ])
    fun testSw(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("dakika 5 baadaye"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("tokea dakika 5"))
    }
}
