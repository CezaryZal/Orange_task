package com.orange.task.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Objects;

public class Meeting {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @NotNull(message = "The 'start' should not be null")
    private final LocalTime start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @NotNull(message = "The 'end' should not be null")
    private final LocalTime end;

    public Meeting(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return getStart().equals(meeting.getStart()) &&
                getEnd().equals(meeting.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
