package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.publisher.dto.UserDTO;
import ec.editer.amqp.publisher.service.IUserService;
import jakarta.validation.Valid;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/user/create")
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userRequest) throws SQLException{
        log.info("----- createUser for {} -----", userRequest.getUsername());
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword())); 
        return userService.create(userRequest)
                .map(user -> ResponseEntity.ok(user))
                .orElseThrow();
    }
}
