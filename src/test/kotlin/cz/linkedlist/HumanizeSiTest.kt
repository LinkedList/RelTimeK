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
class HumanizeSiTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("si"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, තත්පර කිහිපය",
        "SECONDS, 45, තත්පර 45",
        "SECONDS, 48, මිනිත්තුව",
        "MINUTES, 1, මිනිත්තුව",
        "MINUTES, 3, මිනිත්තු 3",
        "MINUTES, 45, පැය",
        "HOURS, 1, පැය",
        "HOURS, 3, පැය 3",
        "HOURS, 22, දිනය",
        "DAYS, 1, දිනය",
        "DAYS, 3, දින 3",
        "DAYS, 26, මාසය",
        "MONTHS, 1, මාසය",
        "DAYS, 93, මාස 3",
        "DAYS, 341, වසර",
        "YEARS, 1, වසර",
        "YEARS, 4, වසර 4"
    ])
    fun testSi(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("මිනිත්තු 5කින්"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("මිනිත්තු 5කට පෙර"))
    }
}
