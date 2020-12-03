package keastudents.projectplanner.domain;


import java.time.LocalDate;
import java.util.ArrayList;

public class Project {
    private String title;
    private int id;
    private LocalDate startDate;
    private ArrayList<Subproject> subprojects;

    public Project(String title, int id, LocalDate startDate) {
        this.title = title;
        this.id = id;
        this.startDate = startDate;
        this.subprojects = new ArrayList<Subproject>();
    }

    public void addSubproject(Subproject subproject) {
        subprojects.add(subproject);
    }

    public void removeSubproject(Subproject subproject) {
        subproject.removeSubproject(subproject);
    }

    public void sortSubprojects() {
        // subprojects.sort(); TODO Implementer sortering pr. dato
    }

    public Project(String title, LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}

