package se.systementor.javasecstart.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import se.systementor.javasecstart.model.AppUser;
import se.systementor.javasecstart.services.AppUserService;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private AppUserService appUserService;

    private static final int MAX_FAILED_ATTEMPTS = 5;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws ServletException, IOException {
        String username = request.getParameter("username");

        if (!appUserService.existsByUsername(username)) {
            exception = new BadCredentialsException("Detta konto finns inte");
            super.setDefaultFailureUrl("/login?error");
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        AppUser appUser = appUserService.findUserByUsername(username);

        if (appUser.isEnabled() && appUser.isAccountNonLocked()) {
            if (appUser.getFailedAttempt() <= MAX_FAILED_ATTEMPTS) {
                appUserService.increaseFailedAttempts(appUser);
            } else {
                appUserService.lockAppUser(appUser);
                exception = new LockedException(
                        """
                                Ditt konto är låst på grund av flera misslyckade försök.
                                Kontakta kundtjänst.""");
            }
        } else if (!appUser.isAccountNonLocked()) {
            if (appUserService.unlockWhenTimeExpired(appUser)) {
                exception = new LockedException("Ditt konto har blivit upplåst.");
            }
        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }


//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
//        String username = request.getParameter("username");
//        String url = appUserService.existsByUsername(username)
//                ? "/login?error=invalidPassword"
//                : "/login?error=usernameNotFound";
//        try {
//            response.sendRedirect(url);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}