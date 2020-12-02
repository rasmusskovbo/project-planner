package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import keastudents.projectplanner.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

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
    public String overview(WebRequest request, Model model) throws DefaultException {

        // Gets User ID from WebRequest (Established upon log-in)
        int id = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);

        // Retrieves project and user info, packs them and sends to html page.
        model.addAttribute("user", domainController.getUser(id));
        model.addAttribute("project", domainController.getProject(id));

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
            // setSessionInfo
            return "redirect:/overview";
        } else {
            throw new DefaultException("The two password did not match!");
        }
    }

    @PostMapping("/loginAction")
    public String loginAction(WebRequest request) throws DefaultException {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");

        // Try/Catch her?
        User user = domainController.login(email, pwd); // UserMapper checks with Database for user. Returns user ID if true
        setSessionInfo(request, user);
        return "redirect:/overview";
    }

    private void setSessionInfo(WebRequest request, User user) {
        // Place user info on session
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
        request.setAttribute("id", user.getId(), WebRequest.SCOPE_SESSION);
    }

    /*
    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message",exception.getMessage());
        return "exceptionPage";
    }
     */
}
