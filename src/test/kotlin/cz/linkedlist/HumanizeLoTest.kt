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
class HumanizeLoTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("lo"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ບໍ່ເທົ່າໃດວິນາທີ",
        "SECONDS, 45, 45 ວິນາທີ",
        "SECONDS, 48, 1 ນາທີ",
        "MINUTES, 1, 1 ນາທີ",
        "MINUTES, 3, 3 ນາທີ",
        "MINUTES, 45, 1 ຊົ່ວໂມງ",
        "HOURS, 1, 1 ຊົ່ວໂມງ",
        "HOURS, 3, 3 ຊົ່ວໂມງ",
        "HOURS, 22, 1 ມື້",
        "DAYS, 1, 1 ມື້",
        "DAYS, 3, 3 ມື້",
        "DAYS, 26, 1 ເດືອນ",
        "MONTHS, 1, 1 ເດືອນ",
        "DAYS, 93, 3 ເດືອນ",
        "DAYS, 341, 1 ປີ",
        "YEARS, 1, 1 ປີ",
        "YEARS, 4, 4 ປີ"
    ])
    fun testLo(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("ອີກ 5 ນາທີ"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 ນາທີຜ່ານມາ"))
    }
}
