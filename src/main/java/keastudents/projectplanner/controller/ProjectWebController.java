package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ProjectWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());


    @GetMapping("/subprojectOverview")
    public String subprojectOverview() {
        return "subprojectOverview";
    }

    @GetMapping("/taskOverview")
    public String taskOverview() {
        return "taskOverview";
    }

    @GetMapping("/createProject")
    public String createProject() {
        return "createProject";
    }

    @PostMapping("/createProjectAction")
    public String createProjectAction(WebRequest request) throws DefaultException {
        // Takes info from WebRequest and creates project in database, afterwards redirecting to overview
        String title = request.getParameter("title");
        String startDate = request.getParameter("startDate");// Format SKAL være YYYY-MM-DD så vi kan parse til LocalDate (skal bruges til beregninger) LocalDate.parse(STRING)
        domainController.createProject(title, startDate);
        return "overview";
    }

    @PostMapping("editProject")
    public String editProject(WebRequest request, Model model) throws DefaultException {
        //Retrieve values from HTML form via WebRequest
        String title = request.getParameter("title");
        return title;
    }
}
