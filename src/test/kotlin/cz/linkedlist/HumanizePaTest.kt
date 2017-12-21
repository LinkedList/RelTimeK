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
class HumanizePaTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("pa"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ਕੁਝ ਸਕਿੰਟ",
        "SECONDS, 45, 45 ਸਕਿੰਟ",
        "SECONDS, 48, ਇਕ ਮਿੰਟ",
        "MINUTES, 1, ਇਕ ਮਿੰਟ",
        "MINUTES, 3, 3 ਮਿੰਟ",
        "MINUTES, 45, ਇੱਕ ਘੰਟਾ",
        "HOURS, 1, ਇੱਕ ਘੰਟਾ",
        "HOURS, 3, 3 ਘੰਟੇ",
        "HOURS, 22, ਇੱਕ ਦਿਨ",
        "DAYS, 1, ਇੱਕ ਦਿਨ",
        "DAYS, 3, 3 ਦਿਨ",
        "DAYS, 26, ਇੱਕ ਮਹੀਨਾ",
        "MONTHS, 1, ਇੱਕ ਮਹੀਨਾ",
        "DAYS, 93, 3 ਮਹੀਨੇ",
        "DAYS, 341, ਇੱਕ ਸਾਲ",
        "YEARS, 1, ਇੱਕ ਸਾਲ",
        "YEARS, 4, 4 ਸਾਲ"
    ])
    fun testPa(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 ਮਿੰਟ ਵਿੱਚ"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 ਮਿੰਟ ਪਿਛਲੇ"))
    }
}
