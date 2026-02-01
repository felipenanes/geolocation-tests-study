package nl.felipenanes.geoloc.user.internal.repository;

import nl.felipenanes.geoloc.user.internal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    default User findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User not found with email: " + email) //TODO: Extract message
        );
    }

}
