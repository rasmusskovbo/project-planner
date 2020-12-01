package keastudents.projectplanner.domain;

import java.time.LocalDate;

public class Task extends Subproject {
    public Task(String title, LocalDate startDate) {
        super(title, startDate);
    }
}
