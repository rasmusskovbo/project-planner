package keastudents.projectplanner.domain;

import java.time.LocalDate;

public class Task extends Subproject {
    private int workHoursNeeded;    // input
    private int extraCosts;         // input
    private int manHourCost;        // input, but baselined on project level
    private int hoursPrWorkday;    // input, but baselined on project level

    public Task(String title, LocalDate startDate) {
        super(title, startDate);
    }

    public Task() {

    }

    public int getWorkHoursNeeded() {
        return workHoursNeeded;
    }

    public void setWorkHoursNeeded(int workHoursNeeded) {
        this.workHoursNeeded = workHoursNeeded;
    }

    public int getExtraCosts() {
        return extraCosts;
    }

    public void setExtraCosts(int extraCosts) {
        this.extraCosts = extraCosts;
    }

    public int getManHourCost() {
        return manHourCost;
    }

    public void setManHourCost(int manHourCost) {
        this.manHourCost = manHourCost;
    }

    public int getHoursPrWorkday() {
        return hoursPrWorkday;
    }

    public void setHoursPrWorkday(int hoursPrWorkday) {
        this.hoursPrWorkday = hoursPrWorkday;
    }
}
