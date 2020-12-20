// Orn-Iliya
package keastudents.projectplanner.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void createProject_successfully() {
        LocalDate d = LocalDate.of(2020, 11, 11);
        Project project = new Project("Byggeprojekt", 1, d);
        String expected = "Byggeprojekt";
        String actual = project.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void addSubproject_successfully() {
        Project project = new Project();
        Subproject subproject1 = new Subproject();
        Subproject subproject2 = new Subproject();
        Subproject subproject3 = new Subproject();

        project.addSubproject(subproject1);
        project.addSubproject(subproject2);
        project.addSubproject(subproject3);

        assertEquals(3, project.getSubprojects().size());
    }

    @Test
    void removeSubproject_successfully() {
        Project project = new Project();
        Subproject subproject1 = new Subproject();
        Subproject subproject2 = new Subproject();

        project.addSubproject(subproject1);
        project.addSubproject(subproject2);

        project.removeSubproject(subproject1);
        assertEquals(1, project.getSubprojects().size());
    }

    @Test
    void sortSubprojects_successfully() {
        ArrayList<Subproject> subprojects = new ArrayList<Subproject>();
        Subproject subproject1 = new Subproject("Garden", LocalDate.of(2020,9,1));
        Subproject subproject2 = new Subproject("House", LocalDate.of(2020,10,15));
        Subproject subproject3 = new Subproject("Carport", LocalDate.of(2020,1,3));

        subprojects.add(subproject1);
        subprojects.add(subproject2);
        subprojects.add(subproject3);
        //Sorts from lowest-highest date
        Collections.sort(subprojects);

        ArrayList<Subproject> expectedSortedSubprojects = new ArrayList<Subproject>();
        expectedSortedSubprojects.add(subproject3);
        expectedSortedSubprojects.add(subproject1);
        expectedSortedSubprojects.add(subproject2);

        assertEquals(subprojects, expectedSortedSubprojects);
    }

    @Test
    void calculateSubprojects_tested_totalWorkHours_successfully() {
        int totalWorkHours = 0;
        ArrayList<Subproject> subprojects = new ArrayList<>();
        Subproject subproject1 = new Subproject("Garden", LocalDate.of(2020,10,1));
        Subproject subproject2 = new Subproject("House", LocalDate.of(2020,9,13));
        Task task1 = new Task();
        Task task2 = new Task();

        subproject1.addTask(task1);
        subprojects.add(subproject1);

        subproject2.addTask(task2);
        subprojects.add(subproject2);

        task1.setWorkHoursNeeded(5);
        task2.setWorkHoursNeeded(7);

        for (Subproject subproject : subprojects) {
            int[] taskData = subproject.calculateSubproject(4);
            totalWorkHours += taskData[0];
        }

        assertEquals(12, totalWorkHours);
    }

    @Test
    void calculateSubprojects_tested_estTotalCost_successfully() {
        int estTotalCost = 0;
        ArrayList<Subproject> subprojects = new ArrayList<>();
        Subproject subproject1 = new Subproject("Garden", LocalDate.of(2020,10,1));
        Subproject subproject2 = new Subproject("House", LocalDate.of(2020,9,13));

        Task task1 = new Task();
        Task task2 = new Task();

        subproject1.addTask(task1);
        subprojects.add(subproject1);

        subproject2.addTask(task2);
        subprojects.add(subproject2);

        task1.setExtraCosts(200);
        task1.setManHourCost(120);
        task1.setWorkHoursNeeded(2);

        task2.setExtraCosts(1000);
        task2.setManHourCost(100);
        task2.setWorkHoursNeeded(5);

        for (Subproject subproject : subprojects) {
            int[] taskData = subproject.calculateSubproject(4);
            estTotalCost += taskData[1];
            estTotalCost += taskData[2];
        }

        assertEquals(1940, estTotalCost);
    }

    @Test
    void calculateProjectData_tested_totalWorkDays_successfully() {
        int totalWorkDays;
        int totalWorkHours = 120;
        int baselineHoursPrWorkday = 8;

        totalWorkDays = (int) Math.ceil((double) totalWorkHours / baselineHoursPrWorkday);

        assertEquals(15, totalWorkDays);
    }

    @Test
    void calculateProjectData_tested_estFinishedByDate_successfully() {
        LocalDate estFinishedByDate;
        int totalWorkDays = 15;
        Project project = new Project("Garden", LocalDate.of(2020,2,2));

        estFinishedByDate = project.getStartDate().plusDays(totalWorkDays);

        assertEquals(LocalDate.of(2020,2,17), estFinishedByDate);
    }

    @Test
    void calculateProjectData_tested_deadlineDifference_successfully() {
        LocalDate estFinishedByDate;
        int deadlineDifference;
        int totalWorkDays = 15;
        Project project = new Project("Garden", LocalDate.of(2020,2,2));

        project.setDeadline("2020-02-29");
        estFinishedByDate = project.getStartDate().plusDays(totalWorkDays);

        deadlineDifference = (int) ChronoUnit.DAYS.between(project.getDeadline(), estFinishedByDate);

        assertEquals(-12, deadlineDifference);
    }

    @Test
    void calculateProjectData_tested_workDaysAvailable_successfully() {
        int workDaysAvailable;
        Project project = new Project("Garden", LocalDate.of(2020,2,2));

        project.setDeadline("2020-02-29");

        workDaysAvailable = (int) ChronoUnit.DAYS.between(project.getStartDate(), project.getDeadline())+1;

        assertEquals(28, workDaysAvailable);
    }


    @Test
    void calculateProjectData_tested_neededWorkHoursDaily_successfully() {
        int neededWorkHoursDaily;
        int totalWorkHours = 224;
        int workDaysAvailable = 28;

        neededWorkHoursDaily = (totalWorkHours / workDaysAvailable);

        assertEquals(8, neededWorkHoursDaily);
    }

    @Test
    void calculateProjectData_tested_changeToWorkHoursNeeded_successfully() {
        int changeToWorkHoursNeeded;
        int neededWorkHoursDaily = 8;
        int baselineHoursPrWorkday = 7;
        Project project = new Project();

        project.setDeadlineDifference(2);

        if (project.getDeadlineDifference() > 0) {
            changeToWorkHoursNeeded = neededWorkHoursDaily - baselineHoursPrWorkday;
        } else {
            changeToWorkHoursNeeded = 0;
        }

        assertEquals(1, changeToWorkHoursNeeded);
    }

}