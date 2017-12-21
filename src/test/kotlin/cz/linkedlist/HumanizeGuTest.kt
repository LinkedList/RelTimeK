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
class HumanizeGuTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("gu"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, અમુક પળો",
        "SECONDS, 45, 45 સેકંડ",
        "SECONDS, 48, એક મિનિટ",
        "MINUTES, 1, એક મિનિટ",
        "MINUTES, 3, 3 મિનિટ",
        "MINUTES, 45, એક કલાક",
        "HOURS, 1, એક કલાક",
        "HOURS, 3, 3 કલાક",
        "HOURS, 22, એક દિવસ",
        "DAYS, 1, એક દિવસ",
        "DAYS, 3, 3 દિવસ",
        "DAYS, 26, એક મહિનો",
        "MONTHS, 1, એક મહિનો",
        "DAYS, 93, 3 મહિનો",
        "DAYS, 341, એક વર્ષ",
        "YEARS, 1, એક વર્ષ",
        "YEARS, 4, 4 વર્ષ"
    ])
    fun testGu(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 મિનિટ મા"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 મિનિટ પેહલા"))
    }
}
