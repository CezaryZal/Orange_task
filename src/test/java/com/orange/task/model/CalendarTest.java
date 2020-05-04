package com.orange.task.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;


class CalendarTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldCompareToSimilarCalendarObject(){
        Calendar expectedCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                new HashSet<>(Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45)))));

        Calendar newCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                new HashSet<>(Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45)))));

        assertThat(expectedCalendar).isEqualTo(newCalendar);
    }

    @Test
    public void shouldReturnNotEqualCalendarObjectWithAnotherCalendarObject(){
        Calendar expectedCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                new HashSet<>(Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45)))));

        Calendar newCalendar = new Calendar(
                new Meeting(LocalTime.of(9, 30), LocalTime.of(18, 0)),
                new HashSet<>(Arrays.asList(new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                        new Meeting(LocalTime.of(14, 30), LocalTime.of(14, 45)))));

        assertThat(expectedCalendar).isNotEqualTo(newCalendar);
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToWorkingHours(){
        Set<ConstraintViolation<Calendar>> constraintViolations =
                validator.validateValue(Calendar.class, "workingHours", null);

        assertThat(1).isEqualTo(constraintViolations.size());
        assertThat("The 'workingHours' should not be null")
                .isEqualTo(constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToPlannedMeeting(){
        Set<ConstraintViolation<Calendar>> constraintViolations =
                validator.validateValue(Calendar.class, "plannedMeeting", null);

        assertThat(2).isEqualTo(constraintViolations.size());
        assertThat("The 'plannedMeeting' should not be null")
                .isEqualTo(constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSendingEmptyPlannedMeeting(){
        Set<ConstraintViolation<Calendar>> constraintViolations =
                validator.validateValue(Calendar.class, "plannedMeeting", new HashSet<>());

        assertThat(1).isEqualTo(constraintViolations.size());
        assertThat("The 'plannedMeeting' must not be empty")
                .isEqualTo(constraintViolations.iterator().next().getMessage());
    }

}