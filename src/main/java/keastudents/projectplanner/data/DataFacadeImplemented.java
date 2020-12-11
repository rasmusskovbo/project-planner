package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.DataFacade;
import keastudents.projectplanner.domain.DefaultException;
import keastudents.projectplanner.domain.Project;
import keastudents.projectplanner.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataFacadeImplemented implements DataFacade {
    private ProjectMapper projectMapper = new ProjectMapper();
    private UserMapper userMapper = new UserMapper();


    @Override
    public User createUser(User user) throws DefaultException{
        userMapper.createUser(user);
        return user;
    }

    @Override
    public User login(String email, String password) throws LoginException {
        return userMapper.login(email, password);
    }

    @Override
    public User getUser(int userId) throws DefaultException {
        return userMapper.getUser(userId);
    }

    @Override
    public void createProject(int userId, String projectTitle, LocalDate startDate, LocalDate deadline, int baselineManHourCost, int baselineHoursPrWorkday) throws DefaultException {
        projectMapper.createProject(userId, projectTitle, startDate, deadline, baselineManHourCost, baselineHoursPrWorkday);
    }

    @Override
    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDateFormatted) throws DefaultException {
        projectMapper.createSubproject(projectId, subprojectTitle, startDateFormatted);
    }

    @Override
    public void createTask(int subprojectId, String taskTitle, LocalDate startDateFormatted) throws DefaultException {
        projectMapper.createTask(subprojectId,taskTitle, startDateFormatted);
    }

    @Override
    public ArrayList<Project> getProjects(int userId) throws DefaultException {
        return projectMapper.getProjects(userId);
    }

    @Override
    public Project getProject(int projectId) throws DefaultException {
        return projectMapper.getProject(projectId);
    }
}

