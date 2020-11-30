package keastudents.projectplanner.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserWebController {
    @GetMapping("/")
    public String overview() {
        return "overview";
    }
}
