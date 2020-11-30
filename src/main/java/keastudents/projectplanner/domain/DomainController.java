package keastudents.projectplanner.domain;

public class DomainController {
    private DataFacade facade = null;

    public DomainController(DataFacade facade) {
        this.facade = facade;
    }

    public void createProject(Project project) throws DefaultException {
        facade.createProject(project);
    }
}
