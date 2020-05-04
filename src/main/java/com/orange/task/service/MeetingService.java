package com.orange.task.service;

import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import com.orange.task.validations.MeetingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

@Service
public class MeetingService {

    private final MeetingCalculator meetingCalculator;

    @Autowired
    public MeetingService(MeetingCalculator meetingCalculator) {
        this.meetingCalculator = meetingCalculator;
    }

    public Set<Meeting> getMeetingSuggestions(Set<Calendar> calendarSet, LocalTime inputDuration){
        validateMeetingInInputCalendarSet(calendarSet);
        
        return meetingCalculator.calculateAvailableMeeting(
                calendarSet,
                inputDuration);
    }

    private void validateMeetingInInputCalendarSet(Set<Calendar> calendarSet){
        calendarSet.stream()
                .map(Calendar::getPlannedMeeting)
                .flatMap(Collection::stream)
                .forEach(inputMeeting -> {
                    MeetingValidator.validateMeetingFromValidationConstraints(inputMeeting);
                    MeetingValidator.validateStartAndEndTimes(inputMeeting);
                });

        calendarSet.stream()
                .map(Calendar::getWorkingHours)
                .forEach(inputMeeting -> {
                    MeetingValidator.validateMeetingFromValidationConstraints(inputMeeting);
                    MeetingValidator.validateStartAndEndTimes(inputMeeting);
                });
    }
}
