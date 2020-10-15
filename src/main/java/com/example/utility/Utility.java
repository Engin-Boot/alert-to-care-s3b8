package com.example.utility;

import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;


public class Utility {

    public static boolean checkIfDateIsValid(String date){
        return GenericValidator.isDate(date, "yyyy-MM-dd",true);
    }

    public static String getAllErrorMessages(MethodArgumentNotValidException ex){
        String errorMessage = "";
        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            errorMessage += error.getDefaultMessage()+", ";
        }
        return errorMessage;
    }
}
