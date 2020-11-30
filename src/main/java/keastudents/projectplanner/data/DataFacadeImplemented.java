package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DataFacade;

public class DataFacadeImplemented implements DataFacade {
    private ProjectMapper projectMapper = new ProjectMapper();
    private UserMapper userMapper = new UserMapper();
}
