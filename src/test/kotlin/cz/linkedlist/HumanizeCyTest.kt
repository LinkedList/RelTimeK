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
class HumanizeCyTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("cy"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ychydig eiliadau",
        "SECONDS, 45, 45 eiliad",
        "SECONDS, 48, munud",
        "MINUTES, 1, munud",
        "MINUTES, 3, 3 munud",
        "MINUTES, 45, awr",
        "HOURS, 1, awr",
        "HOURS, 3, 3 awr",
        "HOURS, 22, diwrnod",
        "DAYS, 1, diwrnod",
        "DAYS, 3, 3 diwrnod",
        "DAYS, 26, mis",
        "MONTHS, 1, mis",
        "DAYS, 93, 3 mis",
        "DAYS, 341, blwyddyn",
        "YEARS, 1, blwyddyn",
        "YEARS, 4, 4 flynedd"
    ])
    fun testCy(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("mewn 5 munud"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 munud yn ôl"))
    }
}
