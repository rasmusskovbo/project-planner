package keastudents.projectplanner.domain;

public interface DataFacade {

    public void createProject(Project project) throws DefaultException;

    public User createUser(User user) throws DefaultException;

    public User login(String email, String password) throws DefaultException;

}
