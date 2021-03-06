// Rasmus, Wafaa, Orn-Iliya
package keastudents.projectplanner.controller;

import keastudents.projectplanner.data.DataFacadeImplemented;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.DomainController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDate;

@Controller
public class TableWebController {
    DomainController domainController = new DomainController(new DataFacadeImplemented());

// Project-related actions
    @PostMapping("/createProjectAction")
    public String createProjectAction(WebRequest request) throws DefaultException {
        // Takes info from WebRequest and creates project in database, afterwards redirecting to overview
        String projectTitle = request.getParameter("projectTitle");
        String startDate = request.getParameter("projectStartDate");
        String deadline = request.getParameter("projectDeadline");
        String baselineManHourCost = request.getParameter("projectBaseSalary");
        String baselineHoursPrWorkday = request.getParameter("projectWorkHoursPerDay");

        // Formats date & defaults deadline if it has not been inputted by user.
        LocalDate startDateFormatted = domainController.validationService.localDateFormatter(startDate);
        LocalDate deadlineFormatted = domainController.validationService.validateDeadline(deadline);

        // Gets user ID from session to create project
        int userId = (int) request.getAttribute("id", WebRequest.SCOPE_SESSION);
        domainController.createProject(userId, projectTitle, startDateFormatted, deadlineFormatted, baselineManHourCost, baselineHoursPrWorkday);

        return "redirect:/overview";
    }


    @PostMapping("/editProjectAction")
    public String editProject(WebRequest request, Model model) throws DefaultException {
        String projectId = request.getParameter("projectId");
        String projectTitle = request.getParameter("projectTitle");
        String projectStartDate = request.getParameter("projectStartDate");

        String projectDeadline = request.getParameter("projectDeadline");
        String projectBaselineManHourCost = request.getParameter("projectBaselineManHourCost");
        String projectBaselineHoursPrWorkday = request.getParameter("projectBaselineHoursPrWorkday");

        LocalDate startDateFormatted = domainController.validationService.localDateFormatter(projectStartDate);
        LocalDate deadlineFormatted = domainController.validationService.validateDeadline(projectDeadline);
        int projectBaselineManHourCostFormatted = domainController.validationService.validateInt(projectBaselineManHourCost);
        int projectBaselineHoursPrWorkdayFormatted = domainController.validationService.validateInt(projectBaselineHoursPrWorkday);

        domainController.editProject(Integer.parseInt(projectId), projectTitle, startDateFormatted, deadlineFormatted, projectBaselineManHourCostFormatted, projectBaselineHoursPrWorkdayFormatted);

        return "redirect:/selectedProjectOverview";
    }

    @PostMapping("/deleteProjectAction")
    public String deleteProject(WebRequest request, Model model) throws DefaultException {
        String projectId = request.getParameter("projectId");

        domainController.deleteProjectObject(Integer.parseInt(projectId), "project");

        return "redirect:/overview";
    }


// Subproject-related actions
    @PostMapping("/createSubprojectAction")
    public String createSubprojectAction(WebRequest request, Model model) throws DefaultException {
        //Takes info from WebRequest and creates/adds subproject to project through project id
        String projectId = request.getParameter("projectId");
        String subprojectTitle = request.getParameter("subprojectTitle");
        String subprojectStartDate = request.getParameter("subprojectStartDate");
        String subprojectDeadline = request.getParameter("subprojectDeadline");

        LocalDate startDateFormatted = domainController.validationService.localDateFormatter(subprojectStartDate);
        LocalDate deadlineFormatted = domainController.validationService.validateDeadline(subprojectDeadline);

        domainController.createSubproject(Integer.parseInt(projectId), subprojectTitle, startDateFormatted, deadlineFormatted);

        return  "redirect:/selectedProjectOverview";
    }

    @PostMapping("/editSubprojectAction")
    public String editSubproject(WebRequest request) throws DefaultException {
        String subprojectId = request.getParameter("subprojectId");
        String subprojectTitle = request.getParameter("subprojectTitle");
        String subprojectStartDate = request.getParameter("subprojectStartDate");
        String subprojectDeadline = request.getParameter("subprojectDeadline");

        LocalDate startDateFormatted = domainController.validationService.localDateFormatter(subprojectStartDate);
        LocalDate deadlineFormatted = domainController.validationService.validateDeadline(subprojectDeadline);

       domainController.editSubproject(Integer.parseInt(subprojectId), subprojectTitle, startDateFormatted, deadlineFormatted);

        return "redirect:/selectedProjectOverview";
    }

    @PostMapping("/deleteSubprojectAction")
    public String deleteSubproject(WebRequest request, Model model) throws DefaultException {
        String subprojectId = request.getParameter("subprojectId");

        domainController.deleteProjectObject(Integer.parseInt(subprojectId), "subproject");

        return "redirect:/selectedProjectOverview";
    }

// Task-related actions
    @PostMapping("/createTaskAction")
    public String createTask(WebRequest request, Model model) throws DefaultException {
        String subprojectId = request.getParameter("subprojectId");
        String taskTitle = request.getParameter("taskTitle");
        String taskStartDate = request.getParameter("taskStartDate");
        String taskDeadline = request.getParameter("taskDeadline");
        String taskWorkHoursNeeded = request.getParameter("taskWorkHoursNeeded");
        String taskManHourCost = request.getParameter("taskManHourCost");
        String taskExtraCost = request.getParameter("taskExtraCosts");

        LocalDate startDateFormatted = domainController.validationService.localDateFormatter(taskStartDate);
        LocalDate deadlineFormatted = domainController.validationService.validateDeadline(taskDeadline);
        int taskWorkHoursNeededFormatted = domainController.validationService.validateInt(taskWorkHoursNeeded);
        int taskManHourCostFormatted = domainController.validationService.validateInt(taskManHourCost);
        int taskExtraCostFormatted = domainController.validationService.validateInt(taskExtraCost);

        domainController.createTask(Integer.parseInt(subprojectId), taskTitle, startDateFormatted, deadlineFormatted, taskWorkHoursNeededFormatted, taskExtraCostFormatted, taskManHourCostFormatted);

        return "redirect:/selectedProjectOverview";
    }

    @PostMapping("/editTaskAction")
    public String editTask(WebRequest request, Model model) throws DefaultException {
        String taskId = request.getParameter("taskId");
        String taskTitle = request.getParameter("taskTitle");
        String taskStartDate = request.getParameter("taskStartDate");
        String taskDeadline = request.getParameter("taskDeadline");
        String taskWorkHoursNeeded = request.getParameter("taskWorkHoursNeeded");
        String taskManHourCost = request.getParameter("taskManHourCost");
        String taskExtraCost = request.getParameter("taskExtraCost");

        LocalDate startDateFormatted = domainController.validationService.localDateFormatter(taskStartDate);
        LocalDate deadlineFormatted = domainController.validationService.validateDeadline(taskDeadline);
        System.out.println(taskWorkHoursNeeded);
        int taskWorkHoursNeededFormatted = domainController.validationService.validateInt(taskWorkHoursNeeded);
        int taskManHourCostFormatted = domainController.validationService.validateInt(taskManHourCost);
        int taskExtraCostFormatted = domainController.validationService.validateInt(taskExtraCost);

        domainController.editTask(Integer.parseInt(taskId), taskTitle, startDateFormatted, deadlineFormatted, taskWorkHoursNeededFormatted, taskExtraCostFormatted, taskManHourCostFormatted);

        return "redirect:/selectedProjectOverview";
    }

    @PostMapping("/deleteTaskAction")
    public String deleteTask(WebRequest request, Model model) throws DefaultException {
        String taskId = request.getParameter("taskId");

        domainController.deleteProjectObject(Integer.parseInt(taskId), "task");

        return "redirect:/selectedProjectOverview";
    }

// EXCEPTION HANDLING
    @ExceptionHandler(Exception.class)
    public String anotherError(Model model, Exception exception) {
        model.addAttribute("message",exception.getMessage());
        return "exceptionPage";
    }

}
