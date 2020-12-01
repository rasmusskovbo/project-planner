package keastudents.projectplanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

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

    private void setSessionInfo(WebRequest request, User user) {
        // Place user info on session
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
        request.setAttribute("role", user.getRole(), WebRequest.SCOPE_SESSION);
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
