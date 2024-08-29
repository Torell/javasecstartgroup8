package se.systementor.javasecstart.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    boolean existsByUsername(String username);

    Optional<AppUser> findByUsername(String username);

    @Query("UPDATE AppUser app_user SET app_user.failedAttempt = ?1 WHERE app_user.username = ?2")
    @Modifying
    int updateFailedAttempts(int failAttempts, String username);

}
