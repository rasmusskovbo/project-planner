package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class UserWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

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
        int id = (int) request.getAttribute("id",WebRequest.SCOPE_SESSION);
        // Retrieves project and user info, packs them and sends to html page.
        Project project = domainController.getProject(id);
        model.addAttribute("user", domainController.getUser(id));

        // If user has active project(s), add to model.
        if (project != null) {
            model.addAttribute("project", project);
        }


        return "afterLogin/overviewPage";
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

    /*
    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message",exception.getMessage());
        return "exceptionPage";
    }
     */
}
