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
class HumanizeTetTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("tet"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, minutu balun",
        "SECONDS, 45, minutu 45",
        "SECONDS, 48, minutu ida",
        "MINUTES, 1, minutu ida",
        "MINUTES, 3, minutus 3",
        "MINUTES, 45, horas ida",
        "HOURS, 1, horas ida",
        "HOURS, 3, horas 3",
        "HOURS, 22, loron ida",
        "DAYS, 1, loron ida",
        "DAYS, 3, loron 3",
        "DAYS, 26, fulan ida",
        "MONTHS, 1, fulan ida",
        "DAYS, 93, fulan 3",
        "DAYS, 341, tinan ida",
        "YEARS, 1, tinan ida",
        "YEARS, 4, tinan 4"
    ])
    fun testTet(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("iha minutus 5"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("minutus 5 liuba"))
    }
}
