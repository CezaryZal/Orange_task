package com.orange.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @GetMapping("/{inputString}")
    public ResponseEntity<String> testEndpoint(@PathVariable String inputString){
        return new ResponseEntity<>(inputString, HttpStatus.OK);
    }
}
