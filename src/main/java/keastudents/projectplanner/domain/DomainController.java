package keastudents.projectplanner.domain;

import java.sql.Date;
import java.time.LocalDate;

public class DomainController {
    private DataFacade facade = null;

    public DomainController(DataFacade facade) {
        this.facade = facade;
    }

    // TODO Kan simplificeres
    public User createUser(String firstName, String lastName, String email, String password) throws DefaultException{
        User userCreate = new User(firstName, lastName, email, password);
        facade.createUser(userCreate);
        return userCreate;
    }

    public User getUser(int id) throws DefaultException {
        return facade.getUser(id);
    }

    public User login(String email, String password) throws DefaultException {
        return facade.login(email, password);
    }

    public void createProject(int id, String projectName, String startDate) throws DefaultException{
        facade.createProject(id, projectName, startDate);
    }

    public Project getProject(int id) throws DefaultException {
        return facade.getProject(id);
    }
}
