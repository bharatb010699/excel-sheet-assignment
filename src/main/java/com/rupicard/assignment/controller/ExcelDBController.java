package com.rupicard.assignment.controller;

import com.rupicard.assignment.service.ExcelDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/db")
public class ExcelDBController {

    @Autowired
    private ExcelDBService service;

    @PostMapping("/saveToSheet")
    public ResponseEntity<String> saveDetailsOnGoogleSheet
            (@RequestParam(value = "name") String name, @RequestParam(value = "mobile") String mobileNumber) {

        log.info("Received the following details - Name: {}, Mobile number: {}", name, mobileNumber);
        try {
            service.saveDetails(name, mobileNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
