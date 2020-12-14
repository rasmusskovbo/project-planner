package keastudents.projectplanner.controller;


import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import keastudents.projectplanner.domain.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class ProjectWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

// TABLE VIEW
    @GetMapping("/selectedProjectOverview")
    public String selectedProjectOverviewPage(WebRequest request, Model model) throws DefaultException {
        String projectId = request.getParameter("projectId");

        int userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        model.addAttribute("user", domainController.getUser(userId));

        Project project = domainController.getProject(Integer.parseInt(projectId));
        model.addAttribute("project", project);

        ArrayList<Project> projects = domainController.getProjects(userId);
        // If user has active project(s), add to model.
        if (projects != null) {
            model.addAttribute("projectList", projects);
        }

        return "afterLogin/selectedProjectOverviewPage";
    }


// PROJECT
    @PostMapping("/createProjectAction")
    public String createProjectAction(WebRequest request) throws DefaultException {
        // Takes info from WebRequest and creates project in database, afterwards redirecting to overview
        String projectTitle = request.getParameter("projectTitle");
        String startDate = request.getParameter("projectStartDate");
        String projectDeadline = null;
        String baselineManHourCost = request.getParameter("projectBaseSalary");
        String baselineHoursPrWorkday = request.getParameter("projectWorkHoursPerDay");

        // Formats date
        LocalDate startDateFormatted = localDateFormatter(startDate);

        // Checks if deadline has been inputted.
        LocalDate deadlineFormatted = null;
        if (request.getParameter("projectDeadline") != null) {
            projectDeadline = request.getParameter("projectDeadline");
            deadlineFormatted = localDateFormatter(projectDeadline);
        }

        //To get projects from current user
        int userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        domainController.createProject(userId, projectTitle, startDateFormatted, deadlineFormatted, baselineManHourCost, baselineHoursPrWorkday);

        return "redirect:/overviewPage";
    }


    @PostMapping("/editProject")
    public String editProject(WebRequest request, Model model) throws DefaultException {
        String projectId = request.getParameter("projectId");
        String projectTitle = request.getParameter("projectTitle");
        String projectStartDate = request.getParameter("projectStartDate");

        String projectDeadline = request.getParameter("projectDeadline");
        String projectBaselineManHourCost = request.getParameter("projectBaselineManHourCost");
        String projectBaselineHoursPrWorkday = request.getParameter("projectBaselineHoursPrWorkday");

        LocalDate startDateFormatted = localDateFormatter(projectStartDate);
        LocalDate deadlineFormatted = localDateFormatter(projectDeadline);

        domainController.editProject(Integer.parseInt(projectId), projectTitle, startDateFormatted, deadlineFormatted, Integer.parseInt(projectBaselineManHourCost), Integer.parseInt(projectBaselineHoursPrWorkday));

        return "redirect:/selectedProjectOverview";
    }

    @PostMapping("/deleteProject")
    public String deleteProject(WebRequest request, Model model) throws DefaultException {
        String projectId = request.getParameter("projectId");

        domainController.deleteProjectObject(Integer.parseInt(projectId), "project");


        return "redirect:/overviewPage";
    }


// SUBPROJECT
    @PostMapping("/createSubproject")
    public String createSubprojectAction(WebRequest request, Model model) throws DefaultException {
        //Takes info from WebRequest and creates/adds subproject to project through project id
        String subprojectTitle = request.getParameter("subprojectTitle");
        String subprojectStartDate = request.getParameter("subprojectStartDate");
        String subprojectDeadline = request.getParameter("subprojectDeadline");

        LocalDate startDateFormatted = localDateFormatter(subprojectStartDate);
        LocalDate deadlineFormatted = localDateFormatter(subprojectDeadline);

//        domainController.createSubproject(Integer.parseInt(projectId), subprojectTitle, startDateFormatted, deadlineFormatted);


        return  "afterLogin/selectedProjectOverviewPage";
    }

    @PostMapping("/editSubproject")
    public String editSubproject(WebRequest request, Model model) throws DefaultException {
        String subprojectId = request.getParameter("subprojectId");
        String subprojectTitle = request.getParameter("subprojectTitle");
        String subprojectStartDate = request.getParameter("subprojectStartDate");
        String subprojectDeadline = request.getParameter("subprojectDeadline");

        LocalDate startDateFormatted = localDateFormatter(subprojectStartDate);
        LocalDate deadlineFormatted = localDateFormatter(subprojectDeadline);

       // domainController.editProjectObject(Integer.parseInt(subprojectId), subprojectTitle, startDateFormatted, deadlineFormatted, Integer.parseInt(projectBaselineManHourCost), Integer.parseInt(projectBaselineHoursPrWorkday), "project");

    //    getPageInfo(request, model, Integer.parseInt(projectId));

        return "afterLogin/selectedProjectOverviewPage";
    }

    @PostMapping("/deleteSubproject")
    public String deleteSubproject(WebRequest request, Model model) throws DefaultException {
        String subprojectId = request.getParameter("subprojectId");

        domainController.deleteProjectObject(Integer.parseInt(subprojectId), "subproject");

        return "afterLogin/selectedProjectOverviewPage";
    }

// TASK
    @PostMapping("/createTask")
    public String createTask(WebRequest request, Model model) throws DefaultException {
        String projectId = request.getParameter("projectId");
        String subprojectId = request.getParameter("subprojectId");
        String taskTitle = request.getParameter("taskTitle");
        String taskStartDate = request.getParameter("taskStartDate");
        String taskDeadline = request.getParameter("taskDeadline");
        String taskWorkHoursNeeded = request.getParameter("taskWorkHoursNeeded");
        String taskManHourCost = request.getParameter("taskManHourCost");
        String taskExtraCosts = request.getParameter("taskExtraCosts");
        String taskHoursPrWorkday = request.getParameter("taskHoursPrWorkday");

        LocalDate startDateFormatted = localDateFormatter(taskStartDate);
        LocalDate deadlineFormatted = localDateFormatter(taskDeadline);

//        domainController.createTask(Integer.parseInt(projectId), Integer.parseInt(subprojectId), taskTitle, startDateFormatted,
//                deadlineFormatted, Integer.parseInt(taskWorkHoursNeeded), Integer.parseInt(taskManHourCost), Integer.parseInt(taskExtraCosts), Integer.parseInt(taskHoursPrWorkday));

        getPageInfo(request, model, Integer.parseInt(projectId));

        return "afterLogin/selectedProjectOverviewPage";
    }

    @PostMapping("/editTask")
    public String editTask(WebRequest request, Model model) throws DefaultException {
        String projectId = request.getParameter("projectId");
        String subprojectId = request.getParameter("subprojectId");
        String taskId = request.getParameter("taskId");
        String taskTitle = request.getParameter("taskTitle");
        String taskStartDate = request.getParameter("taskStartDate");
        String taskDeadline = request.getParameter("taskDeadline");
        String taskWorkHoursNeeded = request.getParameter("taskWorkHoursNeeded");
        String taskManHourCost = request.getParameter("taskManHourCost");
        String taskExtraCost = request.getParameter("taskExtraCost");
        String taskHoursPerWorkday = request.getParameter("taskHoursPerWorkday");

        LocalDate startDateFormatted = localDateFormatter(taskStartDate);
        LocalDate deadlineFormatted = localDateFormatter(taskDeadline);

//        domainController.editTask(Integer.parseInt(subprojectId), Integer.parseInt(taskId), taskTitle, startDateFormatted, deadlineFormatted, Integer.parseInt(taskWorkHoursNeeded),
//                Integer.parseInt(taskManHourCost), Integer.parseInt(taskExtraCost), Integer.parseInt(taskHoursPerWorkday));

        getPageInfo(request, model, Integer.parseInt(projectId));

        return "afterLogin/selectedProjectOverviewPage";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(WebRequest request, Model model) throws DefaultException {
        String taskId = request.getParameter("taskId");

        domainController.deleteProjectObject(Integer.parseInt(taskId), "task");

        return "afterLogin/selectedProjectOverviewPage";
    }

// GENERAL USE
    private void getPageInfo(WebRequest request, Model model, int projectId) throws DefaultException{
        int userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        model.addAttribute("user", domainController.getUser(userId));
        Project project = domainController.getProject(projectId);
        model.addAttribute("project", project);
    }

    private LocalDate localDateFormatter(String date) {
        //Formats input String Date to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

// EXCEPTION HANDLING
/*
    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message",exception.getMessage());
        return "afterLogin/exceptionPage";
    }
*/
}
