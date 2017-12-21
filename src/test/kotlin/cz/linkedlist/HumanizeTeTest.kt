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
class HumanizeTeTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("te"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, కొన్ని క్షణాలు",
        "SECONDS, 45, 45 సెకన్లు",
        "SECONDS, 48, ఒక నిమిషం",
        "MINUTES, 1, ఒక నిమిషం",
        "MINUTES, 3, 3 నిమిషాలు",
        "MINUTES, 45, ఒక గంట",
        "HOURS, 1, ఒక గంట",
        "HOURS, 3, 3 గంటలు",
        "HOURS, 22, ఒక రోజు",
        "DAYS, 1, ఒక రోజు",
        "DAYS, 3, 3 రోజులు",
        "DAYS, 26, ఒక నెల",
        "MONTHS, 1, ఒక నెల",
        "DAYS, 93, 3 నెలలు",
        "DAYS, 341, ఒక సంవత్సరం",
        "YEARS, 1, ఒక సంవత్సరం",
        "YEARS, 4, 4 సంవత్సరాలు"
    ])
    fun testTe(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 నిమిషాలు లో"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 నిమిషాలు క్రితం"))
    }
}
