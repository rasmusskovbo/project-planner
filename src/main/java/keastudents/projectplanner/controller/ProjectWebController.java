package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import keastudents.projectplanner.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ProjectWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

//No separate HTML sites for subprojects and task overview = redundant?
//    @GetMapping("/subprojectOverview")
//    public String subprojectOverview() {
//        return "subprojectOverview";
//    }
//
//    @GetMapping("/taskOverview")
//    public String taskOverview() {
//        return "taskOverview";
//    }
//
//    @GetMapping("/createProject")
//    public String createProject() {
//        return "createProject";
//    }

    @PostMapping("/createProjectAction")
    public String createProjectAction(WebRequest request) throws DefaultException {
        // Takes info from WebRequest and creates project in database, afterwards redirecting to overview
        String title = request.getParameter("title");

        //HTML tager date ind automatisk ind som YYYY-MM-DD - skal selv formattere til YYYY-MM-DD
        String startDate = request.getParameter("startDate");// Format SKAL være YYYY-MM-DD så vi kan parse til LocalDate (skal bruges til beregninger) LocalDate.parse(STRING)

        System.out.println("Title: "+title);
        System.out.println("Start date: "+startDate);

        //To get projects from current user
        int id = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        domainController.createProject(id, title, startDate);

        return "redirect:/projectsOverview";
    }

    @PostMapping("/createSubprojectAction")
    public String createSubprojectAction(WebRequest request) throws DefaultException {
        //Create subproject in db

        return "redirect:/projectsOverview";
    }

    @PostMapping("/createTaskAction")
    public String createTask(WebRequest request) throws DefaultException {
        //Create task in db

        return "redirect:/projectsOverview";
    }

    @PostMapping("editProject")
    public String editProject(WebRequest request, Model model) throws DefaultException {
        //Retrieve values from HTML form via WebRequest
        String title = request.getParameter("title");
        return title;
    }

}
