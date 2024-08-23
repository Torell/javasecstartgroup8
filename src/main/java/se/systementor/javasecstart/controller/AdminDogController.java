package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.systementor.javasecstart.model.Dog;
import se.systementor.javasecstart.services.DogService;

@Controller
public class AdminDogController {

    @Autowired
    private DogService dogService;

    @GetMapping(path="/admin/dogs")
    public String getAllDogsWithSearchAndSort(Model model,
                                              @RequestParam(defaultValue = "0")int page,
                                              @RequestParam(defaultValue = "name")String sort,
                                              @RequestParam(defaultValue = "asc")String sortDirection,
                                              @RequestParam(required = false, defaultValue = "") String name,
                                              @RequestParam(required = false, defaultValue = "") String breed,
                                              @RequestParam(required = false, defaultValue = "") String size,
                                              @RequestParam(required = false, defaultValue = "") String age,
                                              @RequestParam(required = false, defaultValue = "") int price,
                                              @RequestParam(required = false, defaultValue = "")String search)
    {


        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.fromString(sortDirection),sort));
        Page<Dog> dogPage = dogService.findAllDogsWithSearch(search, pageable);

        model.addAttribute("activeFunction", "home");
//        setupVersion(model);

        model.addAttribute("page", dogPage);
        model.addAttribute("sort", sort);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("search",search);
        return "admin/dogs/list";
    }

}
