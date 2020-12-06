package keastudents.projectplanner.controller;

public class ValidationController {
    private final String namePattern = "^[a-zA-Z]*$";
    private final String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public ValidationController() {
    }

    public String validate(String firstName, String lastName, String email) {
        String errorMsg = "";
        if (!firstName.matches(namePattern) || !lastName.matches(namePattern)) {
            errorMsg += "Name can only contain letters.";
            errorMsg += "\n";
        }
        if (!email.matches(emailPattern)) {
            errorMsg += "Not a valid e-mail.";
        }
        return errorMsg;
    }

}
