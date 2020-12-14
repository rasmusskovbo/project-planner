package keastudents.projectplanner.domain;

import keastudents.projectplanner.data.LoginException;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DataFacade {


    public User createUser(User user) throws DefaultException;

    public User getUser(int userId) throws DefaultException;

    public User login(String email, String password) throws LoginException;

    public void createProject(int userId, String projectTitle, LocalDate startDate, LocalDate deadline, int baselineManHourCost, int baselineHoursPrWorkday) throws DefaultException;

    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDateFormatted, LocalDate deadlineFormatted) throws DefaultException;

    public void createTask(int subprojectId, String taskTitle, LocalDate startDate, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost, int hoursPrWorkday) throws DefaultException;

    public void editProject(int projectId, String title, LocalDate start_date, LocalDate deadline, int baseline_man_hour_cost, int baseline_hours_pr_workday) throws DefaultException;

    public void editSubproject(int subprojectId, String title, LocalDate start_date, LocalDate deadline) throws DefaultException;

    public void editTask(int taskId, String title, LocalDate start_date, LocalDate deadline, int workHoursNeeded, int extraCosts, int manHourCost, int hoursPrWorkday) throws DefaultException;

    public void deleteProjectObject(int id, String choice) throws DefaultException;

    public ArrayList<Project> getProjects(int userId) throws DefaultException;

    public Project getProject(int projectId) throws DefaultException;
}
