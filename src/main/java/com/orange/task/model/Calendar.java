package com.orange.task.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

public class Calendar {

    @NotNull(message = "The 'workingHours' should not be null")
    private final Meeting workingHours;

    @NotNull(message = "The 'plannedMeeting' should not be null")
    @NotEmpty(message = "The 'plannedMeeting' must not be empty" )
    private final Set<Meeting> plannedMeeting;

    public Calendar(Meeting workingHours, Set<Meeting> plannedMeeting) {
        this.workingHours = workingHours;
        this.plannedMeeting = plannedMeeting;
    }

    public Meeting getWorkingHours() {
        return workingHours;
    }

    public Set<Meeting> getPlannedMeeting() {
        return plannedMeeting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Calendar)) return false;
        Calendar calendar = (Calendar) o;
        return Objects.equals(getWorkingHours(), calendar.getWorkingHours()) &&
                Objects.equals(getPlannedMeeting(), calendar.getPlannedMeeting());
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
