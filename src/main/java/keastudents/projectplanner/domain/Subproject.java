package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

public class Subproject extends Project {


   private ArrayList<Task> tasks;


    public Subproject(String title, LocalDate startDate) {
        super(title, startDate);
        this.tasks = new ArrayList<Task>();
    }

    public Subproject() {

    }

    public void sortTasks() {
        // tasks.sort(); TODO Implementer sortering pr. dato
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }


}
