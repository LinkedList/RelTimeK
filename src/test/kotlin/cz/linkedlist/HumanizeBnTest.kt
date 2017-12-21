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
class HumanizeBnTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("bn"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, কয়েক সেকেন্ড",
        "SECONDS, 45, 45 সেকেন্ড",
        "SECONDS, 48, এক মিনিট",
        "MINUTES, 1, এক মিনিট",
        "MINUTES, 3, 3 মিনিট",
        "MINUTES, 45, এক ঘন্টা",
        "HOURS, 1, এক ঘন্টা",
        "HOURS, 3, 3 ঘন্টা",
        "HOURS, 22, এক দিন",
        "DAYS, 1, এক দিন",
        "DAYS, 3, 3 দিন",
        "DAYS, 26, এক মাস",
        "MONTHS, 1, এক মাস",
        "DAYS, 93, 3 মাস",
        "DAYS, 341, এক বছর",
        "YEARS, 1, এক বছর",
        "YEARS, 4, 4 বছর"
    ])
    fun testBn(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 মিনিট পরে"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 মিনিট আগে"))
    }
}
