package com.example.xmlparse.rest;
import com.example.xmlparse.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<String> getXml(@RequestBody String xml) throws IOException {
        return ResponseEntity.ok().body(dataService.convert(xml));
    }
}
