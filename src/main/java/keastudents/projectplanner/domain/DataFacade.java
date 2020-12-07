package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public interface DataFacade {


    public User createUser(User user) throws DefaultException;

    public User getUser(int userId) throws DefaultException;

    public User login(String email, String password) throws DefaultException;

    public void createProject(int userId, String projectTitle, LocalDate startDate) throws DefaultException;

    public void createSubproject(int projectId, String subprojectTitle, LocalDate startDateFormatted) throws DefaultException;

    public void createTask(int subprojectId, String taskTitle, LocalDate startDateFormatted) throws DefaultException;

    public ArrayList<Project> getProjects(int userId) throws DefaultException;
}
