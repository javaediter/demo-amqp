package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.dto.UserDTO;
import ec.editer.amqp.publisher.security.RolRepository;
import ec.editer.amqp.publisher.security.UserEntity;
import ec.editer.amqp.publisher.security.UserRepository;
import ec.editer.amqp.publisher.security.UserRolEntity;
import ec.editer.amqp.publisher.security.UserRolRepository;
import java.sql.SQLException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edison Teran
 */
@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class UserService implements IUserService{
    
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final UserRolRepository userRolRepository;
    
    @Override
    public Optional<UserEntity> create(UserDTO userRequest) throws SQLException{
        log.info("----- creating a new user -----");
        try{
            if(userRepository.findByUsername(userRequest.getUsername()).isPresent()){
                throw new SQLException(String.format("Username %s already exists", userRequest.getUsername()));
            }
            return Optional.of(createUser(userRequest));
        }catch(SQLException ex){
            log.error("*****ERROR: {}", ex.getMessage());
            throw ex;
        }
    }
    
    private UserEntity createUser(UserDTO userRequest){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(userRequest.getPassword());
        userRepository.save(userEntity);

        userRequest.getRoles()
                .forEach(newRol -> {
                    rolRepository.findByName("ROLE_" + newRol.toUpperCase())
                    .map(rol -> {
                        UserRolEntity userRolEntity = new UserRolEntity();
                        userRolEntity.setUser(userEntity);
                        userRolEntity.setRol(rol);
                        userRolRepository.save(userRolEntity);
                        return rol;
                    });
        });
        
        return userEntity;
    }
}
