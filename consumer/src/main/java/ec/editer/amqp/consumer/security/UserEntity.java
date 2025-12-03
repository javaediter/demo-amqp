package ec.editer.amqp.consumer.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 *
 * @author Edison Teran
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<UserRolEntity> authorities;
    
}
