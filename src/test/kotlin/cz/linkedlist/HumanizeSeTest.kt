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
class HumanizeSeTest {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("se"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, moadde sekunddat",
        "SECONDS, 45, 45 sekunddat",
        "SECONDS, 48, okta minuhta",
        "MINUTES, 1, okta minuhta",
        "MINUTES, 3, 3 minuhtat",
        "MINUTES, 45, okta diimmu",
        "HOURS, 1, okta diimmu",
        "HOURS, 3, 3 diimmut",
        "HOURS, 22, okta beaivi",
        "DAYS, 1, okta beaivi",
        "DAYS, 3, 3 beaivvit",
        "DAYS, 26, okta mánnu",
        "MONTHS, 1, okta mánnu",
        "DAYS, 93, 3 mánut",
        "DAYS, 341, okta jahki",
        "YEARS, 1, okta jahki",
        "YEARS, 4, 4 jagit"
    ])
    fun testSe(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("5 minuhtat geažes"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("maŋit 5 minuhtat"))
    }
}
