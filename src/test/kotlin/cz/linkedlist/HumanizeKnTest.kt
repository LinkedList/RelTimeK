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
class HumanizeKnTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("kn"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ಕೆಲವು ಕ್ಷಣಗಳು",
        "SECONDS, 45, 45 ಸೆಕೆಂಡುಗಳು",
        "SECONDS, 48, ಒಂದು ನಿಮಿಷ",
        "MINUTES, 1, ಒಂದು ನಿಮಿಷ",
        "MINUTES, 3, 3 ನಿಮಿಷ",
        "MINUTES, 45, ಒಂದು ಗಂಟೆ",
        "HOURS, 1, ಒಂದು ಗಂಟೆ",
        "HOURS, 3, 3 ಗಂಟೆ",
        "HOURS, 22, ಒಂದು ದಿನ",
        "DAYS, 1, ಒಂದು ದಿನ",
        "DAYS, 3, 3 ದಿನ",
        "DAYS, 26, ಒಂದು ತಿಂಗಳು",
        "MONTHS, 1, ಒಂದು ತಿಂಗಳು",
        "DAYS, 93, 3 ತಿಂಗಳು",
        "DAYS, 341, ಒಂದು ವರ್ಷ",
        "YEARS, 1, ಒಂದು ವರ್ಷ",
        "YEARS, 4, 4 ವರ್ಷ"
    ])
    fun testKn(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 ನಿಮಿಷ ನಂತರ"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 ನಿಮಿಷ ಹಿಂದೆ"))
    }
}
