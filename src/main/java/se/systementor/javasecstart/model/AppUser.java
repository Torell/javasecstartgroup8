package se.systementor.javasecstart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.systementor.javasecstart.utils.RandomSelector;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "App_User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(unique = true)
    @Nonnull
    private String username;

    @Size()
    @Nonnull
    private String password;

    @Transient
    @JsonIgnore
    private final RandomSelector randomSelector = new RandomSelector("static/images/accounts");

    private String profileImage;

    private boolean isAccountNonLocked;

    private boolean enabled;

    private int failedAttempt;

    private LocalDateTime lockTime;

    public AppUser(UUID id, @Nonnull String username, @Nonnull String password, boolean isAccountNonLocked, boolean enabled, int failedAttempt, LocalDateTime lockTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profileImage = "/images/accounts/" + randomSelector.getRandomImage();
        this.isAccountNonLocked = isAccountNonLocked;
        this.enabled = enabled;
        this.failedAttempt = failedAttempt;
        this.lockTime = lockTime;
    }
}
