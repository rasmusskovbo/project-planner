package keastudents.projectplanner.controller;

public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException(String msg) {
        super(msg);
    }

}
