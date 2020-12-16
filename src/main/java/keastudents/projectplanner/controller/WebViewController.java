package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.UserNotLoggedInException;
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

// TABLE VIEW
    // Adds/changes active user project to session object
    @PostMapping("/selectProject")
    public String selectProject(WebRequest request) {
        String projectId = request.getParameter("projectId");
        setActiveProject(request, Integer.parseInt(projectId));

        return "redirect:/projectOverview";
    }

    @GetMapping("/projectOverview")
    public String projectOverview(WebRequest request, Model model) throws DefaultException, UserNotLoggedInException {
        int userId = 0;

        try {
            userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        } catch (Exception e) {
            throw new UserNotLoggedInException("You have been logged out. Please login again.");
        }

        int projectId = (int) request.getAttribute("projectId", WebRequest.SCOPE_SESSION);

        model.addAttribute("user", domainController.getUser(userId));

        ArrayList<Project> projectsList = domainController.getProjects(userId);
        // If user has active project(s), add to model.
        if (projectsList != null) {
            model.addAttribute("projectList", projectsList);
             // Only updates and calculates active project, if user has active projects.
        }

        domainController.updateProject(projectId);
        model.addAttribute("project", domainController.getProject(projectId));


        return "afterLogin/selectedProjectOverviewPage";
    }

    public void setActiveProject(WebRequest request, int projectId) {
        request.setAttribute("projectId", projectId, WebRequest.SCOPE_SESSION);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public String error(Model model, UserNotLoggedInException e) {
        model.addAttribute("message", e.getMessage());
        return "exceptionPage";
    }

}
