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
class HumanizeBgTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("bg"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, няколко секунди",
        "SECONDS, 45, 45 секунди",
        "SECONDS, 48, минута",
        "MINUTES, 1, минута",
        "MINUTES, 3, 3 минути",
        "MINUTES, 45, час",
        "HOURS, 1, час",
        "HOURS, 3, 3 часа",
        "HOURS, 22, ден",
        "DAYS, 1, ден",
        "DAYS, 3, 3 дни",
        "DAYS, 26, месец",
        "MONTHS, 1, месец",
        "DAYS, 93, 3 месеца",
        "DAYS, 341, година",
        "YEARS, 1, година",
        "YEARS, 4, 4 години"
    ])
    fun testBg(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("след 5 минути"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("преди 5 минути"))
    }
}
