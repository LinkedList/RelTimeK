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
class HumanizeTzmTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("tzm"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ⵉⵎⵉⴽ",
        "SECONDS, 45, 45 ⵉⵎⵉⴽ",
        "SECONDS, 48, ⵎⵉⵏⵓⴺ",
        "MINUTES, 1, ⵎⵉⵏⵓⴺ",
        "MINUTES, 3, 3 ⵎⵉⵏⵓⴺ",
        "MINUTES, 45, ⵙⴰⵄⴰ",
        "HOURS, 1, ⵙⴰⵄⴰ",
        "HOURS, 3, 3 ⵜⴰⵙⵙⴰⵄⵉⵏ",
        "HOURS, 22, ⴰⵙⵙ",
        "DAYS, 1, ⴰⵙⵙ",
        "DAYS, 3, 3 oⵙⵙⴰⵏ",
        "DAYS, 26, ⴰⵢoⵓⵔ",
        "MONTHS, 1, ⴰⵢoⵓⵔ",
        "DAYS, 93, 3 ⵉⵢⵢⵉⵔⵏ",
        "DAYS, 341, ⴰⵙⴳⴰⵙ",
        "YEARS, 1, ⴰⵙⴳⴰⵙ",
        "YEARS, 4, 4 ⵉⵙⴳⴰⵙⵏ"
    ])
    fun testTzm(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("ⴷⴰⴷⵅ ⵙ ⵢⴰⵏ 5 ⵎⵉⵏⵓⴺ"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("ⵢⴰⵏ 5 ⵎⵉⵏⵓⴺ"))
    }
}
