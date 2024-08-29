package se.systementor.javasecstart.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import se.systementor.javasecstart.model.AppUser;
import se.systementor.javasecstart.services.AppUserService;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AppUserService appUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        AppUser appUser = appUserService.findUserByUsername(appUserDetails.getUsername());
        if (appUser.getFailedAttempt() > 0) {
            appUserService.resetFailedAttempts(appUser.getUsername());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}


