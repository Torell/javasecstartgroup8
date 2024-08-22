package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.systementor.javasecstart.security.AppUserService;
import se.systementor.javasecstart.security.IAuthenticationFacade;
import se.systementor.javasecstart.services.DogService;

@Controller
public class AdminDogController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AppUserService userService;

    @Autowired
    private DogService dogService;

    @GetMapping(path="/admin/dogs")
    String list(Model model){

//        setupVersion(model);

        Authentication authentication = authenticationFacade.getAuthentication();
        String profileImage = userService.findUserByUsername(authentication.getName()).getProfileImage();
        model.addAttribute("profileImage", profileImage);
        model.addAttribute("activeFunction", "home");
        model.addAttribute("dogs", dogService.getPublicDogs());

        return "admin/dogs/list";
    }

}
