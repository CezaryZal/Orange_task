package com.orange.task.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;


class CalendarTest {

    @Test
    public void shouldCompareToSimilarCalendarObject(){
        Calendar expectedCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45))));

        Calendar newCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45))));

        assertThat(expectedCalendar).isEqualTo(newCalendar);
    }

    @Test
    public void shouldReturnNotEqualCalendarObjectWithAnotherCalendarObject(){
        Calendar expectedCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45))));

        Calendar newCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 30), LocalTime.of(18, 0)),
                Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45))));

        assertThat(expectedCalendar).isNotEqualTo(newCalendar);
    }


}