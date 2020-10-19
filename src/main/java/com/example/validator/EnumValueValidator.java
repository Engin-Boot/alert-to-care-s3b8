package com.example.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.customannotations.Enum;
 
public class EnumValueValidator implements ConstraintValidator<Enum, String>
{
    private Enum annotation;
 
    @Override
    public void initialize(Enum annotation)
    {
        this.annotation = annotation;
    }
    
    
    public boolean validate(Object enumValue, Object[] enumValues, String valueForValidation) {
		return valueForValidation.equals(enumValue.toString())||refactorcheckValid(valueForValidation,enumValue);
    }
    
    public boolean refactorcheckValid(String valueForValidation, Object enumValue) {
		return this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString());
    }
    
    public boolean checkValid(Object[] enumValues, String valueForValidation) {
    	boolean result = false;
    	for(Object enumValue:enumValues)
        {
            if(validate(enumValue,enumValues, valueForValidation))
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

        if(valueForValidation==null){
            return true;
        }

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();


        if(enumValues != null)
        {
            result = checkValid(enumValues, valueForValidation);
        }
        return result;
    }
}
