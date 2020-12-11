package keastudents.projectplanner.domain;

public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException(String msg) {
        super(msg);
    }

}
