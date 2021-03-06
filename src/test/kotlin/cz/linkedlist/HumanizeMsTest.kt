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
class HumanizeMsTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ms"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, beberapa saat",
        "SECONDS, 45, 45 saat",
        "SECONDS, 48, seminit",
        "MINUTES, 1, seminit",
        "MINUTES, 3, 3 minit",
        "MINUTES, 45, sejam",
        "HOURS, 1, sejam",
        "HOURS, 3, 3 jam",
        "HOURS, 22, sehari",
        "DAYS, 1, sehari",
        "DAYS, 3, 3 hari",
        "DAYS, 26, sebulan",
        "MONTHS, 1, sebulan",
        "DAYS, 93, 3 bulan",
        "DAYS, 341, setahun",
        "YEARS, 1, setahun",
        "YEARS, 4, 4 tahun"
    ])
    fun testMs(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("dalam 5 minit"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minit yang lepas"))
    }
}
