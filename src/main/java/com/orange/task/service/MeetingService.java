package com.orange.task.service;

import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import com.orange.task.validations.CalendarValidator;
import com.orange.task.validations.MeetingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;

@Service
public class MeetingService {

    private final MeetingCalculator meetingCalculator;
    private final DateTimeFormatter dateTimeFormatter;

    @Autowired
    public MeetingService(MeetingCalculator meetingCalculator) {
        this.meetingCalculator = meetingCalculator;
        dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    }

    public Set<Meeting> getMeetingSuggestions(Set<Calendar> calendarSet, String inputDuration){
        validateMeetingByInputCalendarSet(calendarSet);
        validateCalendarByInputCalendarSet(calendarSet);
        
        return meetingCalculator.calculateAvailableMeeting(
                calendarSet,
                LocalTime.parse(inputDuration, dateTimeFormatter));
    }

    private void validateMeetingByInputCalendarSet(Set<Calendar> calendarSet){
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

    private void validateCalendarByInputCalendarSet(Set<Calendar> calendarSet){
        calendarSet.forEach(inputMeeting -> {
                    CalendarValidator.validateCalendarFromValidationConstraints(inputMeeting);
                    CalendarValidator.validatePlannedMeetingWithRespectToStartTimeOfWorkingHours(inputMeeting);
                    CalendarValidator.validatePlannedMeetingWithRespectToEndTimeOfWorkingHours(inputMeeting);
                });
    }
}
