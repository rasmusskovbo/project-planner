package keastudents.projectplanner.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidationService {
    private final String namePattern = "^[a-zA-Z]*$";
    private final String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private LocalDate defaultDate;

    public ValidationService(LocalDate defaultDate) {
        this.defaultDate = defaultDate;
    }

    public String validateNewUser(String firstName, String lastName, String email, String password, String confirmedPassword) {
        String errorMsg = "";
        if (!firstName.matches(namePattern) || !lastName.matches(namePattern)) {
            errorMsg += "Name can only contain letters.";
            errorMsg += "\n";
        }
        if (!email.matches(emailPattern)) {
            errorMsg += "Not a valid e-mail.";
        }
        if (!password.matches(confirmedPassword)) {
            errorMsg += "The passwords did not match.";
        }
        return errorMsg;
    }

    public LocalDate validateDeadline(String deadline) {
        LocalDate deadlineFormatted = null;

        if (deadline.equals("")) {
            deadlineFormatted = defaultDate;
        } else {
            deadlineFormatted = localDateFormatter(deadline);
        }

        return deadlineFormatted;
    }

    // Formats input String date to LocalDate format
    public LocalDate localDateFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

}
