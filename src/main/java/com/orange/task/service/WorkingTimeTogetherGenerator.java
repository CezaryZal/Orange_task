package com.orange.task.service;

import com.orange.task.exceptions.invalid.InvalidMeetingException;
import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Set;

@Service
public class WorkingTimeTogetherGenerator {

    public LocalTime generateFirstWorkingTimeTogether(Set<Calendar> calendarList) {
        return calendarList.stream()
                .map(Calendar::getWorkingHours)
                .map(Meeting::getStart)
                .max(Comparator.naturalOrder())
                .orElseThrow(() ->
                        new InvalidMeetingException("Something is wrong with the start time of work"));
    }

    public LocalTime generateLastWorkingTimeTogether(Set<Calendar> calendarList) {
        return calendarList.stream()
                .map(Calendar::getWorkingHours)
                .map(Meeting::getEnd)
                .min(Comparator.naturalOrder())
                .orElseThrow(() ->
                        new InvalidMeetingException("Something is wrong with the end time of work"));
    }
}
