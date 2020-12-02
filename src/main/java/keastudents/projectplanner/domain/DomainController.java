package keastudents.projectplanner.domain;

import java.sql.Date;
import java.time.LocalDate;

public class DomainController {
    private DataFacade facade = null;

    public DomainController(DataFacade facade) {
        this.facade = facade;
    }

    public Project createProject(String title, Date date) throws DefaultException {
        Project project = new Project(title, date);
        facade.createProject(project);// creates project in MYSQL
        return project;
    }

    public User createUser(String firstName, String lastName, String email, String password) throws DefaultException{
        User userCreate = new User(firstName, lastName, email, password);
        facade.createUser(userCreate);
        return userCreate;
    }

    public User login(String email, String password) throws DefaultException {
        User userLogin = new User(email, password);
        facade.login(userLogin);
        return userLogin;
    }
}
