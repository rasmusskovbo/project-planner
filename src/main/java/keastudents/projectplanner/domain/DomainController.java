package keastudents.projectplanner.domain;

import keastudents.projectplanner.data.LoginException;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.ArrayList;

public class DomainController {
    private DataFacade facade = null;
    private LocalDate defaultDate = LocalDate.of(2099, 12, 31); // Arbitrary default date;
    private int defaultBaselineManHourCost = 0; // Ikke optional
    private int defaultBaselineHoursPrWorkday = 0; // Ikke optional

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
        int baselineMHC;
        int baselineHPW;

        if (deadline == null) {
            deadline = defaultDate;
        }

        // TODO Skal forceres
        if (baselineManHourCost != null) {
            baselineMHC = Integer.parseInt(baselineManHourCost);
        } else {
            baselineMHC = defaultBaselineManHourCost;
        }

        if (baselineHoursPrWorkday != null) {
            baselineHPW = Integer.parseInt(baselineHoursPrWorkday);
        } else {
            baselineHPW = defaultBaselineHoursPrWorkday;
        }

        facade.createProject(userId, projectTitle, startDate, deadline, baselineMHC, baselineHPW);
    }

    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDateFormatted) throws DefaultException{
        facade.createSubproject(projectId, subprojectTitle, startDateFormatted);
    }

    public void createTask(int subprojectId, String taskTitle, LocalDate startDateFormatted) throws DefaultException{
        facade.createTask(subprojectId, taskTitle, startDateFormatted);
    }

    public ArrayList<Project> getProjects(int userId) throws DefaultException {
        return facade.getProjects(userId);
    }
}
