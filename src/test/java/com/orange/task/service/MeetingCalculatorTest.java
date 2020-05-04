package com.orange.task.service;

import com.google.common.collect.Sets;
import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MeetingCalculatorTest {

    @Spy
    private WorkingTimeTogetherGenerator workingTimeTogetherGenerator;

    private MeetingCalculator meetingCalculator;
    private HashSet<Calendar> sampleCollectionOfCalendar;

    @BeforeEach
    void setUp() {
        meetingCalculator = new MeetingCalculator(workingTimeTogetherGenerator);
        sampleCollectionOfCalendar = Sets.newHashSet(
                new Calendar(new Meeting(LocalTime.of(9, 30), LocalTime.of(16, 0)),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(10, 0)),
                                new Meeting(LocalTime.of(11, 30), LocalTime.of(13, 30)),
                                new Meeting(LocalTime.of(15, 30), LocalTime.of(16, 0)))),
                new Calendar(new Meeting(LocalTime.of(8, 0), LocalTime.of(18, 0)),
                        Sets.newHashSet(new Meeting(LocalTime.of(9, 0), LocalTime.of(11, 0)),
                                new Meeting(LocalTime.of(12, 30), LocalTime.of(13, 0)),
                                new Meeting(LocalTime.of(14, 30), LocalTime.of(16, 0)),
                                new Meeting(LocalTime.of(16, 0), LocalTime.of(17, 30)))
                ));
    }

    @Test
    public void shouldCalculateAvailableMeetingFor30Minute() {
        HashSet<Meeting> expectedMeetings = Sets.newHashSet(
                new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 30)),
                new Meeting(LocalTime.of(13, 30), LocalTime.of(14, 0)),
                new Meeting(LocalTime.of(14, 0), LocalTime.of(14, 30)));

        Set<Meeting> actualMeetings = meetingCalculator.calculateAvailableMeeting(
                sampleCollectionOfCalendar,
                LocalTime.of(0, 30));

        assertThat(expectedMeetings).isEqualTo(actualMeetings);
    }

    @Test
    public void shouldCalculateAvailableMeetingFor20Minute() {
        HashSet<Meeting> expectedMeetings = Sets.newHashSet(
                new Meeting(LocalTime.of(11, 0), LocalTime.of(11, 20)),
                new Meeting(LocalTime.of(13, 30), LocalTime.of(13, 50)),
                new Meeting(LocalTime.of(13, 50), LocalTime.of(14, 10)),
                new Meeting(LocalTime.of(14, 10), LocalTime.of(14, 30)));

        Set<Meeting> actualMeetings = meetingCalculator.calculateAvailableMeeting(
                sampleCollectionOfCalendar,
                LocalTime.of(0, 20));

        assertThat(expectedMeetings).isEqualTo(actualMeetings);
    }

}