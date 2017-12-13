package cz.linkedlist

import cz.linkedlist.reltimek.humanize
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class HumanizeSpanishTest {

  val SPANISH: Locale = Locale.forLanguageTag("es")
  val US: Locale = Locale.US

  @Test
  fun testMinute() {
    val now = LocalDateTime.now()
    val minuteBefore = now.minusMinutes(1)
    Locale.setDefault(US)

    assertThat(Duration.between(now, minuteBefore).humanize(), equalTo("a minute"))
  }

  @Test
  fun testMinuteSpanishLocale() {
    val now = LocalDateTime.now()
    val minuteBefore = now.minusMinutes(1)

    val duration = Duration.between(now, minuteBefore)
    assertThat(duration.humanize(SPANISH), equalTo("un minuto"))
  }

  @Test
  fun testFewSeconds() {
    val now = LocalDateTime.now()
    val secondsBefore = now.minusSeconds(3)

    val duration = Duration.between(now, secondsBefore)
    assertThat(duration.humanize(US), equalTo("a few seconds"))
  }

}

