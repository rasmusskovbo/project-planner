package keastudents.projectplanner.domain;

import java.time.LocalDate;

public class Project {
    private String title;
    private LocalDate startDate;

    public Project(String title, LocalDate date) {
        this.title = title;
        this.startDate = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
