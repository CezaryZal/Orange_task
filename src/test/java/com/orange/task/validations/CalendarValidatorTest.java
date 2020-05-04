package com.orange.task.validations;

import com.google.common.collect.Sets;
import com.orange.task.exceptions.invalid.InvalidCalendarListException;
import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalendarValidatorTest {

    @Test
    public void shouldThrowExceptionWhenSendingStartTimeFromPlannedMeetingBeforeStartTimeFromWorkingHours(){
        String expectedMessage = "The meetings are before start time working hour";

        Exception exception = assertThrows(InvalidCalendarListException.class, () -> {
            CalendarValidator.validatePlannedMeetingWithRespectToStartTimeOfWorkingHours(
                    new Calendar(new Meeting(LocalTime.of(10, 30), LocalTime.of(16, 0)),
                            Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                    new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                    new Meeting(LocalTime.of(15, 0), LocalTime.of(15, 30)))
                    ));
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionWhenSendingEndTimeFromPlannedMeetingAfterEndTimeFromWorkingHours(){
        String expectedMessage = "The meetings are after end time working hour";

        Exception exception = assertThrows(InvalidCalendarListException.class, () -> {
            CalendarValidator.validatePlannedMeetingWithRespectToEndTimeOfWorkingHours(
                    new Calendar(new Meeting(LocalTime.of(19, 30), LocalTime.of(16, 0)),
                            Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                    new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                    new Meeting(LocalTime.of(15, 0), LocalTime.of(16, 30)))
                    ));
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}