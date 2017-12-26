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
class HumanizeUzLatnTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("uz-latn"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, soniya",
        "SECONDS, 45, 45 soniya",
        "SECONDS, 48, bir daqiqa",
        "MINUTES, 1, bir daqiqa",
        "MINUTES, 3, 3 daqiqa",
        "MINUTES, 45, bir soat",
        "HOURS, 1, bir soat",
        "HOURS, 3, 3 soat",
        "HOURS, 22, bir kun",
        "DAYS, 1, bir kun",
        "DAYS, 3, 3 kun",
        "DAYS, 26, bir oy",
        "MONTHS, 1, bir oy",
        "DAYS, 93, 3 oy",
        "DAYS, 341, bir yil",
        "YEARS, 1, bir yil",
        "YEARS, 4, 4 yil"
    ])
    fun testUzLatn(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("Yaqin 5 daqiqa ichida"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("Bir necha 5 daqiqa oldin"))
    }
}
