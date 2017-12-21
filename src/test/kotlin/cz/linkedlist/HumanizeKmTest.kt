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
class HumanizeKmTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("km"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ប៉ុន្មានវិនាទី",
        "SECONDS, 45, 45 វិនាទី",
        "SECONDS, 48, មួយនាទី",
        "MINUTES, 1, មួយនាទី",
        "MINUTES, 3, 3 នាទី",
        "MINUTES, 45, មួយម៉ោង",
        "HOURS, 1, មួយម៉ោង",
        "HOURS, 3, 3 ម៉ោង",
        "HOURS, 22, មួយថ្ងៃ",
        "DAYS, 1, មួយថ្ងៃ",
        "DAYS, 3, 3 ថ្ងៃ",
        "DAYS, 26, មួយខែ",
        "MONTHS, 1, មួយខែ",
        "DAYS, 93, 3 ខែ",
        "DAYS, 341, មួយឆ្នាំ",
        "YEARS, 1, មួយឆ្នាំ",
        "YEARS, 4, 4 ឆ្នាំ"
    ])
    fun testKm(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 នាទីទៀត"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 នាទីមុន"))
    }
}
