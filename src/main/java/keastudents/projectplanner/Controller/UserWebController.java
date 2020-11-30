package keastudents.projectplanner.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserWebController {

    //show welcome-/frontpage
    @GetMapping("/")
    public String frontpage() {
        return "frontpage";
    }

    //show log in page
    @GetMapping("/logIn")
    public String logIn() {
        return "logIn";
    }

    //show sign up page
    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

    @GetMapping("/overview")
    public String overview() {
        return "overview";
    }
}
