package com.orange.task.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class MeetingTest {

    @Test
    public void shouldCompareToSimilarMeetingObject(){
        Meeting expectedMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(15, 30));
        Meeting newMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(15, 30));

        assertThat(expectedMeeting).isEqualTo(newMeeting);
    }

    @Test
    public void shouldReturnNotEqualMeetingObjectWithAnotherMeetingObject(){
        Meeting expectedMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(15, 30));
        Meeting anotherMeeting = new Meeting(LocalTime.of(11, 10), LocalTime.of(14, 30));

        assertThat(expectedMeeting).isNotEqualTo(anotherMeeting);
    }

}