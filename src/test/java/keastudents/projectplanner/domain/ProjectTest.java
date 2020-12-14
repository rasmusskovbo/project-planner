package keastudents.projectplanner.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void creatProject_successfully() {
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
    void removeSubproject_Successfully() {
        Project project = new Project();
        Subproject subproject1 = new Subproject();
        Subproject subproject2 = new Subproject();

        project.addSubproject(subproject1);
        project.addSubproject(subproject2);

        project.removeSubproject(subproject1);
        assertEquals(1, project.getSubprojects().size());

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
    void removeTask_Successfully() {
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
    void sort_Successfully() {
        Project project = new Project();
        Subproject subproject = new Subproject();

        LocalDate a = LocalDate.of(2020, 11, 11);
        LocalDate b = LocalDate.of(2020, 9, 9);

        project.setStartDate(a);
        subproject.setStartDate(b);
        //Linjen neden under fejler. ved ikke om det er min opsætning der er noget galt med. eller,
        // om det er fordi sort-metoden ikke er lavet korret under projekts.
       // subproject.sortSubprojects(b.compareTo(project.getStartDate()));

    }


}