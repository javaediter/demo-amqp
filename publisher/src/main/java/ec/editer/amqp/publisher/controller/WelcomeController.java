package ec.editer.amqp.publisher.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@CrossOrigin("/**")
@RestController
@RequestMapping("/welcome")
public class WelcomeController {
    
    @GetMapping
    public ResponseEntity<String> welcome(){
        log.info("----- welcome -----");
        return ResponseEntity.ok("Hello world!");
    }
}
