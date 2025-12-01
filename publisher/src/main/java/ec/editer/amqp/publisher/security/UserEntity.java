package ec.editer.amqp.publisher.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

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
