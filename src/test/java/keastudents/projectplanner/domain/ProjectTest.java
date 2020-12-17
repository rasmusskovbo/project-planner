package keastudents.projectplanner.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.LocalDate;
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
}