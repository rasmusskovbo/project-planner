package keastudents.projectplanner.domain;

public interface DataFacade {

    public Project createProject(Project project) throws DefaultException;

    public User createUser(User user) throws DefaultException;

    public User login(User user) throws DefaultException;

}
