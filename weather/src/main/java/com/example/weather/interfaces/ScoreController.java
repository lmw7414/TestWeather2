package com.example.weather.interfaces;

import com.example.weather.domain.ScoreFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreController {
    @PostMapping("/weathers/score")
    public ResponseEntity<?> calScore(@RequestBody ScoreFilter resource){
        return null;
    }
}
