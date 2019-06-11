package main.api.utils;

public enum ExceptionCode {
    /*
        Kody błędów. {0} oznacza że z konstruktora ApplicationException params[0] zostanie w
        to miejsce podstawione.
     */
    USER_ALREADY_REGISTERED("User with email {0} has been already registered"),
    BAD_CREDENTIALS("Bad login or bad password"),
    USER_NOT_LOGGED("User is not logged in"),
    USER_EXISTING("User is already in this group"),
    USER_NOT_FOUND("Cannot find user by id: {0}"),
    ITEM_NOT_FOUND("Item not found by id: {0}"),
    POLL_IS_CLOSED("Poll with id {0} is closed"),
    VOTE_IS_CLOSED("Vote with id {0} is closed"),
    NOT_ALLOWED("You are not allowed to do this"),
    VOTED_ALREADY("You have voted already"),
    ACCESS_DENIED("Access to current resource is denied: {0}"),
    ANSWER_NOT_EXISTING("Answer is not existing. Wrong vote"),
    QUESTION_NOT_UNDERWAY("Question with id {0} is not underway"),
    ;

    private String message;
    private int status;

    ExceptionCode(String message){
        this.message = message;
        this.status = 500;
        //TODO
    }

    ExceptionCode(String message,int status){
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
