package ec.editer.amqp.consumer.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityUserDetailService implements UserDetailsService{
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("=====> loadUserByUsername for {} <=====", username);
        return userRepository.findByUsername(username)
                .map(user -> {
                    var authorities = user.getAuthorities()
                            .stream()
                            .map(rol -> new SimpleGrantedAuthority(rol.getRol().getName()))
                            .collect(Collectors.toList());
                    return new User(user.getUsername(), user.getPassword(), authorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
}
