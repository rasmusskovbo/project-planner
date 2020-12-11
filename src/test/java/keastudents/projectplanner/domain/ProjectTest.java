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
        LocalDate d = LocalDate.of(2020,11,11);
        Project project = new Project( "Byggeprojekt", 1, d);
        String expected = "Byggeprojekt";
        String actual= project.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void addSubproject_successfully() {
        Project project = new Project();
        Subproject subproject1 = new Subproject();
        Subproject subproject2 = new Subproject();

        project.addSubproject(subproject1);
        project.addSubproject(subproject2);
        project.addSubproject(2);

        assertEquals(3, project.getSubprojects().size());


        }


    @Test
    void removeSubproject_Successfully() {
        Project project = new Project();
        Subproject subproject1 = new Subproject();
        Subproject subproject2 = new Subproject();

        project.addSubproject(subproject1);
        project.addSubproject(subproject2);

        project.removeSubproject(0);
        assertEquals(1, project.getSubprojects().size());

    }

    @Test
    void sort_Successfully() {

    }

}