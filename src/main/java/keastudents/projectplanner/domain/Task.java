package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.util.Date;

public class Task extends Subproject {
    public Task(String title, LocalDate date) {
        super(title, date);
    }
}
