package com.example.utility;

import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;


public class Utility {

    public static final ResourceBundle rb = ResourceBundle.getBundle("limits");

    public static String getVitalLimit(String key) {
        String path = rb.getString(key);
        return path;
    }

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
