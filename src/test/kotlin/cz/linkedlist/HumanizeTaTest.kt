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
class HumanizeTaTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("ta"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ஒரு சில விநாடிகள்",
        "SECONDS, 45, 45 விநாடிகள்",
        "SECONDS, 48, ஒரு நிமிடம்",
        "MINUTES, 1, ஒரு நிமிடம்",
        "MINUTES, 3, 3 நிமிடங்கள்",
        "MINUTES, 45, ஒரு மணி நேரம்",
        "HOURS, 1, ஒரு மணி நேரம்",
        "HOURS, 3, 3 மணி நேரம்",
        "HOURS, 22, ஒரு நாள்",
        "DAYS, 1, ஒரு நாள்",
        "DAYS, 3, 3 நாட்கள்",
        "DAYS, 26, ஒரு மாதம்",
        "MONTHS, 1, ஒரு மாதம்",
        "DAYS, 93, 3 மாதங்கள்",
        "DAYS, 341, ஒரு வருடம்",
        "YEARS, 1, ஒரு வருடம்",
        "YEARS, 4, 4 ஆண்டுகள்"
    ])
    fun testTa(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 நிமிடங்கள் இல்"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 நிமிடங்கள் முன்"))
    }
}
