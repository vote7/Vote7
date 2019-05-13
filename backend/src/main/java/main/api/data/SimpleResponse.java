package main.api.data;

public class SimpleResponse {

    private final String message;

    public SimpleResponse(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
