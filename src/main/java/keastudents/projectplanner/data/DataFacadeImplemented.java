package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DataFacade;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.User;

public class DataFacadeImplemented implements DataFacade {
    private ProjectMapper projectMapper = new ProjectMapper();
    private UserMapper userMapper = new UserMapper();

    @Override
    public void createProject(Project project) throws DefaultException {
        projectMapper.createProject(project);
    }

    @Override
    public User createUser(User user) throws DefaultException{
        userMapper.createUser(user);
        return user;
    }

    @Override
    public User login(String email, String password) throws DefaultException {
        return userMapper.login(email, password);
    }

    public User getUser(int id) throws DefaultException {
        return userMapper.getUser(id);
    }
}
