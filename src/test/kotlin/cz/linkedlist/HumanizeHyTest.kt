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
class HumanizeHyTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("hy"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, մի քանի վայրկյան",
        "SECONDS, 45, 45 վայրկյան",
        "SECONDS, 48, րոպե",
        "MINUTES, 1, րոպե",
        "MINUTES, 3, 3 րոպե",
        "MINUTES, 45, ժամ",
        "HOURS, 1, ժամ",
        "HOURS, 3, 3 ժամ",
        "HOURS, 22, օր",
        "DAYS, 1, օր",
        "DAYS, 3, 3 օր",
        "DAYS, 26, ամիս",
        "MONTHS, 1, ամիս",
        "DAYS, 93, 3 ամիս",
        "DAYS, 341, տարի",
        "YEARS, 1, տարի",
        "YEARS, 4, 4 տարի"
    ])
    fun testHy(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 րոպե հետո"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 րոպե առաջ"))
    }
}
