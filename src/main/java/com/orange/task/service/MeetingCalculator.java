package com.orange.task.service;

import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MeetingCalculator {

    private final WorkingTimeTogetherGenerator workingTimeTogetherGenerator;
    private final Set<Meeting> meetingSuggestions;

    private LocalTime currentTime;
    private LocalTime firstWorkingTimeTogether;
    private LocalTime lastWorkingTimeTogether;

    @Autowired
    public MeetingCalculator(WorkingTimeTogetherGenerator workingTimeTogetherGenerator) {
        this.workingTimeTogetherGenerator = workingTimeTogetherGenerator;
        this.meetingSuggestions = new HashSet<>();
    }

    public Set<Meeting> calculateAvailableMeeting(Set<Calendar> calendarSet, LocalTime inputDuration) {
        generateWorkingTimeTogetherByCalendarSet(calendarSet);
        currentTime = firstWorkingTimeTogether;

        while (isBeforeNextEvent(currentTime.plusSeconds(inputDuration.toSecondOfDay()), lastWorkingTimeTogether)
                && !currentTime.equals(lastWorkingTimeTogether)) {
            Set<Meeting> overlappingMeetingsByCurrentTime =
                    getOverlappingMeetingsByCurrentTime(calendarSet);

            currentTime = overlappingMeetingsByCurrentTime.isEmpty() ?
                    handleWhenOverlappingMeetingIsEmpty(calendarSet, inputDuration) :
                    getEndTimeOfLastMeeting(overlappingMeetingsByCurrentTime);
        }
        return meetingSuggestions;
    }

    private void generateWorkingTimeTogetherByCalendarSet(Set<Calendar> calendarSet){
        firstWorkingTimeTogether = workingTimeTogetherGenerator.generateFirstWorkingTimeTogether(calendarSet);
        lastWorkingTimeTogether = workingTimeTogetherGenerator.generateLastWorkingTimeTogether(calendarSet);
    }

    private LocalTime handleWhenOverlappingMeetingIsEmpty(Set<Calendar> calendarList, LocalTime inputDuration) {

        LocalTime endOfMeetingAtCurrentTime = currentTime.plusSeconds(inputDuration.toSecondOfDay());
        Meeting nextMeeting = getNextMeeting(calendarList);

        if (isBeforeNextEvent(endOfMeetingAtCurrentTime, nextMeeting.getStart())){
            meetingSuggestions.add(new Meeting(currentTime, endOfMeetingAtCurrentTime));
            return endOfMeetingAtCurrentTime;
        } else {
            return nextMeeting.getEnd();
        }
    }

    private Meeting getNextMeeting(Set<Calendar> calendarList) {
        return calendarList.stream()
                .map(Calendar::getPlannedMeeting)
                .flatMap(Collection::stream)
                .filter(meeting -> !meeting.getStart().isBefore(currentTime))
                .min((firstMeeting, secondMeeting) -> firstMeeting.getStart().isAfter(secondMeeting.getStart()) ? 1 : 0)
                .orElse(new Meeting(lastWorkingTimeTogether, lastWorkingTimeTogether));
    }

    private LocalTime getEndTimeOfLastMeeting(Set<Meeting> currentMeetings){
        return currentMeetings.stream()
                .map(Meeting::getEnd)
                .max(LocalTime::compareTo)
                .orElse(firstWorkingTimeTogether);
    }

    private Set<Meeting> getOverlappingMeetingsByCurrentTime(Set<Calendar> calendarList){
        return calendarList.stream()
                .map(Calendar::getPlannedMeeting)
                .flatMap(Collection::stream)
                .filter(meeting -> isBeforeNextEvent(meeting.getStart(), currentTime) &&
                        meeting.getEnd().isAfter(currentTime))
                .collect(Collectors.toSet());
    }

    private boolean isBeforeNextEvent(LocalTime timeChecked, LocalTime startTimeOfNextEvent) {
        return !timeChecked.isAfter(startTimeOfNextEvent);
    }
}
