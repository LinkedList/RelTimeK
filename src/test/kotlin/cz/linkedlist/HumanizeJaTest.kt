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
class HumanizeJaTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ja"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, 数秒",
        "SECONDS, 45, 45秒",
        "SECONDS, 48, 1分",
        "MINUTES, 1, 1分",
        "MINUTES, 3, 3分",
        "MINUTES, 45, 1時間",
        "HOURS, 1, 1時間",
        "HOURS, 3, 3時間",
        "HOURS, 22, 1日",
        "DAYS, 1, 1日",
        "DAYS, 3, 3日",
        "DAYS, 26, 1ヶ月",
        "MONTHS, 1, 1ヶ月",
        "DAYS, 93, 3ヶ月",
        "DAYS, 341, 1年",
        "YEARS, 1, 1年",
        "YEARS, 4, 4年"
    ])
    fun testJa(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5分後"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5分前"))
    }
}
