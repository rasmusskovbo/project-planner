package keastudents.projectplanner.controller;


import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class ProjectWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

    @PostMapping("/createProjectAction")
    public String createProjectAction(WebRequest request) throws DefaultException {
        // Takes info from WebRequest and creates project in database, afterwards redirecting to overview
        String projectTitle = request.getParameter("projectTitle");
        String startDate = request.getParameter("startDate");

        LocalDate startDateFormatted = localDateFormatter(startDate);


        //To get projects from current user
        int userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        domainController.createProject(userId, projectTitle, startDateFormatted);

        return "redirect:/overviewPage";
    }

    @PostMapping("/createSubprojectAction")
    public String createSubprojectAction(WebRequest request) throws DefaultException {
        //Takes info from WebRequest and creates/adds subproject to project through project id
        String projectId = request.getParameter("projectId");
        String subprojectTitle = request.getParameter("subprojectTitle");
        String startDate = request.getParameter("subprojectStartDate");

        LocalDate startDateFormatted = localDateFormatter(startDate);

        domainController.createSubproject(Integer.parseInt(projectId), subprojectTitle, startDateFormatted);

        return "redirect:/overviewPage";
    }

    @PostMapping("/createTaskAction")
    public String createTask(WebRequest request) throws DefaultException {
        //Takes info from WebRequest and creates/adds task to subproject through subproject id
        String subprojectId = request.getParameter("subprojectId");
        String taskTitle = request.getParameter("taskTitle");
        String startDate = request.getParameter("taskStartDate");

        LocalDate startDateFormatted = localDateFormatter(startDate);

        domainController.createTask(Integer.parseInt(subprojectId), taskTitle, startDateFormatted);

        return "redirect:/overviewPage";
    }

    @PostMapping("editProject")
    public String editProject(WebRequest request, Model model) throws DefaultException {
        //Retrieve values from HTML form via WebRequest
        String title = request.getParameter("title");
        return title;

    }

    private LocalDate localDateFormatter(String date) {
        //Formats input String Date to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        return LocalDate.parse(date, formatter);
    }

    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message",exception.getMessage());
        return "afterLogin/exceptionPage";
    }
}
