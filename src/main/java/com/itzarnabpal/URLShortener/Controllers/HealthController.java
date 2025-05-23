package com.itzarnabpal.URLShortener.Controllers;

import com.itzarnabpal.URLShortener.Service.URLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final URLService urlService;

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return new ResponseEntity<>("Success: Service is Online", HttpStatus.OK);
    }
}
