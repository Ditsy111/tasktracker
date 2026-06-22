package com.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import javax.sql.DataSource;


@RestController
@RequiredArgsConstructor
public class HealthController {

    private final DataSource dataSource;

    @GetMapping("/healthz")
    public Map<String, String> health() {

        return Map.of(
                "status",
                "ok"
        );
    }

    @GetMapping("/readyz")
    public ResponseEntity<?> ready() {

        try {

            dataSource.getConnection().isValid(2);

            return ResponseEntity.ok(
                    Map.of(
                            "status",
                            "ready"
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.status(
                    HttpStatus.SERVICE_UNAVAILABLE
            ).body(
                    Map.of(
                            "status",
                            "not-ready"
                    )
            );
        }
    }

}