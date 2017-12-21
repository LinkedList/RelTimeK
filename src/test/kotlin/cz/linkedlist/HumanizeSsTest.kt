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
class HumanizeSsTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ss"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, emizuzwana lomcane",
        "SECONDS, 45, 45 mzuzwana",
        "SECONDS, 48, umzuzu",
        "MINUTES, 1, umzuzu",
        "MINUTES, 3, 3 emizuzu",
        "MINUTES, 45, lihora",
        "HOURS, 1, lihora",
        "HOURS, 3, 3 emahora",
        "HOURS, 22, lilanga",
        "DAYS, 1, lilanga",
        "DAYS, 3, 3 emalanga",
        "DAYS, 26, inyanga",
        "MONTHS, 1, inyanga",
        "DAYS, 93, 3 tinyanga",
        "DAYS, 341, umnyaka",
        "YEARS, 1, umnyaka",
        "YEARS, 4, 4 iminyaka"
    ])
    fun testSs(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("nga 5 emizuzu"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("wenteka nga 5 emizuzu"))
    }
}
