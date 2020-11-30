package keastudents.projectplanner.Controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

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

    @PostMapping("/signUpAction")
    public String signUpAction(WebRequest request) throws DefaultException {
        //Retrieve values from HTML signUp form via WebRequest
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password1");
        String confirmedPassword = request.getParameter("password2");

        //Check if first password value matches confirmed password value
        if (password.equals(confirmedPassword)) {
            domainController.createUser(firstName, lastName, email, password);
            return "redirect:/overview";
        } else {
            throw new DefaultException("The two password did not match!");
        }
    }
}
