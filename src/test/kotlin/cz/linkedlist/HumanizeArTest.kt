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
class HumanizeArTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ar"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ثوان",
        "SECONDS, 45, 45 ثانية",
        "SECONDS, 48, دقيقة",
        "MINUTES, 1, دقيقة",
        "MINUTES, 3, 3 دقائق",
        "MINUTES, 45, ساعة",
        "HOURS, 1, ساعة",
        "HOURS, 3, 3 ساعات",
        "HOURS, 22, يوم",
        "DAYS, 1, يوم",
        "DAYS, 3, 3 أيام",
        "DAYS, 26, شهر",
        "MONTHS, 1, شهر",
        "DAYS, 93, 3 أشهر",
        "DAYS, 341, سنة",
        "YEARS, 1, سنة",
        "YEARS, 4, 4 سنوات"
    ])
    fun testAr(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("في 5 دقائق"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("منذ 5 دقائق"))
    }
}
