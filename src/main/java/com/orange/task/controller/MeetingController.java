package com.orange.task.controller;

import com.orange.task.model.Calendar;
import com.orange.task.model.Meeting;
import com.orange.task.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Set;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/calculate/{inputDuration}")
    public ResponseEntity<Set<Meeting>> getAvailableTimesOfMeeting(
            @RequestBody Set<Calendar> inputCalendars,
            @PathVariable @DateTimeFormat(pattern = "HH:mm") LocalTime inputDuration) {
        return new ResponseEntity<>(meetingService.getMeetingSuggestions(inputCalendars, inputDuration), HttpStatus.OK);
    }
}
