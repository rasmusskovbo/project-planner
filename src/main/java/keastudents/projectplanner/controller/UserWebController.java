package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.data.LoginException;
import keastudents.projectplanner.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

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
    @GetMapping("/logInPage")
    public String logInPage() {
        return "beforeLogin/logInPage";
    }

    //show sign up page
    @GetMapping("/signUpPage")
    public String signUpPage() {
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
            return "redirect:/overviewPage";
        } else {
            model.addAttribute("errorMsg", validationStatus);
            return "beforeLogin/signUpPage";
        }
    }

// Initial landing page
    @GetMapping("/overviewPage")
    public String projectsOverview(WebRequest request, Model model) throws DefaultException, UserNotLoggedInException {
        int userId = 0;
        try {
            userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        } catch (Exception e) {
            throw new UserNotLoggedInException("You have been logged out. Please login again.");
        }

        model.addAttribute("user", domainController.getUser(userId));
        ArrayList<Project> projects = domainController.getProjects(userId);

        // If user has active project(s), add to model.
        if (projects !=null) {
            model.addAttribute("projectList", projects);
        }

        return "afterLogin/overviewPage";
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
        return "redirect:/overviewPage";

    }

    @PostMapping("/logoutAction")
    public String logoutAction(WebRequest request) {
        request.setAttribute("user", null, WebRequest.SCOPE_SESSION);
        request.setAttribute("id", null, WebRequest.SCOPE_SESSION);
        request.setAttribute("projectId", null, WebRequest.SCOPE_SESSION);
        return "beforeLogin/frontpage";
    }

// General use
    private void setSessionInfo(WebRequest request, User user) {
        // Place user info on session
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
        request.setAttribute("id", user.getId(), WebRequest.SCOPE_SESSION);
    }

// Exception handling
    @ExceptionHandler(UserNotLoggedInException.class)
    public String error(Model model, UserNotLoggedInException e) {
        model.addAttribute("message", e.getMessage());
        return "exceptionPage";
    }

}
