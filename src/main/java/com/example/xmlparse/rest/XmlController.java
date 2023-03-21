package com.example.xmlparse.rest;
import com.example.xmlparse.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class XmlController {

    private final DataService dataService;

    @Autowired
    public XmlController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<String> getXml(@RequestBody String xml) throws IOException {
        return ResponseEntity.ok().body(dataService.convert(xml));
    }
}
