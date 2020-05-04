package com.orange.task.validations;

import com.orange.task.exceptions.invalid.InvalidMeetingException;
import com.orange.task.model.Meeting;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class MeetingValidator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validateMeetingFromValidationConstraints(final Meeting inputMeeting) {
        final Set<ConstraintViolation<Meeting>> violations = VALIDATOR.validate(inputMeeting);
        if (!violations.isEmpty()) {
            final String errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            throw new InvalidMeetingException(errorMessages);
        }
    }

    public static void validateStartAndEndTimes(Meeting meeting){
        if (!meeting.getStart().isBefore(meeting.getEnd())){
            throw new InvalidMeetingException("The end time of Meeting is before start time.");
        }
    }
}
