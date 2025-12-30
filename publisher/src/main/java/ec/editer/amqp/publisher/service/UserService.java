package ec.editer.amqp.publisher.service;

import ec.editer.amqp.publisher.dto.UpdateUserRequestDTO;
import ec.editer.amqp.publisher.dto.UserDTO;
import ec.editer.amqp.publisher.security.RolRepository;
import ec.editer.amqp.publisher.security.UserEntity;
import ec.editer.amqp.publisher.security.UserRepository;
import ec.editer.amqp.publisher.security.UserRolEntity;
import ec.editer.amqp.publisher.security.UserRolRepository;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public Optional<UserDTO> create(UserDTO userRequest) throws SQLException{
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

    @Override
    public UserDTO update(UpdateUserRequestDTO updateUserRequestDTO) {
        log.info("----- updating an user {} -----", updateUserRequestDTO.getUsername());
        return userRepository.findByUsername(updateUserRequestDTO.getUsername())
                .map(user ->{
            user.setActive(updateUserRequestDTO.isActive());
            userRepository.save(user);
                    UserDTO dto = new UserDTO();
                    BeanUtils.copyProperties(user,dto);
                    return dto;
        })
                .orElse(null);
    }

    @Override
    public List<UserDTO> findAll() {
        log.info("----- finding all users -----");
        return userRepository.findAll().stream()
                .map(user -> {
            UserDTO dto = new UserDTO();
            dto.setUsername(user.getUsername());
            dto.setActive(user.isActive());

            List<String> roles = user.getAuthorities().stream()
                    .map(authority -> {
                return authority.getRol().getName();
            })
                    .collect(Collectors.toList());

            dto.setRoles(roles);
            return dto;
        })
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRoles() {
        log.info("----- getting roles -----");
        return rolRepository.findAll().stream().map(rol -> rol.getName()).collect(Collectors.toList());
    }

    private UserDTO createUser(UserDTO userRequest){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setActive(userRequest.isActive());
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
        
        return userRequest;
    }
}
