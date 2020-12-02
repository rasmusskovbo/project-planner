package keastudents.projectplanner.domain;

import java.time.LocalDate;

public interface DataFacade {

    public void createProject(String title, String startDate) throws DefaultException;

    public Project getProject(int id) throws DefaultException;

    public User createUser(User user) throws DefaultException;

    public User getUser(int id) throws DefaultException;

    public User login(String email, String password) throws DefaultException;

}
