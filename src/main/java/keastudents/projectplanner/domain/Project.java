// Rasmus
package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.time.temporal.ChronoUnit;


public class Project {
    // Variables related to business logic
    private ArrayList<Subproject> subprojects = new ArrayList<Subproject>();
    private LocalDate defaultDate = LocalDate.of(2099,01,01);

    // Variables for project creation/edit project
    private int id;
    private String title;                       // input
    private LocalDate startDate;                // input
    private LocalDate deadline = defaultDate;   // input
    private int baselineManHourCost = 0;            // input
    private int baselineHoursPrWorkday = 0;         // input

    // Variables for calculating and showing info to user
    private int totalWorkHours = 0;                 // calculated
    private int totalWorkDays = 0;                  // calculated
    private LocalDate estFinishedByDate = defaultDate;
    private int deadlineDifference;             // deadline - estFinishedByDate displayed in days and hours using remainder op -> %
    private int changeToWorkHoursNeeded;        // calculated
    private int estTotalCost;                   // calculated

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
        // Resetting variables to avoid addition with saved values in database
        totalWorkHours = 0;
        estTotalCost = 0;

        for (int i = 0; i < subprojects.size(); i++) {
            int[] taskData = subprojects.get(i).calculateSubproject(baselineHoursPrWorkday);
            totalWorkHours += taskData[0];
            estTotalCost += taskData[1];
            estTotalCost += taskData[2];
        }
    }

    public void calculateProjectData() {
        calculateSubprojects();
        // Resetting variables to avoid addition with saved values in database
        totalWorkDays = 0;

        totalWorkDays = (int) Math.ceil((double) totalWorkHours / baselineHoursPrWorkday); // Rounds up
        estFinishedByDate = startDate.plusDays(totalWorkDays);
        deadlineDifference = (int) ChronoUnit.DAYS.between(deadline, estFinishedByDate);

        int neededWorkHoursDaily = 0;
        int workDaysAvailable = 0;
        if (deadlineDifference > 0) {
            workDaysAvailable = (int) ChronoUnit.DAYS.between(startDate, deadline)+1;
            neededWorkHoursDaily = (totalWorkHours / workDaysAvailable);
            changeToWorkHoursNeeded = neededWorkHoursDaily - baselineHoursPrWorkday;
        } else {
            changeToWorkHoursNeeded = 0;
        }

    }

    public void setStartDate(String startDate) {
        if (startDate != null) {
            this.startDate = LocalDate.parse(startDate);
        } else {
            this.startDate = defaultDate;
        }
    }

    public void setDeadline(String deadline) {
        if (deadline != null) {
            this.deadline = LocalDate.parse(deadline);
        } else {
            this.deadline = defaultDate;
        }
    }

    public void setEstFinishedByDate(String estFinishedByDate) {
        if (estFinishedByDate != null) {
            this.estFinishedByDate = LocalDate.parse(estFinishedByDate);
        } else {
            this.estFinishedByDate = defaultDate;
        }
    }

    public void setEstFinishedByDate(LocalDate estFinishedByDate) {
        this.estFinishedByDate = estFinishedByDate;
    }

    public ArrayList<Subproject> getSubprojects() {
        return subprojects;
    }

    public void setSubprojects(ArrayList<Subproject> subprojects) {
        this.subprojects = subprojects;
    }

    public LocalDate getDefaultDate() {
        return defaultDate;
    }

    public void setDefaultDate(LocalDate defaultDate) {
        this.defaultDate = defaultDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getDeadline() {
        return deadline;
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

    public LocalDate getEstFinishedByDate() {
        return estFinishedByDate;
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
}

