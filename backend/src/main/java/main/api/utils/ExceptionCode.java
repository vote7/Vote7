package main.api.utils;

public enum ExceptionCode {
    /*
        Kody błędów. {0} oznacza że z konstruktora ApplicationException params[0] zostanie w
        to miejsce podstawione.
     */
    USER_ALREADY_REGISTERED("User with email {0} has been already registered"),
    BAD_CREDENTIALS("Bad login or bad password"),
    USER_NOT_LOGGED("User is not logged in")
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
