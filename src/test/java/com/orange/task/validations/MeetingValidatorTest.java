package com.orange.task.validations;

import com.orange.task.exceptions.invalid.InvalidMeetingException;
import com.orange.task.model.Meeting;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MeetingValidatorTest {

    @Test
    public void shouldThrowExceptionWhenSendingEndTimeIsBeforeStartTime(){
        String expectedMessage = "The end time of Meeting is before start time.";

        Exception exception = assertThrows(InvalidMeetingException.class, () -> {
            MeetingValidator.validateStartAndEndTimes(
                    new Meeting(LocalTime.of(12,10), LocalTime.of(10,0)));
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}