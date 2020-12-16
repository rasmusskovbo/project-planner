package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Subproject extends Project implements Comparable<Subproject> {
    private ArrayList<Task> tasks;

    public Subproject() {
    }

    public Subproject(String title, LocalDate startDate) {
        super(title, startDate);
        this.tasks = new ArrayList<Task>();
    }

    public void sortTasks() {
        Collections.sort(tasks);
    }

    public int[] calculateTasks() {
        int subtotalWorkHours = 0;
        int subtotalExtraCosts = 0;
        int subtotalCostOfLabor = 0;

        for (int i = 0; i<tasks.size(); i++) {
            subtotalWorkHours += tasks.get(i).getWorkHoursNeeded();
            subtotalExtraCosts += tasks.get(i).getExtraCosts();
            subtotalCostOfLabor += tasks.get(i).getManHourCost() * tasks.get(i).getWorkHoursNeeded();
        }

        return new int[] {subtotalWorkHours, subtotalExtraCosts, subtotalCostOfLabor};

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

    @Override
    public int compareTo(Subproject subproject) {
        return this.getStartDate().compareTo(subproject.getStartDate());
    }
}
