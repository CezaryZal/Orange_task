package com.orange.task.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class MeetingTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldCompareToSimilarMeetingObject(){
        Meeting expectedMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(15, 30));
        Meeting newMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(15, 30));

        assertThat(expectedMeeting).isEqualTo(newMeeting);
    }

    @Test
    public void shouldReturnNotEqualMeetingObjectWithAnotherMeetingObject(){
        Meeting expectedMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(15, 30));
        Meeting anotherMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(14, 30));

        assertThat(expectedMeeting).isNotEqualTo(anotherMeeting);
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToStart(){
        Set<ConstraintViolation<Meeting>> constraintViolations =
                validator.validateValue(Meeting.class, "start", null);

        assertThat(1).isEqualTo(constraintViolations.size());
        assertThat("The 'start' should not be null")
                .isEqualTo(constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToEnd(){
        Set<ConstraintViolation<Meeting>> constraintViolations =
                validator.validateValue(Meeting.class, "end", null);

        assertThat(1).isEqualTo(constraintViolations.size());
        assertThat("The 'end' should not be null")
                .isEqualTo(constraintViolations.iterator().next().getMessage());
    }

}