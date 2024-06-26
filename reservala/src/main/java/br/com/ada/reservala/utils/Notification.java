package br.com.ada.reservala.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Notification extends RuntimeException{
    List<Error> errors = new ArrayList<>();

    public void addError(String message){
        addError(message, null);
    }

    public void addError(String message, Exception e){
        errors.add(new Error(message, e));
    }

    public boolean isCorrect(){
        return errors.isEmpty();
    }

    public boolean hasErrors(){
        return !isCorrect();
    }

    public static class Error{
        String message;
        Exception cause;

        public Error(String message, Exception cause){
            this.message = message;
            this.cause = cause;
        }
    }

    public String errorMessage(){
        return errors.stream()
                .map(e -> e.message)
                .collect(Collectors.joining(" , "));
    }
}
