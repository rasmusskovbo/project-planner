package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DataFacade;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;

public class DataFacadeImplemented implements DataFacade {
    private ProjectMapper projectMapper = new ProjectMapper();
    private UserMapper userMapper = new UserMapper();

    @Override
    public void createProject(Project project) throws DefaultException {
        projectMapper.createProject(project);
    }
}
