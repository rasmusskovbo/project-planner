package keastudents.projectplanner.data;

import keastudents.projectplanner.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class DataFacadeImplemented implements DataFacade {
    private ProjectMapper projectMapper = new ProjectMapper();
    private SubprojectMapper subprojectMapper = new SubprojectMapper();
    private TaskMapper taskMapper = new TaskMapper();
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
    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDateFormatted, LocalDate deadlineFormatted) throws DefaultException {
        subprojectMapper.createSubproject(projectId, subprojectTitle, startDateFormatted, deadlineFormatted);
    }

    @Override
    public void createTask(int subprojectId, String taskTitle, LocalDate startDate, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost) throws DefaultException {
        taskMapper.createTask(subprojectId,taskTitle, startDate, deadline, workHoursNeeded, extraCosts, manHourCost);
    }

    @Override
    public void updateProject(Project project) throws DefaultException {
        projectMapper.updateProject(project);
    }

    @Override
    public void editProject(int projectId, String title, LocalDate start_date, LocalDate deadline, int baseline_man_hour_cost, int baseline_hours_pr_workday) throws DefaultException {
        projectMapper.editProject(projectId, title, start_date, deadline, baseline_man_hour_cost, baseline_hours_pr_workday);
    }

    @Override
    public void editSubproject(int subprojectId, String title, LocalDate start_date, LocalDate deadline) throws DefaultException {
        subprojectMapper.editSubproject(subprojectId, title, start_date, deadline);
    }

    @Override
    public void editTask(int taskId, String title, LocalDate start_date, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost) throws DefaultException {
        taskMapper.editTask(taskId, title, start_date, deadline, workHoursNeeded, extraCosts, manHourCost);
    }

    @Override
    public void deleteProjectObject(int id, String choice) throws DefaultException {
        projectMapper.deleteProjectObject(id, choice);
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

