package com.candido.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/docker/test")
public class ControllerTest {

    @GetMapping
    public ResponseEntity<String> testDocker() {
        return ResponseEntity.ok("It works.");
    }

}
