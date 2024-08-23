package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.systementor.javasecstart.services.DogService;

@Controller
public class PublicDogsController {

    @Autowired
    private DogService dogService;

    @GetMapping(path="/dogs")
    String list(Model model) {
        model.addAttribute("activeFunction", "publicdogs");
        model.addAttribute("dogs", dogService.getPublicDogs());
        return "dogs";
    }

}
