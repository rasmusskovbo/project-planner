// Orn-Iliya
package keastudents.projectplanner.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SubprojectTest {

    @Test
    void sortTasks_successfully() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task1 = new Task("Garden", LocalDate.of(2020,12,1));
        Task task2 = new Task("House", LocalDate.of(2020,11,3));
        Task task3 = new Task("Carport", LocalDate.of(2020,12,3));

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        //Sorts from lowest-highest date
        Collections.sort(tasks);

        ArrayList<Task> expectedSortedTasks = new ArrayList<Task>();
        expectedSortedTasks.add(task2);
        expectedSortedTasks.add(task1);
        expectedSortedTasks.add(task3);

        assertEquals(tasks, expectedSortedTasks);
    }

    @Test
    void calculateSubproject_tested_subtotalWorkHours_successfully() {
        int subtotalWorkHours = 0;

        Subproject subproject = new Subproject();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        task1.setWorkHoursNeeded(8);
        tasks.add(task1);

        task2.setWorkHoursNeeded(6);
        tasks.add(task2);

        task3.setWorkHoursNeeded(7);
        tasks.add(task3);

        for (Task task : tasks) {
            subtotalWorkHours += task.getWorkHoursNeeded();
        }

        subproject.setTotalWorkHours(subtotalWorkHours);

        assertEquals(21, subproject.getTotalWorkHours());
    }

    @Test
    void calculateSubproject_tested_subtotalExtraCosts_successfully() {
        int subtotalExtraCosts = 0;

        Subproject subproject = new Subproject();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        task1.setExtraCosts(1000);
        tasks.add(task1);

        task2.setExtraCosts(560);
        tasks.add(task2);

        task3.setExtraCosts(200);
        tasks.add(task3);

        for (Task task : tasks) {
            subtotalExtraCosts += task.getExtraCosts();
        }

        subproject.setEstTotalCost(subtotalExtraCosts);

        assertEquals(1760, subproject.getEstTotalCost());
    }

    @Test
    void calculateSubproject_tested_subtotalCostOfLabor_successfully() {
        int subtotalCostOfLabor = 0;

        ArrayList<Task> tasks = new ArrayList<Task>();
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        task1.setManHourCost(100);
        task1.setWorkHoursNeeded(8);
        tasks.add(task1);

        task2.setManHourCost(120);
        task2.setWorkHoursNeeded(5);
        tasks.add(task2);

        task3.setManHourCost(150);
        task3.setWorkHoursNeeded(3);
        tasks.add(task3);

        for (Task task : tasks) {
            subtotalCostOfLabor += task.getManHourCost() * task.getWorkHoursNeeded();
        }

        assertEquals(1850, subtotalCostOfLabor);
    }

    @Test
    void calculateSubproject_tested_workDaysAvailable_successfully() {
        int workDaysAvailable;
        LocalDate startDate = LocalDate.of(2020,12,1);
        LocalDate deadline = LocalDate.of(2020,12,31);

        workDaysAvailable = (int) ChronoUnit.DAYS.between(startDate, deadline) +1;

        assertEquals(31, workDaysAvailable);
    }

    @Test
    void calculateSubproject_tested_neededWorkHoursDaily_successfully() {
        int neededWorkHoursDaily;
        int totalWorkHours=155;
        int workDaysAvailable = 31;

        neededWorkHoursDaily = (totalWorkHours / workDaysAvailable);

        assertEquals(5, neededWorkHoursDaily);
    }

    @Test
    void calculateSubproject_tested_setChangeToWorkHoursNeeded_successfully() {
        Subproject subproject = new Subproject();
        int neededWorkHoursDaily = 5;
        int baselineHoursPrWorkday = 8;
        subproject.setDeadlineDifference(2);

        if (subproject.getDeadlineDifference() > 0) {
            int result = neededWorkHoursDaily - baselineHoursPrWorkday;
            subproject.setChangeToWorkHoursNeeded(result);
        } else {
            subproject.setChangeToWorkHoursNeeded(0);
        }

        assertEquals(-3, subproject.getChangeToWorkHoursNeeded());
    }


    @Test
    void addTask_successfully() {
        Project project = new Project();
        Subproject subproject = new Subproject();
        project.addSubproject(subproject);

        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();

        subproject.addTask(task1);
        subproject.addTask(task2);
        subproject.addTask(task3);

        assertEquals(3, subproject.getTasks().size());
    }


    @Test
    void removeTask_successfully() {
        Project project = new Project();
        Subproject subproject = new Subproject();
        project.addSubproject(subproject);

        Task task1 = new Task();
        Task task2 = new Task();

        subproject.addTask(task1);
        subproject.addTask(task2);

        subproject.removeTask(task1);
        assertEquals(1, project.getSubprojects().size());

    }
    @Test
    void compareTo_tested_equal_successfully() {
        Subproject subproject1 = new Subproject("House", LocalDate.of(2020,12,11));
        Subproject subproject2 = new Subproject("Garden", LocalDate.of(2020,12,11));

        int result = subproject1.compareTo(subproject2);

        assertEquals(result, 0, "expected to be equal");
    }

    @Test
    void compareTo_tested_greater_than_successfully() {
        Subproject subproject1 = new Subproject("House", LocalDate.of(2020,12,1));
        Subproject subproject2 = new Subproject("Garden", LocalDate.of(2020,10,13));

        int result = subproject1.compareTo(subproject2);

        assertTrue(result >= 1, "expected to be greater than");
    }

    @Test
    void compareTo_tested_less_than_successfully() {
        Subproject subproject1 = new Subproject("House", LocalDate.of(2020,12,12));
        Subproject subproject2 = new Subproject("Garden", LocalDate.of(2020,12,13));

        int result = subproject1.compareTo(subproject2);

        assertTrue(result <= -1, "expected to be less than");
    }
}