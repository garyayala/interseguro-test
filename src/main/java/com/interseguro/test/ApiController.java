package com.interseguro.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.interseguro.test.services.ApiService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interseguro")
@CrossOrigin(origins = "*")
public class ApiController {
    @Autowired
    private ApiService apiService;

    @GetMapping
    public ResponseEntity<Map> getApi(){
        return ResponseEntity.ok(this.apiService.getSessionKey());
    }
}
