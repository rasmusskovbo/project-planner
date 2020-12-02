package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
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

    @GetMapping("/projectsOverview")
    public String projectsOverview(WebRequest request, Model model) throws DefaultException {

        //Gets User ID from WebRequest (Established upon log-in)
//       int id = (int) request.getAttribute("id",WebRequest.SCOPE_SESSION);
        // Retrieves project and user info, packs them and sends to html page.
//        model.addAttribute("user", domainController.getUser(id));

        /* TODO Check om der et projekt først, hvis går videre, ellers load
        if (domainController.getProject(id) != null) {
            model.addAttribute("project", domainController.getProject(id));
        }
         */
        return "afterLogin/projectsOverview";
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
            return "redirect:/projectsOverview";
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
        //Nullpointer in login method????
        try {
            User user = domainController.login(email, pwd); // UserMapper checks with Database for user. Returns user ID if true
            setSessionInfo(request, user);
        } catch (NullPointerException ex) {
            System.out.println("User missing");
        }

        return "redirect:/projectsOverview";
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
