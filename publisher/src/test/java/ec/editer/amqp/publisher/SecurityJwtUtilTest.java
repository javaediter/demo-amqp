package ec.editer.amqp.publisher;

import ec.editer.amqp.publisher.jwt.JwtUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Edison Teran
 */
@SpringBootTest(classes = {BCryptPasswordEncoder.class, JwtUtil.class})
public class SecurityJwtUtilTest {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private UserDetails user;
    
    @BeforeEach
    public void setUpUserDetails(){
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        user = new User("admin@gmail.com", "admin", authorities);
    }

    @Disabled
    @Test
    public void encoderPasswordTest(){
        String encoderPasswd = passwordEncoder.encode("admin");
        System.out.println("encoder: " + encoderPasswd);
        assertNotNull(encoderPasswd);
    }
    
    @Test
    public void generateTokenTest(){
        String token = jwtUtil.generateToken(user);
        System.out.println("token: " + token);
        assertNotNull(token);
    }

    @Test
    public void extractUsernameTest(){
        String token = jwtUtil.generateToken(user);
        String username = jwtUtil.extractUsername(token);
        System.out.println("username: " + username);
        assertNotNull(username);
    }

    @Test
    public void validateTokenTest(){
        String token = jwtUtil.generateToken(user);        
        boolean valid = jwtUtil.validateToked(token, user);
        System.out.println("valid: " + valid);
        assertTrue(valid);
    }
}
