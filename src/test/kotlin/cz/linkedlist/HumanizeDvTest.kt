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
class HumanizeDvTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("dv"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ސިކުންތުކޮޅެއް",
        "SECONDS, 45, d% ސިކުންތު",
        "SECONDS, 48, މިނިޓެއް",
        "MINUTES, 1, މިނިޓެއް",
        "MINUTES, 3, މިނިޓު 3",
        "MINUTES, 45, ގަޑިއިރެއް",
        "HOURS, 1, ގަޑިއިރެއް",
        "HOURS, 3, ގަޑިއިރު 3",
        "HOURS, 22, ދުވަހެއް",
        "DAYS, 1, ދުވަހެއް",
        "DAYS, 3, ދުވަސް 3",
        "DAYS, 26, މަހެއް",
        "MONTHS, 1, މަހެއް",
        "DAYS, 93, މަސް 3",
        "DAYS, 341, އަހަރެއް",
        "YEARS, 1, އަހަރެއް",
        "YEARS, 4, އަހަރު 4"
    ])
    fun testDv(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("ތެރޭގައި މިނިޓު 5"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("ކުރިން މިނިޓު 5"))
    }
}
