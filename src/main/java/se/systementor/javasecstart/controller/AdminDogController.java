package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.systementor.javasecstart.services.AppUserService;
import se.systementor.javasecstart.security.IAuthenticationFacade;
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

    @GetMapping(path = "/admin/dogs")
    public String dogAdminPage(Model model,
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
        model.addAttribute("search", search);

        authenticationFacade.loggedInUserProvider()
                .ifPresent(appUser -> model.addAttribute("profileImage", appUser.getProfileImage()));
        model.addAttribute("activeFunction", "home");
        model.addAttribute("dogs", dogService.getPublicDogs());

        return "admin/dogs/list";
    }

//    /admin/dogs/edit/(id=${dog.id})

    @GetMapping(path = "/admin/dogs/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
//        Debug purpose
        System.out.println("INSIDE SHOW EDIT FORM");

        Dog dog = dogService.getDog(id);

        //if dog not empty
        model.addAttribute("dog", dog);
        model.addAttribute("title","Editera hunduppgifter");

        return "admin/dogs/editForm";
    }


    @PostMapping(path = "/admin/update")
    public String updateDog(@ModelAttribute("dog") Dog dog) {
        //update dog from editForm
        System.out.println("INSIDE UPDATE DOG");
        dogService.saveDog(dog);

        return "redirect:/admin/dogs";
    }


}
