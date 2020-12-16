package keastudents.projectplanner.domain;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.time.temporal.ChronoUnit;


public class Project {
    // Variables for project creation/edit project
    private int id;
    private String title;                       // input
    private LocalDate startDate;                // input
    private LocalDate deadline;                 // input
    private int baselineManHourCost;            // input
    private int baselineHoursPrWorkday;         // input

    // Variables for calculating and showing info to user
    private int totalWorkHours;                 // calculated
    private int totalWorkDays;                  // calculated
    private LocalDate estFinishedByDate;        // calculated
    private int deadlineDifference;          // deadline - estFinishedByDate displayed in days and hours using remainder op -> %
    private int changeToWorkHoursNeeded;        // calculated
    private int estTotalCost;                   // calculated

    // Variables related to business logic
    private ArrayList<Subproject> subprojects;

    public Project() {
        this.subprojects = new ArrayList<Subproject>();
    }

    public Project(String title, LocalDate startDate) {
        this.title = title;
        this.startDate = startDate;
    }

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
        subprojects.remove(subproject);
    }

    public void sortSubprojects() {
        Collections.sort(subprojects);
    }

    public void calculateSubprojects() {
        for (int i = 0; i < subprojects.size(); i++) {
            int[] taskData = subprojects.get(i).calculateTasks();
            totalWorkHours += taskData[0];
            estTotalCost += taskData[1];
            estTotalCost += taskData[2];
        }
    }

    public void calculateProjectData() {
        calculateSubprojects();

        totalWorkDays = totalWorkHours * baselineHoursPrWorkday;
        estFinishedByDate = startDate.plusDays(totalWorkDays);

        deadlineDifference = (int) ChronoUnit.DAYS.between(deadline, estFinishedByDate);
        changeToWorkHoursNeeded = deadlineDifference / baselineHoursPrWorkday;


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

    public ArrayList<Subproject> getSubprojects() {
        return subprojects;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(int totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public int getTotalWorkDays() {
        return totalWorkDays;
    }

    public void setTotalWorkDays(int totalWorkDays) {
        this.totalWorkDays = totalWorkDays;
    }

    public int getBaselineManHourCost() {
        return baselineManHourCost;
    }

    public void setBaselineManHourCost(int baselineManHourCost) {
        this.baselineManHourCost = baselineManHourCost;
    }

    public int getBaselineHoursPrWorkday() {
        return baselineHoursPrWorkday;
    }

    public void setBaselineHoursPrWorkday(int baselineHoursPrWorkday) {
        this.baselineHoursPrWorkday = baselineHoursPrWorkday;
    }

    public LocalDate getEstFinishedByDate() {
        return estFinishedByDate;
    }

    public void setEstFinishedByDate(LocalDate estFinishedByDate) {
        this.estFinishedByDate = estFinishedByDate;
    }

    public int getDeadlineDifference() {
        return deadlineDifference;
    }

    public void setDeadlineDifference(int deadlineDifference) {
        this.deadlineDifference = deadlineDifference;
    }

    public int getChangeToWorkHoursNeeded() {
        return changeToWorkHoursNeeded;
    }

    public void setChangeToWorkHoursNeeded(int changeToWorkHoursNeeded) {
        this.changeToWorkHoursNeeded = changeToWorkHoursNeeded;
    }

    public int getEstTotalCost() {
        return estTotalCost;
    }

    public void setEstTotalCost(int estTotalCost) {
        this.estTotalCost = estTotalCost;
    }

    public void setSubprojects(ArrayList<Subproject> subprojects) {
        this.subprojects = subprojects;
    }

}

