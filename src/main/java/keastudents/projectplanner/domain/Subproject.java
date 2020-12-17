package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class Subproject extends Project implements Comparable<Subproject> {
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public Subproject() {
    }

    public Subproject(String title, LocalDate startDate) {
        super(title, startDate);
        this.tasks = new ArrayList<Task>();
    }

    public void sortTasks() {
        Collections.sort(tasks);
    }

    public int[] calculateSubproject(int baselineHoursPrWorkday) {
        int subtotalWorkHours = 0;
        int subtotalExtraCosts = 0;
        int subtotalCostOfLabor = 0;

        for (int i = 0; i<tasks.size(); i++) {
            subtotalWorkHours += tasks.get(i).getWorkHoursNeeded();
            subtotalExtraCosts += tasks.get(i).getExtraCosts();
            subtotalCostOfLabor += tasks.get(i).getManHourCost() * tasks.get(i).getWorkHoursNeeded();
        }

        // Sets subtotals for table viewing (updated through project -> subproject)
        setTotalWorkHours(subtotalWorkHours);
        setEstTotalCost(subtotalExtraCosts);
        setTotalWorkDays((int) Math.ceil((double) getTotalWorkHours() / baselineHoursPrWorkday));
        setEstFinishedByDate(getStartDate().plusDays(getTotalWorkDays()));
        setDeadlineDifference ((int) (ChronoUnit.DAYS.between(getDeadline(), getEstFinishedByDate())));

        int neededWorkHoursDaily = 0;
        int workDaysAvailable = 0;

        if (getDeadlineDifference() > 0) {
            workDaysAvailable = (int) ChronoUnit.DAYS.between(getStartDate(), getDeadline())+1;
            neededWorkHoursDaily = (getTotalWorkHours() / workDaysAvailable);
            setChangeToWorkHoursNeeded(neededWorkHoursDaily - baselineHoursPrWorkday);
        } else {
            setChangeToWorkHoursNeeded(0);
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
