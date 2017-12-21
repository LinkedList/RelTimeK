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
class HumanizeCaTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ca"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, uns segons",
        "SECONDS, 45, 45 segons",
        "SECONDS, 48, un minut",
        "MINUTES, 1, un minut",
        "MINUTES, 3, 3 minuts",
        "MINUTES, 45, una hora",
        "HOURS, 1, una hora",
        "HOURS, 3, 3 hores",
        "HOURS, 22, un dia",
        "DAYS, 1, un dia",
        "DAYS, 3, 3 dies",
        "DAYS, 26, un mes",
        "MONTHS, 1, un mes",
        "DAYS, 93, 3 mesos",
        "DAYS, 341, un any",
        "YEARS, 1, un any",
        "YEARS, 4, 4 anys"
    ])
    fun testCa(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("d'aquí 5 minuts"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("fa 5 minuts"))
    }
}
