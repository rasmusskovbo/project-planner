// Rasmus, Wafaa, Orn-Iliya
package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import keastudents.projectplanner.domain.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@Controller
public class WebViewController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

    // Initial landing page after login
    @GetMapping("/overview")
    public String projectsOverview(WebRequest request, Model model) throws DefaultException, UserNotLoggedInException {
        int userId = tryLogin(request);

        model.addAttribute("user", domainController.getUser(userId));
        ArrayList<Project> projects = domainController.getProjects(userId);

        // If user has active project(s), add to model.
        if (projects !=null) {
            model.addAttribute("projectList", projects);
        }

        return "afterLogin/overviewPage";
    }

    // General use login page
    @GetMapping("/selectedProjectOverview")
    public String projectOverview(WebRequest request, Model model) throws DefaultException, UserNotLoggedInException {
        int userId = tryLogin(request);
        int projectId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);

        model.addAttribute("user", domainController.getUser(userId));

        ArrayList<Project> projectsList = domainController.getProjects(userId);
        // If user has active project(s), add to model.
        if (projectsList != null) {
            model.addAttribute("projectList", projectsList);
        }

        domainController.updateProject(projectId);
        model.addAttribute("project", domainController.getProject(projectId));

        return "afterLogin/selectedProjectOverviewPage";
    }
    
    // Adds/changes active user project to session object
    @PostMapping("/selectProject")
    public String selectProject(WebRequest request) {
        //choose project to view
        //try-catch if theres no projects to view but user tries to send request anyway by clicking on the button
        try {
            String projectId = request.getParameter("projectId");
            setActiveProject(request, Integer.parseInt(projectId));

            return "redirect:/selectedProjectOverview";
        } catch (NumberFormatException e) {
            return "redirect:/overview";
        }
    }

    public void setActiveProject(WebRequest request, int projectId) {
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);
    }

    public int tryLogin(WebRequest request) throws UserNotLoggedInException {
        try {
            return (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        } catch (Exception e) {
            throw new UserNotLoggedInException("You are not currently logged in. Please login again.");
        }
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public String error(Model model, UserNotLoggedInException e) {
        model.addAttribute("message", e.getMessage());
        return "exceptionPage";
    }

}
