package keastudents.projectplanner.domain;

import keastudents.projectplanner.data.LoginException;

import java.time.LocalDate;
import java.util.ArrayList;

public class DomainController {
    private DataFacade facade = null;

    public DomainController(DataFacade facade) {
        this.facade = facade;
    }

    // TODO Kan simplificeres, User ikke nødvendigt
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

    public void createProject(int userId, String projectTitle, LocalDate startDate) throws DefaultException{
        facade.createProject(userId, projectTitle, startDate);
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
