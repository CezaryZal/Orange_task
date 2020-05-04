package com.orange.task.service;

import com.google.common.collect.Sets;
import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

class WorkingTimeTogetherGeneratorTest {

    private final WorkingTimeTogetherGenerator workingTimeTogetherGenerator = new WorkingTimeTogetherGenerator();
    private final HashSet<Calendar> sampleCollectionOfCalendar = Sets.newHashSet(
            new Calendar(new Meeting(LocalTime.of(9, 30), LocalTime.of(16, 0)),
                    Sets.newHashSet(new Meeting(LocalTime.of(13, 30), LocalTime.of(14, 30)),
                            new Meeting(LocalTime.of(15, 0), LocalTime.of(15, 30)))),
            new Calendar(new Meeting(LocalTime.of(8, 0), LocalTime.of(18, 0)),
                    Sets.newHashSet(new Meeting(LocalTime.of(9, 30), LocalTime.of(11, 30)),
                            new Meeting(LocalTime.of(14, 0), LocalTime.of(17, 30)))
            ));

    @Test
    public void shouldGenerateCorrectFirstWorkingTimeFromCollect() {
        LocalTime expectedFirstWorkingTimeTogether = LocalTime.of(9, 30);

        LocalTime actualFirstWorkingTimeTogether =
                workingTimeTogetherGenerator.generateFirstWorkingTimeTogether(sampleCollectionOfCalendar);

        assertThat(expectedFirstWorkingTimeTogether).isEqualTo(actualFirstWorkingTimeTogether);

    }

    @Test
    public void shouldGenerateCorrectLastWorkingTimeFromCollect() {
        LocalTime expectedLastWorkingTimeTogether = LocalTime.of(16, 0);

        LocalTime actualLastWorkingTimeTogether =
                workingTimeTogetherGenerator.generateLastWorkingTimeTogether(sampleCollectionOfCalendar);

        assertThat(expectedLastWorkingTimeTogether).isEqualTo(actualLastWorkingTimeTogether);
    }

}