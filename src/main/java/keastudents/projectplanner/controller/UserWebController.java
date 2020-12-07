package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.User;
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
    private ValidationController validationController = new ValidationController();

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

    @GetMapping("/overviewPage")
    public String projectsOverview(WebRequest request, Model model) throws DefaultException {
        // Retrieves project and user info, packs them and sends to html page.
        int userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        model.addAttribute("user", domainController.getUser(userId));

        ArrayList<Project> projects = domainController.getProjects(userId);
        // If user has active project(s), add to model.
        if (projects !=null) {
            model.addAttribute("projectList", projects);
        }

        return "afterLogin/overviewPage";
    }


    @PostMapping("/signUpAction")
    public String signUpAction(WebRequest request, Model model) throws DefaultException {
        //Retrieve values from HTML signUp form via WebRequest
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password1");
        String confirmedPassword = request.getParameter("password2");

        //Check if first password value matches confirmed password value
        if (password.equals(confirmedPassword)) {
            String validationStatus = validationController.validate(firstName, lastName, email); //Checks input against regex. Returns "" if OK
            if (validationStatus.equals("")) {
                domainController.createUser(firstName, lastName, email, password);
            } else {
                model.addAttribute("errorMsg", validationStatus);
                return "beforeLogin/signUpPage";
            }
            // setSessionInfo
            User user = domainController.login(email, password);
            setSessionInfo(request, user);

            return "redirect:/overviewPage";
        } else {
            throw new DefaultException("The two password did not match!");
        }
    }

    @PostMapping("/loginAction")
    public String loginAction(WebRequest request) throws DefaultException {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // TODO Try/Catch her?

        User user = domainController.login(email, password);
        setSessionInfo(request, user);

        return "redirect:/overviewPage";

    }



    private void setSessionInfo(WebRequest request, User user) {
        // Place user info on session
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
        request.setAttribute("id", user.getId(), WebRequest.SCOPE_SESSION);
    }


    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message",exception.getMessage());
        return "afterLogin/exceptionPage";
    }

}
