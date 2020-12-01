package keastudents.projectplanner.domain;

import java.time.LocalDate;

public class DomainController {
    private DataFacade facade = null;

    public DomainController(DataFacade facade) {
        this.facade = facade;
    }

    public void createProject(Project project) throws DefaultException {
        facade.createProject(project);
    }

/* // hvis vi skal gemme de oprettede projekter i databasen ?
    public Project createProject(String title, LocalDate startDate) throws DefaultException {
        Project project = new Project(title, startDate);
        facade.createProject(project);
        return project;
    }


 */
    public User createUser(String firstName, String lastName, String email, String password) throws DefaultException{
        User user = new User(firstName, lastName, email, password);
        facade.createUser(user);
        return user;
    }
}
