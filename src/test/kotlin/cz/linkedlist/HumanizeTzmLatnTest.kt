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
class HumanizeTzmLatnTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("tzm-latn"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, imik",
        "SECONDS, 45, 45 imik",
        "SECONDS, 48, minuḍ",
        "MINUTES, 1, minuḍ",
        "MINUTES, 3, 3 minuḍ",
        "MINUTES, 45, saɛa",
        "HOURS, 1, saɛa",
        "HOURS, 3, 3 tassaɛin",
        "HOURS, 22, ass",
        "DAYS, 1, ass",
        "DAYS, 3, 3 ossan",
        "DAYS, 26, ayowr",
        "MONTHS, 1, ayowr",
        "DAYS, 93, 3 iyyirn",
        "DAYS, 341, asgas",
        "YEARS, 1, asgas",
        "YEARS, 4, 4 isgasn"
    ])
    fun testTzmLatn(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("dadkh s yan 5 minuḍ"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("yan 5 minuḍ"))
    }
}
