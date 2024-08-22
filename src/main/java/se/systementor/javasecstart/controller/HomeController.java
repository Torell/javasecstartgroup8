package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.systementor.javasecstart.security.AppUserService;
import se.systementor.javasecstart.security.IAuthenticationFacade;

@Controller
public class HomeController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AppUserService userService;

    @GetMapping(path = "/")
    String empty(Model model) {

//        setupVersion(model);
//        model.addAttribute("dogs", dogRepository.findAll());

        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            String profileImage = userService.findUserByUsername(authentication.getName()).getProfileImage();
            model.addAttribute("profileImage", profileImage);
        }
        model.addAttribute("activeFunction", "home");

        return "home";
    }
}
