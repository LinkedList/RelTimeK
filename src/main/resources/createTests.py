#!/usr/bin/python
import os
import csv
from string import Template

temp = Template(
'''package cz.linkedlist

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
class Humanize${langCap}Test {

    @BeforeEach
    fun setUp() {
        Locale.setDefault(Locale.forLanguageTag("${lang}"))
    }

    @ParameterizedTest(name = "{index} => minus {1} {0} = {2}")
    @CsvSource(value = [
        "SECONDS, 3, ${fewSeconds}",
        "SECONDS, 45, ${seconds45}",
        "SECONDS, 48, ${oneMinute}",
        "MINUTES, 1, ${oneMinute}",
        "MINUTES, 3, ${minutes3}",
        "MINUTES, 45, ${oneHour}",
        "HOURS, 1, ${oneHour}",
        "HOURS, 3, ${hours3}",
        "HOURS, 22, ${oneDay}",
        "DAYS, 1, ${oneDay}",
        "DAYS, 3, ${days3}",
        "DAYS, 26, ${oneMonth}",
        "MONTHS, 1, ${oneMonth}",
        "DAYS, 93, ${months3}",
        "DAYS, 341, ${oneYear}",
        "YEARS, 1, ${oneYear}",
        "YEARS, 4, ${years4}"
    ])
    fun test${langCap}(unit: ChronoUnit, value: Long, result: String) {
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

        assertThat(Duration.between(now, before).humanize(true), equalTo("${future5minutes}"))
    }

    @Test
    fun testPast() {
        val now = LocalDateTime.now()
        val before = now.minusMinutes(5)

        assertThat(Duration.between(now, before).humanize(true), equalTo("${past5minutes}"))
    }
}
'''
)

with open('simple.csv', 'rb') as csvfile:
    reader = csv.reader(csvfile)
    for row in reader:
        lang = row[0]
        langCap= row[0].title()
        future = row[1]
        past = row[2]
        s = row[3]
        ss = row[4]
        m = row[5]
        mm = row[6]
        h = row[7]
        hh = row[8]
        d = row[9]
        dd = row[10]
        M = row[11]
        MM = row[12]
        y = row[13]
        yy = row[14]

        filled = temp.substitute(
                lang=lang,
                langCap=langCap,
                fewSeconds=s,
                seconds45=ss.replace("%d", "45"),
                oneMinute=m,
                minutes3=mm.replace("%d", "3"),
                oneHour=h,
                hours3=hh.replace("%d", "3"),
                oneDay=d,
                days3=dd.replace("%d", "3"),
                oneMonth=M,
                months3=MM.replace("%d", "3"),
                oneYear=y,
                years4=yy.replace("%d", "4"),
                future5minutes=future.replace("%s", mm.replace("%d", "5")),
                past5minutes=past.replace("%s", mm.replace("%d", "5"))
                )
        filename="Humanize"+langCap+"Test.kt"
        with open(filename, "w") as ktFile:
            ktFile.write(filled)
    

