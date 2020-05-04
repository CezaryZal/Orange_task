package com.orange.task.service;

import com.google.common.collect.Sets;
import com.orange.task.exceptions.invalid.InvalidMeetingException;
import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @Spy
    private WorkingTimeTogetherGenerator workingTimeTogetherGenerator;

    @InjectMocks
    private MeetingCalculator meetingCalculator;

    private MeetingService meetingService;
    private HashSet<Calendar> sampleCollectionOfCalendar;

    @BeforeEach
    void setUp() {
        meetingService = new MeetingService(meetingCalculator);
//        sampleCollectionOfCalendar = Sets.newHashSet(
//                new Calendar(new Meeting(LocalTime.of(9, 30), LocalTime.of(16, 0)),
//                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
//                                new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
//                                new Meeting(LocalTime.of(15, 30), LocalTime.of(16, 0)))),
//                new Calendar(new Meeting(LocalTime.of(8, 0), LocalTime.of(18, 0)),
//                        Sets.newHashSet(new Meeting(LocalTime.of(9, 0), LocalTime.of(11, 0)),
//                                new Meeting(LocalTime.of(12, 30), LocalTime.of(13, 0)),
//                                new Meeting(LocalTime.of(14, 30), LocalTime.of(16, 0)),
//                                new Meeting(LocalTime.of(16, 0), LocalTime.of(17, 30)))
//                ));
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToStartTimeToPlannedMeeting(){
        String expectedMessage = "The 'start' should not be null";
        sampleCollectionOfCalendar = Sets.newHashSet(
                new Calendar(new Meeting(LocalTime.of(9, 30), LocalTime.of(16, 0)),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                new Meeting(null, LocalTime.of(15, 30)))
                ));

        Exception exception = assertThrows(InvalidMeetingException.class, () -> {
            meetingService.getMeetingSuggestions(sampleCollectionOfCalendar, "10:11");
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToEndTimeToPlannedMeeting(){
        String expectedMessage = "The 'end' should not be null";
        sampleCollectionOfCalendar = Sets.newHashSet(
                new Calendar(new Meeting(LocalTime.of(9, 30), LocalTime.of(16, 0)),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                new Meeting(LocalTime.of(15, 30), null))
                ));

        Exception exception = assertThrows(InvalidMeetingException.class, () -> {
            meetingService.getMeetingSuggestions(sampleCollectionOfCalendar, "10:11");
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionWhenSendingEndTimeIsBeforeStartTimeInPlannedMeeting(){
        String expectedMessage = "The end time of Meeting is before start time.";
        sampleCollectionOfCalendar = Sets.newHashSet(
                new Calendar(new Meeting(LocalTime.of(9, 30), LocalTime.of(16, 0)),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                new Meeting(LocalTime.of(15, 30), LocalTime.of(11, 30)))
                ));

        Exception exception = assertThrows(InvalidMeetingException.class, () -> {
            meetingService.getMeetingSuggestions(sampleCollectionOfCalendar, "10:11");
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToStartTimeToWorkingHours(){
        String expectedMessage = "The 'start' should not be null";
        sampleCollectionOfCalendar = Sets.newHashSet(
                new Calendar(new Meeting(null, LocalTime.of(16, 0)),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                new Meeting(LocalTime.of(14, 30), LocalTime.of(15, 30)))
                ));

        Exception exception = assertThrows(InvalidMeetingException.class, () -> {
            meetingService.getMeetingSuggestions(sampleCollectionOfCalendar, "10:11");
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionWhenSendingNullToEndTimeToWorkingHours(){
        String expectedMessage = "The 'end' should not be null";
        sampleCollectionOfCalendar = Sets.newHashSet(
                new Calendar(new Meeting(LocalTime.of(9, 30), null),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                new Meeting(LocalTime.of(15, 30), LocalTime.of(16, 30)))
                ));

        Exception exception = assertThrows(InvalidMeetingException.class, () -> {
            meetingService.getMeetingSuggestions(sampleCollectionOfCalendar, "10:11");
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldThrowExceptionWhenSendingEndTimeIsBeforeStartTimeInWorkingHours(){
        String expectedMessage = "The end time of Meeting is before start time.";
        sampleCollectionOfCalendar = Sets.newHashSet(
                new Calendar(new Meeting(LocalTime.of(16, 30), LocalTime.of(16, 0)),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)))
                ));

        Exception exception = assertThrows(InvalidMeetingException.class, () -> {
            meetingService.getMeetingSuggestions(sampleCollectionOfCalendar, "10:11");
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}