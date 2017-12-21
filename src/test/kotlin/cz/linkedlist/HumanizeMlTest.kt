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
class HumanizeMlTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ml"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, അൽപ നിമിഷങ്ങൾ",
        "SECONDS, 45, 45 സെക്കൻഡ്",
        "SECONDS, 48, ഒരു മിനിറ്റ്",
        "MINUTES, 1, ഒരു മിനിറ്റ്",
        "MINUTES, 3, 3 മിനിറ്റ്",
        "MINUTES, 45, ഒരു മണിക്കൂർ",
        "HOURS, 1, ഒരു മണിക്കൂർ",
        "HOURS, 3, 3 മണിക്കൂർ",
        "HOURS, 22, ഒരു ദിവസം",
        "DAYS, 1, ഒരു ദിവസം",
        "DAYS, 3, 3 ദിവസം",
        "DAYS, 26, ഒരു മാസം",
        "MONTHS, 1, ഒരു മാസം",
        "DAYS, 93, 3 മാസം",
        "DAYS, 341, ഒരു വർഷം",
        "YEARS, 1, ഒരു വർഷം",
        "YEARS, 4, 4 വർഷം"
    ])
    fun testMl(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 മിനിറ്റ് കഴിഞ്ഞ്"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 മിനിറ്റ് മുൻപ്"))
    }
}
