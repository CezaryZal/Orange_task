package com.orange.task.service;

import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MeetingService {


    public Set<Meeting> getMeetingSuggestions(Set<Calendar> calendarList, LocalTime inputDuration){
        return new HashSet<>(Arrays.asList(null, null));

    }
}
