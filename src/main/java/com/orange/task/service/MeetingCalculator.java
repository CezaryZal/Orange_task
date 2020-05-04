package com.orange.task.service;

import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MeetingCalculator {

    private final WorkingTimeTogetherGenerator workingTimeTogetherGenerator;

    private LocalTime firstWorkingTimeTogether;
    private LocalTime lastWorkingTimeTogether;

    @Autowired
    public MeetingCalculator(WorkingTimeTogetherGenerator workingTimeTogetherGenerator) {
        this.workingTimeTogetherGenerator = workingTimeTogetherGenerator;
    }

    public Set<Meeting> calculateAvailableMeeting(Set<Calendar> calendarSet, LocalTime inputDuration) {
        generateWorkingTimeTogetherByCalendarSet(calendarSet);

        return new HashSet<>(Arrays.asList(new Meeting(firstWorkingTimeTogether, lastWorkingTimeTogether)));
    }

    private void generateWorkingTimeTogetherByCalendarSet(Set<Calendar> calendarSet){
        firstWorkingTimeTogether = workingTimeTogetherGenerator.generateFirstWorkingTimeTogether(calendarSet);
        lastWorkingTimeTogether = workingTimeTogetherGenerator.generateLastWorkingTimeTogether(calendarSet);
    }
}
