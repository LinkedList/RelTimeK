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
class HumanizeDanishTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("da"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, få sekunder",
        "SECONDS, 45, 45 sekunder",
        "SECONDS, 48, et minut",
        "MINUTES, 1, et minut",
        "MINUTES, 3, 3 minutter",
        "MINUTES, 45, en time",
        "HOURS, 1, en time",
        "HOURS, 3, 3 timer",
        "HOURS, 22, en dag",
        "DAYS, 1, en dag",
        "DAYS, 3, 3 dage",
        "DAYS, 26, en måned",
        "MONTHS, 1, en måned",
        "DAYS, 93, 3 måneder",
        "DAYS, 341, et år",
        "YEARS, 1, et år",
        "YEARS, 4, 4 år"
    ])
    fun testDanish(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("om 5 minutter"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minutter siden"))
    }
}