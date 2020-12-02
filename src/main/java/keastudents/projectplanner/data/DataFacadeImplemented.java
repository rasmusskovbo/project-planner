package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DataFacade;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.User;

import java.time.LocalDate;

public class DataFacadeImplemented implements DataFacade {
    private ProjectMapper projectMapper = new ProjectMapper();
    private UserMapper userMapper = new UserMapper();


    @Override
    public User createUser(User user) throws DefaultException{
        userMapper.createUser(user);
        return user;
    }

    @Override
    public User login(String email, String password) throws DefaultException {
        return userMapper.login(email, password);
    }

    @Override
    public User getUser(int id) throws DefaultException {
        return userMapper.getUser(id);
    }

    @Override
    public void createProject(int id, String projectName, String startDate) throws DefaultException {
        projectMapper.createProject(id, projectName, startDate);
    }

    @Override
    public Project getProject(int id) throws DefaultException {
        return projectMapper.getProject(id);
    }
}
