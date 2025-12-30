package ec.editer.amqp.publisher.controller;

import ec.editer.amqp.publisher.dto.AuthRequestDTO;
import ec.editer.amqp.publisher.dto.AuthResponseDTO;
import ec.editer.amqp.publisher.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@RequestMapping("/api/auth")
public class LoginController {
    
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        log.info("----- login for {} -----", authRequestDTO.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDTO.getUsername(),
                authRequestDTO.getPassword()
        ));
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
