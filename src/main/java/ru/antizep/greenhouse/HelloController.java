package ru.antizep.greenhouse;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "Greenhouse System Online! Time: " + LocalDateTime.now();
    }
}
