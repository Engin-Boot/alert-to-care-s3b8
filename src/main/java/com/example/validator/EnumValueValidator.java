package com.example.validator;

import com.example.customannotations.Enum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
 
public class EnumValueValidator implements ConstraintValidator<Enum, String>
{
    private Enum annotation;
 
    @Override
    public void initialize(Enum annotation)
    {
        this.annotation = annotation;
    }
    
    
    public boolean validate(Object enumValue, Object[] enumValues, String valueForValidation, boolean result) {
    	if(valueForValidation.equals(enumValue.toString())||refactorcheckValid(valueForValidation,enumValue,result)) {
    		result = true;
    	}
		return result;
    }
    
    public boolean refactorcheckValid(String valueForValidation, Object enumValue, boolean result) {
    	if(this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString())){
    		result = true;
    	}
		return result;
    }
    
    public boolean checkValid(Object[] enumValues, String valueForValidation, boolean result) {
    	for(Object enumValue:enumValues)
        {
            if(validate(enumValue,enumValues, valueForValidation, result))
            {
                result = true; 
                break;
            }
        }
		return result;
    }
    
    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext)
    {
        boolean result = false;
         
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
         
        if(enumValues != null)
        {
            result = checkValid(enumValues, valueForValidation, result);
        }
        return result;
    }
}
