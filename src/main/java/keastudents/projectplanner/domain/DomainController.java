package keastudents.projectplanner.domain;

public class DomainController {
    private DataFacade facade = null;

    public DomainController(DataFacade facade) {
        this.facade = facade;
    }

    public void createProject(Project project) throws DefaultException {
        facade.createProject(project);
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
