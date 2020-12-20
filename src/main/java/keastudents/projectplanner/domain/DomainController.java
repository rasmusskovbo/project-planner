// Rasmus, Wafaa, Orn-Iliya, Bilal
package keastudents.projectplanner.domain;

import keastudents.projectplanner.data.LoginException;

import java.time.LocalDate;
import java.util.ArrayList;

public class DomainController {
    private DataFacade facade = null;
    private LocalDate defaultDate = LocalDate.of(2099, 01,01); // Default date if user does not want to input upon creation;
    public ValidationService validationService = new ValidationService(defaultDate);

    public DomainController(DataFacade facade) {
        this.facade = facade;
    }

    public User createUser(String firstName, String lastName, String email, String password) throws DefaultException{
        User userCreate = new User(firstName, lastName, email, password);
        facade.createUser(userCreate);
        return userCreate;
    }

    public User getUser(int userId) throws DefaultException {
        return facade.getUser(userId);
    }

    public User login(String email, String password) throws LoginException {
        return facade.login(email, password);
    }

    public void createProject(int userId, String projectTitle, LocalDate startDate, LocalDate deadline, String baselineManHourCost, String baselineHoursPrWorkday) throws DefaultException{

        if (deadline == null) {
            deadline = defaultDate;
        }

        facade.createProject(userId, projectTitle, startDate, deadline, Integer.parseInt(baselineManHourCost), Integer.parseInt(baselineHoursPrWorkday));
    }

    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDateFormatted, LocalDate deadlineFormatted) throws DefaultException{
        facade.createSubproject(projectId, subprojectTitle, startDateFormatted, deadlineFormatted);
    }

    public void createTask(int subprojectId, String taskTitle, LocalDate startDate, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost) throws DefaultException {
        facade.createTask(subprojectId, taskTitle, startDate, deadline, workHoursNeeded, extraCosts, manHourCost);
    }

    public void updateProject(int projectId) throws DefaultException {
        Project project = facade.getProject(projectId);
        project.calculateProjectData();
        facade.updateProject(project);
    }

    public void editProject(int projectId, String title, LocalDate start_date, LocalDate deadline, int baseline_man_hour_cost, int baseline_hours_pr_workday) throws DefaultException {
        facade.editProject(projectId, title, start_date, deadline, baseline_man_hour_cost, baseline_hours_pr_workday);
    }

    public void editSubproject(int subprojectId, String title, LocalDate start_date, LocalDate deadline) throws DefaultException {
        facade.editSubproject(subprojectId, title, start_date, deadline);
    }

    public void editTask(int taskId, String title, LocalDate start_date, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost) throws DefaultException {
        facade.editTask(taskId, title, start_date, deadline, workHoursNeeded, extraCosts, manHourCost);
    }

    public void deleteProjectObject(int id, String choice) throws DefaultException {
        facade.deleteProjectObject(id, choice);
    }

    public ArrayList<Project> getProjects(int userId) throws DefaultException {
        return facade.getProjects(userId);
    }

    public Project getProject(int projectId) throws DefaultException{
        return facade.getProject(projectId);
    }
}
