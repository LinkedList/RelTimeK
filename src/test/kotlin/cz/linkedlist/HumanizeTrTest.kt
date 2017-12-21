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
class HumanizeTrTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("tr"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, birkaç saniye",
        "SECONDS, 45, 45 saniye",
        "SECONDS, 48, bir dakika",
        "MINUTES, 1, bir dakika",
        "MINUTES, 3, 3 dakika",
        "MINUTES, 45, bir saat",
        "HOURS, 1, bir saat",
        "HOURS, 3, 3 saat",
        "HOURS, 22, bir gün",
        "DAYS, 1, bir gün",
        "DAYS, 3, 3 gün",
        "DAYS, 26, bir ay",
        "MONTHS, 1, bir ay",
        "DAYS, 93, 3 ay",
        "DAYS, 341, bir yıl",
        "YEARS, 1, bir yıl",
        "YEARS, 4, 4 yıl"
    ])
    fun testTr(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 dakika sonra"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 dakika önce"))
    }
}
