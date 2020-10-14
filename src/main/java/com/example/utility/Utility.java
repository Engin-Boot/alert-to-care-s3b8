package com.example.utility;

import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Service;


public class Utility {

    public static boolean checkIfDateIsValid(String date){
        return GenericValidator.isDate(date, "yyyy-MM-dd",true);
    }
}
