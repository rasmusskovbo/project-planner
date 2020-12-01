package keastudents.projectplanner.domain;

import java.sql.Date;

public class Project {
    private String title;
    private int id;
    private Date start_date;

    public Project(String title, int id, Date date) {
        this.title = title;
        this.id = id;
        this.start_date = date;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
}
