package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Subproject extends Project {

    private ArrayList<Task> tasks;

    public Subproject(String title, LocalDate startDate) {
        super(title, startDate);
        this.tasks = new ArrayList<Task>();
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
