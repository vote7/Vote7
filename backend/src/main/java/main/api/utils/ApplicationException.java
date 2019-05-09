package main.api.utils;

public class ApplicationException extends RuntimeException {

    private ExceptionCode code;
    private String errorMessage;

    public ApplicationException(ExceptionCode code, Object ... params){
        this.code = code;
        this.errorMessage = parseErrorMessage(code.getMessage(),params);
    }

    private String parseErrorMessage(String message, Object[] params) {
       String[] var = message.split("\\{*}");
       for(String param : var){
           char last = param.charAt(param.length() - 1);
           if(!Character.isDigit(last))
               continue;
           int number = Integer.valueOf(String.valueOf(last));
           if(params.length > number)
               message = message.replace("{"+number+"}",params[number].toString());
       }
       return message;
    }

    public ExceptionCode getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
