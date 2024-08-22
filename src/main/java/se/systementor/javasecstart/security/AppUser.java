package se.systementor.javasecstart.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.systementor.javasecstart.utils.RandomSelector;

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

    private String profileImage = "/images/accounts/" + randomSelector.getRandomImage();

}
