package com.orange.task.service;

import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Set;

@Service
public class MeetingService {

    private final MeetingCalculator meetingCalculator;

    @Autowired
    public MeetingService(MeetingCalculator meetingCalculator) {
        this.meetingCalculator = meetingCalculator;
    }

    public Set<Meeting> getMeetingSuggestions(Set<Calendar> calendarList, LocalTime inputDuration){
        return meetingCalculator.calculateAvailableMeeting(
                calendarList,
                inputDuration);
        //początkowo format daty przy deserializacji, potem do obsługi błedów własny, wewnątrzny
//        LocalTime.parse(inputDuration, dateTimeFormatter));
    }
}
