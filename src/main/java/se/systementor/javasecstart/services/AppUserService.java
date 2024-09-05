package se.systementor.javasecstart.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.systementor.javasecstart.model.AppUser;
import se.systementor.javasecstart.model.AppUserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AppUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserService.class);

    @Autowired
    private AppUserRepository appUserRepository;

    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public AppUser findUserById(UUID id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Konto med id '%s' finns inte.".formatted(id)));
    }

    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Konto med användarnamn '%s' finns inte.".formatted(username)));
    }

    public boolean addUser(AppUser user) {
        return appUserRepository.findByUsername(user.getUsername())
                .map(foundUser -> {
                    LOGGER.warn("Username '{}' is already taken.", foundUser.getUsername());
                    return false;
                })
                .orElseGet(() -> {
                    user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                    user.setAccountNonLocked(true);
                    user.setEnabled(true);
                    appUserRepository.save(user);
                    LOGGER.info("User '{}' was added.", user.getUsername());
                    return true;
                });
    }

    public void increaseFailedAttempts(AppUser appUser) {
        int attempt = appUser.getFailedAttempt() + 1;
        appUserRepository.updateFailedAttempts(attempt, appUser.getUsername());
    }

    public void resetFailedAttempts(String email) {
        appUserRepository.updateFailedAttempts(0, email);
    }

    public void lockAppUser(AppUser appUser) {
        appUser.setAccountNonLocked(false);
        appUser.setLockTime(LocalDateTime.now());

        appUserRepository.save(appUser);
    }

    public boolean unlockWhenTimeExpired(AppUser appUser) {
        var lockTime = appUser.getLockTime();
        var currentTime = LocalDateTime.now();

        if (lockTime.plusDays(1).isBefore(currentTime)) {
            appUser.setAccountNonLocked(true);
            appUser.setLockTime(null);
            appUser.setFailedAttempt(0);

            appUserRepository.save(appUser);
            return true;
        }
        return false;
    }
}
