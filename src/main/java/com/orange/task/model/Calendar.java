package com.orange.task.model;

import java.util.List;
import java.util.Objects;

public class Calendar {

    private Meeting workingHours;
    private List<Meeting> plannedMeeting;

    public Calendar(Meeting workingHours, List<Meeting> plannedMeeting) {
        this.workingHours = workingHours;
        this.plannedMeeting = plannedMeeting;
    }

    public Meeting getWorkingHours() {
        return workingHours;
    }

    public List<Meeting> getPlannedMeeting() {
        return plannedMeeting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Calendar)) return false;
        Calendar calendar = (Calendar) o;
        return getWorkingHours().equals(calendar.getWorkingHours()) &&
                getPlannedMeeting().equals(calendar.getPlannedMeeting());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkingHours(), getPlannedMeeting());
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "working_hours=" + workingHours +
                ", planned_meeting=" + plannedMeeting +
                '}';
    }
}
