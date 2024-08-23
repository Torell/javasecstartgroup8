package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.systementor.javasecstart.services.AppUserService;
import se.systementor.javasecstart.security.IAuthenticationFacade;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.model.Dog;
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
    public String getAllDogsWithSearchAndSort(Model model,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "name") String sort,
                                              @RequestParam(defaultValue = "asc") String sortDirection,
                                              @RequestParam(required = false, defaultValue = "") String search) {
        final int PAGE_SIZE = 10;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.fromString(sortDirection), sort));
        Page<Dog> dogPage = dogService.findAllDogsWithSearch(search, pageable);
        model.addAttribute("activeFunction", "home");
        model.addAttribute("page", dogPage);
        model.addAttribute("sort", sort);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("search",search);

        Authentication authentication = authenticationFacade.getAuthentication();
        String profileImage = userService.findUserByUsername(authentication.getName()).getProfileImage();
        model.addAttribute("profileImage", profileImage);
        model.addAttribute("activeFunction", "home");
        model.addAttribute("dogs", dogService.getPublicDogs());

        return "admin/dogs/list";
    }

}
