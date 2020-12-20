// Wafaa
package keastudents.projectplanner.domain;

import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {
    @Test
    void validateNewUser_firstName_NotValid_Successfully() {
        ValidationService validationService = new ValidationService(LocalDate.of(2021, 01, 01));
        String errorMsg =  validationService.validateNewUser("Hans123", "Hasen", "hansen@test.dk", "123", "123");

        assertEquals("Name can only contain letters."+"\n", errorMsg);


    }
    @Test
    void validateNewUser_lastName_NotValid_Successfully() {
        ValidationService validationService = new ValidationService(LocalDate.of(2021, 01, 01));
        String errorMsg =  validationService.validateNewUser("Hans", "Hansen123", "hansen@test.dk", "123", "123");

        assertEquals("Name can only contain letters."+"\n", errorMsg);


    }
    @Test
    void validateNewUser_emailNotValid_Successfully() {
        ValidationService validationService = new ValidationService(LocalDate.of(2021, 01, 01));
        String errorMsg =  validationService.validateNewUser("Hans", "Hansen", "hansentest.dk", "123", "123");

        assertEquals("Not a valid e-mail.", errorMsg);


    }

    @Test
    void validateNewUser_password_and_confirmedPassword_NotValid_Successfully() {
        ValidationService validationService = new ValidationService(LocalDate.of(2021, 01, 01));
        String errorMsg =  validationService.validateNewUser("Hans", "Hansen", "hansen@test.dk", "13", "123");

        assertEquals("The passwords did not match.", errorMsg);


    }


    @Test
    void validateDeadline_Successfully() {
        ValidationService validationService = new ValidationService(LocalDate.of(2020, 01, 01));
        LocalDate deadlineFormatted = validationService.validateDeadline("2020-12-12");
        assertEquals(LocalDate.of(2020,12,12), deadlineFormatted);

    }

    @Test
    void localDateFormatter_Formats_input_String_date_to_LocalDate_format_Successfully() {
        ValidationService validationService = new ValidationService(LocalDate.of(2021, 01, 01));

        LocalDate localDateFormatter = validationService.localDateFormatter("2020-12-12");
        assertEquals(LocalDate.of(2020,12,12), localDateFormatter);

    }


}