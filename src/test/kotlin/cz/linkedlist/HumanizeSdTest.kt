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
class HumanizeSdTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("sd"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, چند سيڪنڊ",
        "SECONDS, 45, 45 سيڪنڊ",
        "SECONDS, 48, هڪ منٽ",
        "MINUTES, 1, هڪ منٽ",
        "MINUTES, 3, 3 منٽ",
        "MINUTES, 45, هڪ ڪلاڪ",
        "HOURS, 1, هڪ ڪلاڪ",
        "HOURS, 3, 3 ڪلاڪ",
        "HOURS, 22, هڪ ڏينهن",
        "DAYS, 1, هڪ ڏينهن",
        "DAYS, 3, 3 ڏينهن",
        "DAYS, 26, هڪ مهينو",
        "MONTHS, 1, هڪ مهينو",
        "DAYS, 93, 3 مهينا",
        "DAYS, 341, هڪ سال",
        "YEARS, 1, هڪ سال",
        "YEARS, 4, 4 سال"
    ])
    fun testSd(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 منٽ پوء"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 منٽ اڳ"))
    }
}
