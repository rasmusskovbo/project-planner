package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.data.LoginException;
import keastudents.projectplanner.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;


@Controller
public class UserWebController {
    private DomainController domainController = new DomainController(new DataFacadeImplemented());

// Log-in / Sign-up steps
    //show welcome-/frontpage
    @GetMapping("/")
    public String frontpage() {
        return "beforeLogin/frontpage";
    }

    //show log in page
    @GetMapping("/logIn")
    public String logIn() {
        return "beforeLogin/logInPage";
    }

    //show sign up page
    @GetMapping("/signUp")
    public String signUp() {
        return "beforeLogin/signUpPage";
    }

    @PostMapping("/signUpAction")
    public String signUpAction(WebRequest request, Model model) throws LoginException, DefaultException {
        //Retrieve values from HTML signUp form via WebRequest
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password1");
        String confirmedPassword = request.getParameter("password2");

        // Validates input. Returns custom error message if any errors, if not returns an empty string ""
        String validationStatus = domainController.validationService.validateNewUser(firstName, lastName, email, password, confirmedPassword);

        // Creates user if no validation error, if not reloads page and passes error message on
        if (validationStatus.equals("")) {
            domainController.createUser(firstName, lastName, email, password);
            User user = domainController.login(email, password);
            setSessionInfo(request, user);
            return "redirect:/overview";
        } else {
            model.addAttribute("errorMsg", validationStatus);
            return "beforeLogin/signUpPage";
        }
    }

// Log-in / Log-out actions
    @PostMapping("/loginAction")
    public String loginAction(WebRequest request, Model model) throws LoginException {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = domainController.login(email, password);
            setSessionInfo(request, user);
        } catch (LoginException e) {
            model.addAttribute("errorMsg", "Could not validate the user. Please check your e-mail and password and try again.");
            return "beforeLogin/logInPage";
        }
        return "redirect:/overview";

    }

    @PostMapping("/logoutAction")
    public String logoutAction(WebRequest request) {
        request.setAttribute("user", null, WebRequest.SCOPE_SESSION);
        request.setAttribute("id", null, WebRequest.SCOPE_SESSION);
        request.setAttribute("projectId", null, WebRequest.SCOPE_SESSION);
        return "beforeLogin/frontpage";
    }

// User Session
    private void setSessionInfo(WebRequest request, User user) {
        // Place user info on session
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
        request.setAttribute("id", user.getId(), WebRequest.SCOPE_SESSION);
    }

}
