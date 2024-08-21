package se.systementor.javasecstart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SecurityController {

    @Autowired
    private AppUserService userService;
    private static final String WAVING_EMOJI = "\uD83D\uDC4B";

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Logga in");
        model.addAttribute("header", "Logga in");
        model.addAttribute("usernameLabelText", "E-postadress");
        model.addAttribute("usernamePlaceholder", "email@exempel.com");
        model.addAttribute("passwordLabelText", "Lösenord");
        model.addAttribute("passwordPlaceholder", "Lösenord");
        model.addAttribute("submitText", "Logga in");
        model.addAttribute("newUserText", "Skapa Konto");

        return "security/login.html";
    }

    @GetMapping("/login/success")
    public String loginSuccessful(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AppUser appUser) {
            redirectAttributes.addFlashAttribute("appUser", appUser);
            redirectAttributes.addFlashAttribute("alertMessage", "Välkommen '%s' %s".formatted(appUser.getUsername(), WAVING_EMOJI));
        }

        return "redirect:/";
    }

    @GetMapping("/logout/success")
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertMessage", "Du är nu utloggad!");

        return "redirect:/";
    }


    @GetMapping("/login/new-user")
    public String signUp(Model model) {
        model.addAttribute("pageTitle", "Skapa Konto");
        model.addAttribute("header", "Registrera konto");
        model.addAttribute("errorMessage", "Lösenorden matchar inte. Försök igen.");
        model.addAttribute("usernameLabelText", "Ange e-postadress:");
        model.addAttribute("usernamePlaceholder", "email@exempel.com");
        model.addAttribute("passwordLabelText", "Ange lösenord:");
        model.addAttribute("passwordPlaceholder", "Lösenord");
        model.addAttribute("passwordConfirmLabelText", "Upprepa lösenord:");
        model.addAttribute("passwordConfirmPlaceholder", "Lösenord");
        model.addAttribute("buttonText", "Skapa Konto");
        model.addAttribute("user", new AppUser());

        return "security/new-user.html";
    }

    @PostMapping("/login/register")
    public String register(RedirectAttributes redirectAttributes, @ModelAttribute AppUser user) {

        if (userService.addUser(user)) {
            redirectAttributes.addFlashAttribute("alertMessage", "Kontot '%s' har skapats!".formatted(user.getUsername()));
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Användarnamnet är redan upptaget!");
            return "redirect:/login/new-user";
        }
    }
}


