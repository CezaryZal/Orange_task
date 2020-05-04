package com.orange.task.validations;

import com.orange.task.exceptions.invalid.InvalidCalendarListException;
import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

public class CalendarValidator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validateCalendarFromValidationConstraints(final Calendar inputCalendar) {
        final Set<ConstraintViolation<Calendar>> violations = VALIDATOR.validate(inputCalendar);
        if (!violations.isEmpty()) {
            final String errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            throw new InvalidCalendarListException(errorMessages);
        }
    }

    public static void validatePlannedMeetingWithRespectToStartTimeOfWorkingHours(final Calendar calendar){
        LocalTime firstStartTimeFromPlannedMeeting = calendar.getPlannedMeeting().stream()
                .map(Meeting::getStart)
                .max(LocalTime::compareTo)
                .orElseThrow(() -> new InvalidCalendarListException("The planned meeting is empty"));

        if (!firstStartTimeFromPlannedMeeting.isBefore(calendar.getWorkingHours().getStart())){
            throw new InvalidCalendarListException("The meetings are before start time working hour");
        }
    }

    public static void validatePlannedMeetingWithRespectToEndTimeOfWorkingHours(final Calendar calendar){
        LocalTime lastEndTimeFromPlannedMeeting = calendar.getPlannedMeeting().stream()
                .map(Meeting::getEnd)
                .min(LocalTime::compareTo)
                .orElseThrow(() -> new InvalidCalendarListException("The planned meeting is empty"));

        if (!lastEndTimeFromPlannedMeeting.isAfter(calendar.getWorkingHours().getEnd())){
            throw new InvalidCalendarListException("The meetings are after end time working hour");
        }
    }
}
