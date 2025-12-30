package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.publisher.dto.UpdateUserRequestDTO;
import ec.editer.amqp.publisher.dto.UserDTO;
import ec.editer.amqp.publisher.service.IUserService;
import jakarta.validation.Valid;
import java.sql.SQLException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles(){
        log.info("----- getting roles -----");
        return ResponseEntity.ok(userService.getRoles());
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.info("----- getAllUsers -----");
        return ResponseEntity.ok(userService.findAll());
    }
    
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userRequest) throws SQLException{
        log.info("----- createUser for {} -----", userRequest.getUsername());
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword())); 
        return userService.create(userRequest)
                .map(user -> ResponseEntity.ok(user))
                .orElseThrow();
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserRequestDTO userRequest){
        log.info("----- updateUser for {} -----", userRequest.getUsername());
        return ResponseEntity.ok(userService.update(userRequest));
    }
}
